package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {
	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private Map<String,IWebWorker> workersMap;
	private Map<String, SessionMapEntry> sessions =
			new ConcurrentHashMap<String, SmartHttpServer.SessionMapEntry>();
	private Random sessionRandom = new Random();
	ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

	public SmartHttpServer(String configFileName) {
		readServerProperties(configFileName);
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				sessions = sessions.entrySet().stream().filter((e) -> {
					if(e.getValue().validUntil > getCurrentSecond()) return false;
					return true;
				}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			}
		}, 0, 300, TimeUnit.SECONDS);

	}

	private void readServerProperties(String configFileName) {
		String path = "./config/" + configFileName;
		try (InputStream input = new FileInputStream(path)) {
			Properties prop = new Properties();
			prop.load(input);
			address = prop.getProperty("server.address");
			domainName = prop.getProperty("server.domainName");
			port = Integer.parseInt(prop.getProperty("server.port"));
			workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
			documentRoot = Path.of(prop.getProperty("server.documentRoot"));
			sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
			try(InputStream input2 = new FileInputStream(Path.of(prop.getProperty("server.mimeConfig")).normalize().toAbsolutePath().toString())){
				Properties prop2 = new Properties();
				prop2.load(input2);
				mimeTypes = prop2.entrySet().stream().collect(Collectors.toMap(
						(entry) -> entry.getKey().toString(),
						(entry) -> entry.getValue().toString()
						));

			}
			try(InputStream input2 = new FileInputStream(Path.of(prop.getProperty("server.workers")).normalize().toAbsolutePath().toString())){
				Properties prop2 = new Properties();
				prop2.load(input2);
				workersMap = prop2.entrySet().stream().collect(Collectors.toMap(
						(entry) -> entry.getKey().toString(),
						(entry) -> {
							String FQCN = ((Entry<Object,Object>) entry).getValue().toString();
							return getIWebworker(FQCN);					
						}
						));
			}


		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	private IWebWorker getIWebworker(String FQCN) {
		Class<?> referenceToClass;
		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(FQCN)
					;@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					return (IWebWorker)newObject;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	protected synchronized void start() {
		System.out.println("Server je pokrenut");
		serverThread = new ServerThread();
		serverThread.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}
	protected synchronized void stop() {
		threadPool.shutdown();
		serverThread.interrupt();
	}
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(
						new InetSocketAddress(domainName, port)
						);

				while(true) {
					Socket toClient = serverSocket.accept();
					threadPool.submit(new ClientWorker(toClient));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// given in pesudo-code:
			// open serverSocket on specified port
			// while(true) {
			// Socket client = serverSocket.accept();
			// ClientWorker cw = new ClientWorker(client);
			// submit cw to threadpool for execution
			// }
		}
	}
	private class ClientWorker implements Runnable,IDispatcher {
		private Socket csocket;
		private InputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private String host;
		private Map<String,String> params = new HashMap<String, String>();
		private Map<String,String> tempParams = new HashMap<String, String>();
		private Map<String,String> permPrams = new HashMap<String, String>();
		private List<RCCookie> outputCookies =
				new ArrayList<RequestContext.RCCookie>();
		private String SID;
		private RequestContext context;


		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new BufferedInputStream(
						csocket.getInputStream()
						);
				ostream = new BufferedOutputStream(
						csocket.getOutputStream()
						);

				Optional<List<String>> request = readRequest();
				if(request.isEmpty()) {
					return;
				}
				// Then read complete request header from your client in separate method...
				List<String> headers = extractHeaders(request.get());

				checkSession(headers);

				// If header is invalid (less then a line at least) return response status 400
				String[] firstLine = headers.isEmpty() ? 
						null : headers.get(0).split(" ");
				if(firstLine==null || firstLine.length != 3) {
					sendEmptyResponse(ostream, 400, "Bad request");
					return;
				}
				// Extract (method, requestedPath, version) from firstLine
				// if method not GET or version not HTTP/1.0 or HTTP/1.1 return response status 400
				String method = firstLine[0].toUpperCase();
				if(!method.equals("GET")) {
					sendEmptyResponse(ostream, 405, "Method Not Allowed");
					return;
				}

				String version = firstLine[2].toUpperCase();
				if(!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendEmptyResponse(ostream, 505, "HTTP Version Not Supported");
					return;
				}
				host = domainName;
				for(String header : headers) {
					// Go through headers, and if there is header “Host: xxx”, assign host property
					// to trimmed value after “Host:”; else, set it to server’s domainName
					// If xxx is of form some-name:number, just remember “some-name”-part
					if(header.startsWith("Host:")) {
						host = header.substring(header.indexOf(":")).trim();
						if(host.contains(":")) {
							host = host.substring(host.indexOf(":"), host.length());
						}
					}
				}

				String[] pathAll = firstLine[1].split("\\?");
				String path = pathAll[0];
				if(pathAll.length != 1) {
					String paramString = pathAll[1];
					String[] paramsS = paramString.split("&");

					// (path, paramString) = split requestedPath to path and parameterString
					// parseParameters(paramString); ==> your method to fill map parameters
					for(String param : paramsS) {
						String[] KV = param.split("=");
						params.put(KV[0], KV.length >1 ?KV[1]: null);
					}
				}
				if(path.startsWith("/private")){
					internalDispatchRequest(path, true);
					return;
				}
				
				
				serveRequest(path);

			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void runWorker(String worker, Boolean fullPath) throws Exception{
			if(fullPath == false) worker = getClass().getPackageName() + ".workers." + worker;
			checkIfContextNull();
			synchronized(this) {
				getIWebworker(worker).processRequest(context);
			}
			ostream.flush();
			return;
			
		}

		private void checkSession(List<String> headers) {
			String sidCandidate = null;
			for(String line : headers) {
				if(!line.startsWith("Cookie:")) continue;
				Map<String,String> cookies = List.of(line.split("\\;* ")).stream().skip(1).collect(Collectors.toMap(
						(s) -> ((String)s).split("=")[0],
						(s) -> ((String)s).split("=")[1].replace("\"", "")
						)) ;

				if((sidCandidate = cookies.get("sid"))  != null) continue;
				break;
			}
			if(sidCandidate == null || sessions.containsKey(sidCandidate) == false) {
				newSID();
				return;
			}
			SessionMapEntry entry = sessions.get(sidCandidate);
			if(entry.host != host) {
				newSID();
				return;
			}
			if(entry.validUntil < getCurrentSecond()) {
				sessions.remove(sidCandidate);
				newSID();
				return;
			}
			permPrams = entry.map;


		}

		private void newSID() {
			int leftLimit = 65; // letter 'A'
			int rightLimit = 122; // letter 'z'
			int targetStringLength = 20;

			String generatedString = sessionRandom.ints(leftLimit, rightLimit + 1)
					.limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
					.toString();
			SID = generatedString;
			RCCookie cookie = new RCCookie("sid",generatedString,null,host,"/");
			outputCookies.add(cookie);
			sessions.put(generatedString,new SessionMapEntry(generatedString, host, getCurrentSecond() + sessionTimeout, permPrams));

		}

		private void serveRequest(String path) throws IOException,Exception {
			if(workersMap.containsKey(path)){
				runWorker(path, true);
				return;
			}
			
			if(path.startsWith("/ext")) {
				runWorker(path.substring(path.lastIndexOf("/") +1, path.length()),false);
				return;
			}

			if(path.equals("/calc")) {
				runWorker("SumWorker",false);
				return;
			}
			
			if(path.equals("/index2.html")) {
				runWorker("Home", false);
				return;
			}
			if(path.startsWith("/setbgcolor")) {
				runWorker("BgColorWorker", false);
				return;
			}
			
			

			
			Path requestedFilePath = documentRoot.resolve(path.substring(1)).toAbsolutePath().normalize();

			if (!requestedFilePath.toString().startsWith(documentRoot.toString())) {
				sendEmptyResponse(ostream,403,"Forbidden");
				return;
			}
			// check if requestedPath exists, is file and is readable; if not, return status 404
			if(!Files.isReadable(requestedFilePath)) {
				sendEmptyResponse(ostream,404,"File Not Found");
				return;
			}
			// else extract file extension
			// find in mimeTypes map appropriate mimeType for current file extension
			// (you filled that map during the construction of SmartHttpServer from mime.properties)
			// if no mime type found, assume application/octet-stream
			byte[] okteti = Files.readAllBytes(requestedFilePath);
			// create a rc = new RequestContext(...); set mime-type; set status to 200
			if(extractExtension(requestedFilePath.getFileName().toString()).equals("smscr")) {
				String text = new String(okteti, StandardCharsets.UTF_8);
				checkIfContextNull();
				new SmartScriptEngine(
						new SmartScriptParser(text).getDocumentNode(),
						context
						).execute();
				ostream.flush();
				return;
			}
			String mt = mimeTypes.get(extractExtension(requestedFilePath.getFileName().toString()));
			if(mt == null) {
				mt = "application/octet-stream";
			}
			checkIfContextNull();
			context.setMimeType(mt);
			context.setContentLength((long)okteti.length);
			// open file, read its content and write it to rc (that will generate header and send
			// file bytes to client)
			context.write(okteti);
			ostream.flush();

		}

		private void checkIfContextNull() {
			if(context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies,this,tempParams,SID);
				context.setStatusCode(200);
				context.setStatusText("OK");
			}

		}

		private Optional<List<String>> readRequest() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l:		while(true) {
				int b = istream.read();
				if(b==-1) {
					if(bos.size()!=0) {
						System.err.println("Incomplete header received.");
					}
					return Optional.empty();
				}
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			String requestStr = new String(
					bos.toByteArray(), 
					StandardCharsets.US_ASCII
					);


			return Optional.of(List.of(requestStr.split("\n")));

		}
		private  List<String> extractHeaders(List<String> requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		private void sendResponseWithData(OutputStream cos, int statusCode, String statusText, String contentType, byte[] data) throws IOException {

			cos.write(
					("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
							"Server: simple java server\r\n"+
							"Content-Type: "+contentType+"\r\n"+
							"Content-Length: "+data.length+"\r\n"+
							"Connection: close\r\n"+
							"\r\n").getBytes(StandardCharsets.US_ASCII)
					);
			cos.write(data);
			cos.flush();
		}
		private  void sendEmptyResponse(OutputStream cos, int statusCode, String statusText) throws IOException {
			sendResponseWithData(cos, statusCode, statusText, "text/plain;charset=UTF-8", new byte[0]);
		}

		private  String extractExtension(String string) {

			int dot = string.lastIndexOf('.');
			if (dot>=1) {
				return string.substring(dot+1).toLowerCase();
			}
			return "";
		}

		private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if(directCall == true) {
				sendEmptyResponse(ostream,404,"File Not Found");
				return;
			}
			serveRequest(urlPath);

		}
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

	}

	private long getCurrentSecond() {
		return System.currentTimeMillis() / 1000;
	}

	private static class SessionMapEntry {
		String sid;
		String host;
		long validUntil;
		Map<String,String> map;

		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}





	}



}

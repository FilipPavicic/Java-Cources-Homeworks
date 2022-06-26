package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RequestContext {
	public static class RCCookie {
		String name;
		String value;
		String domain;
		String path;
		Integer maxAge;

		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getDomain() {
			return domain;
		}

		public String getPath() {
			return path;
		}

		public int getMaxAge() {
			return maxAge;
		}

		@Override
		public String toString() {
			return name + "=\"" + value + "\"" +
					(domain != null ? "; Domain=" + domain : "") + 
					(path != null ? "; Path=" + path : "") + 
					(maxAge != null ? "; MaxAge="+ maxAge : "");
		}
	}

	boolean headerGenerated = false;

	OutputStream outputStream;
	Charset charset;
	String encoding = "UTF-8";
	int statusCode = 200;
	String statusText = "OK";
	String mimeType = "text/html";
	Long contentLength = null;
	Map<String,String> parameters;
	Map<String,String> temporaryParameters;
	Map<String,String> persistentParameters;
	List<RCCookie> outputCookies;
	IDispatcher dispater;
	String sid;

	public RequestContext(OutputStream outputStream,	 Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, String sid) {
		this(outputStream, parameters, persistentParameters, outputCookies, new HashMap<>(),sid);
	}
	
	public RequestContext(OutputStream outputStream, String sid) {
		this(outputStream, null, null, null, sid);
	}
	
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, IDispatcher dispatcher,
			Map<String,String> temporaryParameters, String sid) {
		this(outputStream, parameters, persistentParameters, outputCookies, temporaryParameters, sid);
		this.dispater = dispatcher;
		
	}
	private RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, Map<String,String> temporaryParameters, String sid) {
		super();
		if(outputStream == null) throw new IllegalArgumentException("Argument outputStream mora biti definiran.");
		this.outputStream = outputStream;
		this.parameters = parameters != null ? parameters : new HashMap<>();
		this.persistentParameters = persistentParameters != null ? persistentParameters : new HashMap<>();
		this.outputCookies = outputCookies != null ? outputCookies : new ArrayList<>();
		this.temporaryParameters = temporaryParameters;
		this.sid = sid;
	}
	
	public IDispatcher getDispater() {
		return dispater;
	}

	public void setEncoding(String encoding) {
		checkIfHeaderGenerated();
		this.encoding = encoding;
	}

	public void setStatusCode(int statusCode) {
		checkIfHeaderGenerated();
		this.statusCode = statusCode;
	}

	public void setStatusText(String statusText) {
		checkIfHeaderGenerated();
		this.statusText = statusText;
	}

	public void setMimeType(String mimeType) {
		checkIfHeaderGenerated();
		this.mimeType = mimeType;
	}

	public void setContentLength(Long contentLength) {
		checkIfHeaderGenerated();
		this.contentLength = contentLength;
	}
	
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(cookie);
	}
	
	public boolean removeRCCookie(RCCookie cookie) {
		return outputCookies.remove(cookie);
	}

	public String getParameter(String name) {
		return parameters.get(name);
	}

	public Set<String> getParameterNames() {
		return  Collections.unmodifiableSet(parameters.keySet());
	}

	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	public Set<String> getPersistentParameterNames() {
		return  Collections.unmodifiableSet(persistentParameters.keySet());
	}

	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	public Set<String> getTemporaryParameterNames() {
		return  Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	public String getSessionID() {
		return sid;
	}

	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	static interface ConsumerIOException<T> {
		public void accept(T t) throws IOException;
	}

	public RequestContext write(byte[] data) throws IOException{
		return writeInternal((os) -> {
			os.write(data);
		});

	}
	public RequestContext write(byte[] data, int offset, int len) throws IOException{
		return writeInternal((os) -> {
			os.write(data, offset, len);
		});
	}
	public RequestContext write(String text) throws IOException{
		return writeInternal((os) -> {
			os.write(text.getBytes(charset));
		});
	}

	private RequestContext writeInternal(ConsumerIOException<OutputStream> work)  throws IOException{
		if(headerGenerated == false) {
			charset = Charset.forName(encoding);
			outputStream.write(generateHeader().getBytes(charset));
			headerGenerated = true;
		}
		work.accept(outputStream);
		return this;
	}



	private void checkIfHeaderGenerated() {
		if(headerGenerated == true) {
			throw new RuntimeException("Nije moguće mijenjati svojstva nakon inicijalizacije zaglavlja.");
		}
	}
	
	public String generateHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 "+statusCode+" "+statusText+"\r\n");
		sb.append("Content-Type: "+mimeType+(mimeType.startsWith("text/") == true ? "; charset=" + encoding:"") +"\r\n");
		sb.append(contentLength != null ? "Content-Length: "+contentLength+"\r\n" : "");
		for(RCCookie cookie : outputCookies) {
			sb.append("Set-Cookie: " + cookie + "\r\n");
		}
		sb.append("\r\n");
		return sb.toString();
	}

}

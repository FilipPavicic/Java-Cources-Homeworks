package hr.fer.oprpp2.chatapp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



public class Server {

	static class User {
		String name;
		ExecutorService thread;
		long UID;
		long numberRecieve = 1;
		long numberSend = 1;
		InetAddress address;
		int port;
		long randkey;

		public User(String name, ExecutorService thread, InetAddress address, int port,long UID,long randkey) {
			super();
			this.name = name;
			this.thread = thread;
			this.address = address;
			this.port = port;
			this.UID = UID;
			this.randkey = randkey;
		}

	}
	public static final Server server = new Server();


	public static final String LOCALHOST = "127.0.0.1";

	static InetAddress address = null;
	static int port = 0;
	static DatagramSocket socket = null;

	public static long UIDs = new Random().nextLong();

	static Map<Long,User> users = new HashMap<>();
	static Map<Long,Long> randkeys = new HashMap<>();

	public static long newUID() {
		return UIDs++;
	}

	private Future<?> addMessageInSendQueue(User user, Runnable message,boolean wait) {
		return user.thread.submit(new Runnable() {

			@Override
			public void run() {
				send(message,user,wait);
			}


		});

	}
	private boolean send(Runnable message,User user,boolean wait) {
		int counter = 0;
		while(counter < 10) {
			counter++;
			message.run();
			if(wait == false) return true;
			synchronized (this) {
				while(true) {
					try {
						wait(5000);

					} catch (InterruptedException e) {
						continue;
					}break;


				}
			}
			if(user.numberRecieve== user.numberSend) {
				return true;
			}
		}
		return false;

	}


	public static void main(String[] args) {
		if(args.length !=1) {
			System.err.println("Dragi korisniće, očekivao sam jedan argument: port");
			return;
		}

		try {
			address = InetAddress.getByName(LOCALHOST);
			port = Integer.parseInt(args[0]);

			if(port < 1 || port > 65535) {
				System.out.println("Port mora biti izmedu 1 i 65535");
				return;
			}

		}catch (UnknownHostException e) {
			System.out.println("Ne može se razrješiti adresu: " + LOCALHOST);
			return;
		}catch (NumberFormatException e) {
			System.out.println("Nije broj: "  + args[0]);
			return;
		}
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Ne mogu otvoriti pristupnu točku");
			return;
		}
		System.out.println("Započinjem slušanje na portu: " + port);

		while(true) {
			DatagramPacket packet = Utils.recieveMessage(address, port, socket);
			switch (packet.getData()[packet.getOffset()]) {
			case HelloMessage.CODE : {
				User user;
				HelloMessage helloMessage = HelloMessage.parseDatagramPacket(packet);
				Utils.printRecieve(helloMessage);
				if(randkeys.containsKey(helloMessage.randkey)) {
					System.out.println("Korisnik već postoji u bazi, zanemarujem prijavu i vraćam mu postojeće podatke");
					user = users.get(randkeys.get(helloMessage.randkey));
					server.addMessageInSendQueue(user,() -> new AckMessage(helloMessage.number, user.UID).sendMessage(user, socket),false);
					break;
				}
				user = new User(helloMessage.name, Executors.newFixedThreadPool(1), packet.getAddress(), packet.getPort(),newUID(),helloMessage.randkey);
				randkeys.put(helloMessage.randkey, user.UID);
				users.put(user.UID, user);
				server.addMessageInSendQueue(user,() -> new AckMessage(helloMessage.number, user.UID).sendMessage(user, socket),false);
				break;

			}
			case OutmsgMessage.CODE : {
				OutmsgMessage outmsgMessage = OutmsgMessage.parseDatagramPacket(packet);
				Utils.printRecieve(outmsgMessage);
				if(!users.containsKey(outmsgMessage.UID)) {
					System.out.println("Primio sam poruku od nepoznatog korisnika");
					break;
				}
				User user = users.get(outmsgMessage.UID);
				server.addMessageInSendQueue(user,() -> new AckMessage(outmsgMessage.number, user.UID).sendMessage(user, socket),false);
				for(User usersned : users.values()) {
					server.addMessageInSendQueue(usersned, () -> new InmsgMessage(outmsgMessage.text, usersned.numberSend++,user.name).sendMessage(usersned, socket),true);

				}
				break;

			}
			case AckMessage.CODE : {

				AckMessage ack = AckMessage.parseDatagramPacket(packet);
				Utils.printRecieve(ack);
				User user = users.get(ack.UID);
				try {
					ack.checkACK(user.numberRecieve, user.UID);
				}catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					break;
				}
				user.numberRecieve++;
				synchronized (server) {
					server.notifyAll();
				}
				break;
			}
			case ByeMessage.CODE: {
				ByeMessage byeMessage = ByeMessage.parseBytes(packet);
				Utils.printRecieve(byeMessage);
				User user = users.get(byeMessage.UID);
				randkeys.remove(user.randkey);
				users.remove((user.UID));
				server.addMessageInSendQueue(user,() -> new AckMessage(user.numberRecieve, user.UID).sendMessage(user, socket),false);
				break;
				
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + packet.getData()[packet.getOffset()]);
			}
		}
	}

}

package hr.fer.oprpp2.chatapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.function.Function;

import hr.fer.oprpp2.chatapp.Server.User;

public class Utils {

	public static byte[] toByteArrayMessage_Hello(byte code, long number, String message,Long randkey) {
		return toByteArrayMessagePrivate(code, number, message, randkey,null,null);
	}

	public static byte[] toByteArrayMessage_Ack(byte code, long number,Long UID) {
		return toByteArrayMessagePrivate(code, number, null, null,UID,null);
	}
	public static byte[] toByteArrayMessage_Outmsg(byte code, long number,Long UID, String message) {
		return toByteArrayMessagePrivate(code, number, message, null,UID,null);
	}
	public static byte[] toByteArrayMessage_Intmsg(byte code, long number,String ime, String text) {
		return toByteArrayMessagePrivate(code, number, text, null,null,ime);
	}

	private static byte[] toByteArrayMessagePrivate(byte code, long number, String message,Long randkey,Long UID,String ime) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		byte[] buf = null;
		try {
			dos.writeByte(code); //TODO
			dos.writeLong(number); //TODO
			if(UID != null) dos.writeLong(UID);
			if(ime !=  null) dos.writeUTF(ime);
			if(message !=  null) dos.writeUTF(message);
			if(randkey != null) dos.writeLong(randkey);
			buf = bos.toByteArray();
			dos.close();
			bos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf;
	}

	public static DatagramPacket recieveMessage(InetAddress address, int port,DatagramSocket socket) {
		int failCounter = 0;
		while(true) {
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);

			try {
				socket.receive(packet);
			} catch (IOException e) {
				failCounter++;
				try{Thread.sleep(1000); } catch (InterruptedException ignorable) {} {
					if(failCounter > 10) {
						break;
					}
				}
			}
			failCounter = 0;
			return packet;
		}
		return null;
	}

	public static void sendMessage(byte[] message, InetAddress address, int port,DatagramSocket socket) {

		DatagramPacket packet = new DatagramPacket(message, message.length);
		packet.setAddress(address);
		packet.setPort(port);

		try {
			socket.send(packet);
		} catch (IOException e) {
			System.out.println("Upiti se ne može poslati.");
			
		}

	}
	

	public static String parseText(String text) {
		return null;
	}

	public static String getCodeName(byte code) {
		switch (code) {
		case 1: return "HELLO";
		case 2: return "ACK";
		case 3: return "BYE";
		case 4: return "OUTMSG";
		case 5: return "INMSG";
		default:
			throw new IllegalArgumentException("Unexpected value: " + code);
		}
	}

	static interface  FunctionIOException<T,R> {
		R apply(T t) throws IOException;
	}

	public static <T> T parseDatagramPacket(DatagramPacket packet, FunctionIOException<DataInputStream, T> parser) {
		byte[] message = packet.getData();
		int offset = packet.getOffset();
		int length = packet.getLength();

		ByteArrayInputStream in = new  ByteArrayInputStream(message,offset,length);
		DataInputStream dis = new DataInputStream(in);
		
		T data;
		
		try {
			data= parser.apply(dis);
			in.close();
			dis.close();
		}catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		return data;
	}
	
	public static void printRecieve(Object o) {
		System.out.println("Primio sam paket: " + o.toString());
	}
	public static void printSend(Object o) {
		System.out.println("Šaljem paket: " + o.toString());
	}

}

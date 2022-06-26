package hr.fer.oprpp2.chatapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Random;


public class HelloMessage implements Message{

	public static final byte CODE = 1;
	
	String name;
	long number;
	long randkey;
	
	
	public HelloMessage(String name, long number, long randkey) {
		super();
		this.name = name;
		this.number = number;
		this.randkey = randkey;
	}


	public static HelloMessage parseDatagramPacket(DatagramPacket packet) {
		return Utils.parseDatagramPacket(packet, (dis)-> {
			if(dis.readByte() != CODE) throw new IllegalArgumentException("Code ne odgovara vrsti poruke");
			long number = dis.readLong();
			String name = dis.readUTF();
			long randkey = dis.readLong();
			return new HelloMessage(name, number, randkey);
		});
		
	}
	
	@Override
	public void sendMessageInternal(InetAddress address, int port,DatagramSocket socket) {
		byte[] message = Utils.toByteArrayMessage_Hello(CODE, number, name,randkey);
		Utils.sendMessage(message, address, port, socket);
	}
	@Override
	public String toString() {
		return "HelloMessage [name=" + name + ", number=" + number + ", randkey=" + randkey + "]";
	}

}

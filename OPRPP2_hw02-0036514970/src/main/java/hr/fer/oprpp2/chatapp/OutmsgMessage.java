package hr.fer.oprpp2.chatapp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class OutmsgMessage implements Message{
	public static final byte CODE = 4;
	
	long number;
	long UID;
	String text;
	
	
	

	public OutmsgMessage(long number, long uID, String text) {
		super();
		this.number = number;
		UID = uID;
		this.text = text;
	}



	@Override
	public  void sendMessageInternal(InetAddress address, int port, DatagramSocket socket) {
		byte[] message = Utils.toByteArrayMessage_Outmsg(CODE, number,UID, text);
		Utils.sendMessage(message, address, port, socket);
	}
	
	public static OutmsgMessage parseDatagramPacket(DatagramPacket packet) {
		return Utils.parseDatagramPacket(packet, (dis)-> {
			if(dis.readByte() != CODE) throw new IllegalArgumentException("Code ne odgovara vrsti poruke");
			long number = dis.readLong();
			long UID = dis.readLong();
			String text = dis.readUTF();
			return new OutmsgMessage(number, UID, text);
		});

	}




	@Override
	public String toString() {
		return "OutmsgMessage [number=" + number + ", UID=" + UID + ", text=" + text + "]";
	}
	
	
}

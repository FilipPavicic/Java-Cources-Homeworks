package hr.fer.oprpp2.chatapp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ByeMessage implements Message{
	public static final byte CODE = 3;
	
	long number; 
	Long UID;



	public ByeMessage(long number, long uID) {
		super();
		this.number = number;
		UID = uID;
	}
	
	@Override
	public  void sendMessageInternal(InetAddress address, int port, DatagramSocket socket) {
		if(UID == null) return; 
		byte[] message = Utils.toByteArrayMessage_Ack(CODE, number, UID);
		Utils.sendMessage(message, address, port, socket);
	}
	
	public static ByeMessage parseBytes(DatagramPacket packet) {
		return Utils.parseDatagramPacket(packet, (dis)-> {
			if(dis.readByte() != CODE) throw new IllegalArgumentException("Code ne odgovara vrsti poruke");
			long number = dis.readLong();
			long UID = dis.readLong();
			return new ByeMessage(number, UID);
		});

	}

	@Override
	public String toString() {
		return "ByeMessage [number=" + number + ", UID=" + UID + "]";
	}
	
	
}

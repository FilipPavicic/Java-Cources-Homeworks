package hr.fer.oprpp2.chatapp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class AckMessage implements Message{

	public static final byte CODE = 2;

	long number; 
	long UID;



	public AckMessage(long number, long uID) {
		super();
		this.number = number;
		UID = uID;
	}

	@Override
	public void sendMessageInternal(InetAddress address, int port, DatagramSocket socket) {
		byte[] message = Utils.toByteArrayMessage_Ack(CODE, number, UID);
		Utils.sendMessage(message, address, port, socket);
		
	}


	public static AckMessage parseDatagramPacket(DatagramPacket packet) {
		return Utils.parseDatagramPacket(packet, (dis)-> {
			if(dis.readByte() != CODE) throw new IllegalArgumentException("Code ne odgovara vrsti poruke");
			long number = dis.readLong();
			long UID = dis.readLong();
			return new AckMessage(number, UID);
		});

	}


	public void checkACK(long number,Long UID) {

		if(this.number != number) {
			throw new IllegalArgumentException("Dobio sam potvrdu s brojem "+ this.number +", očekvao sam: "+number);
		}
		if(UID != null && this.UID != UID) {
			throw new IllegalArgumentException("Poruka nije namijenjena meni, krivi UID");

		}
	}
		
	

	@Override
	public String toString() {
		return "AckMessage [number=" + number + ", UID=" + UID + "]";
	}


	
	


}

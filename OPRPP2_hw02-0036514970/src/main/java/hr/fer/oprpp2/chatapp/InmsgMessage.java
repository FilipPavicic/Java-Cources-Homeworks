package hr.fer.oprpp2.chatapp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class InmsgMessage implements Message{
	public final static byte CODE = 5;
	
	String text;
	long number;
	String ime;
	
	
	
	
	public InmsgMessage(String text, long number, String ime) {
		super();
		this.text = text;
		this.number = number;
		this.ime = ime;
	}



	public InmsgMessage(String text, long number) {
		super();
		this.text = text;
		this.number = number;
	}



	@Override
		public void sendMessageInternal(InetAddress address, int port, DatagramSocket socket) {
			byte[] message = Utils.toByteArrayMessage_Intmsg(CODE, number, ime, text);
			Utils.sendMessage(message, address, port, socket);
			
		}
	
	
	
	public static InmsgMessage parseDatagramPacket(DatagramPacket packet) {
		return Utils.parseDatagramPacket(packet, (dis)-> {
			if(dis.readByte() != CODE) throw new IllegalArgumentException("Code ne odgovara vrsti poruke");
			long number = dis.readLong();
			String text = dis.readUTF() + " ==>" + dis.readUTF();
			return new InmsgMessage(text, number);
		});

	}


	@Override
	public String toString() {
		return "InmsgMessage [text=" + text + ", number=" + number + "]";
	}







	
	
	
}

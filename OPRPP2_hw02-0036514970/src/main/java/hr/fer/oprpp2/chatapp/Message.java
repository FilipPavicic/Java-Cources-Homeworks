package hr.fer.oprpp2.chatapp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import hr.fer.oprpp2.chatapp.Server.User;

public interface Message {
	public void sendMessageInternal(InetAddress address, int port, DatagramSocket socket) ;
	
	public default void sendMessage(InetAddress address, int port, DatagramSocket socket) {
		 Utils.printSend(this.toString());
		 sendMessageInternal(address, port, socket);
	}
	public default void sendMessage(User user, DatagramSocket socket) {
		 Utils.printSend(this.toString());
		 sendMessageInternal(user.address, user.port, socket);
	}
	
}

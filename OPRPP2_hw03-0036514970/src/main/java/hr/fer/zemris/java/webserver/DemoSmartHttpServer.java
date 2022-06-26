package hr.fer.zemris.java.webserver;

public class DemoSmartHttpServer {
	public static void main(String[] args) {
		new SmartHttpServer("server.properties.txt").start();
	}
}

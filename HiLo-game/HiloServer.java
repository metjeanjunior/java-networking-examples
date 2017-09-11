/**
* Author: Jeannmarie Metelus Jr.
**/

import java.io.*;
import java.net.*;
import java.lang.Math;

public class HiloServer {
	public static void main(String[] args) throws IOException {
		System.out.println("Server is listening for new connection ....");

		DatagramSocket serverSocket = new DatagramSocket(5060);
		System.out.println(serverSocket.getLocalAddress() + ":" + serverSocket.getLocalPort());
		
		String output;
		int sGuess = 1 + (int)(Math.random() * ((10 - 1) + 1));
		int cGuess;
	
		byte[] rbuf = new byte[256];
		DatagramPacket packet = new DatagramPacket(rbuf, rbuf.length);		
		serverSocket.receive(packet);

		ByteArrayInputStream bin = new ByteArrayInputStream(rbuf, 0,
			packet.getLength());
		DataInputStream dis = new DataInputStream(bin);

		String name = dis.readUTF(); // get name;
		InetAddress address = packet.getAddress();
		int port = packet.getPort();

		output = "Hi " + name + ". Let's play the guessing Game.";
		byte[] sbuf = new byte[256];
		sbuf = output.getBytes();
		packet = new DatagramPacket(sbuf, sbuf.length, address, port);
		serverSocket.send(packet);

		// We loop until they guess the correct number
		while (true) {	
			output = "Which number between 1 and 10 am I thiking of ? >>> ";
			sbuf = output.getBytes();
			packet = new DatagramPacket(sbuf, sbuf.length, address, port);
			serverSocket.send(packet);

			rbuf = new byte[256];
			packet = new DatagramPacket(rbuf, rbuf.length);
			serverSocket.receive(packet);

			bin = new ByteArrayInputStream(rbuf, 0, packet.getLength());
			dis = new DataInputStream(bin);

			cGuess = dis.readInt();

			if (cGuess == sGuess) {
				output = "YOUR GUESS WAS CORRECT!!!!";
				sbuf = output.getBytes();
				packet = new DatagramPacket(sbuf, sbuf.length, address, port);
				serverSocket.send(packet);
				break;
			} else if (cGuess > sGuess) 
				output = "Too High :( Guess Again";
			else
				output = "Too Low :( Guess Again";

			sbuf = output.getBytes();
			packet = new DatagramPacket(sbuf, sbuf.length, address, port);
			serverSocket.send(packet);
			
			
		}
		serverSocket.close();
		System.out.println("Goodbye");
	}
}

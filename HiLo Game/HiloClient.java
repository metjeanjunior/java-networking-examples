/**
* Author: Jeannmarie Metelus Jr.
**/

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HiloClient {
	public static void main(String argv[]) throws IOException {
		String name, input, hostname;; 
		int number;
		// Should really be another arg
		int port = 5060;

		if (argv.length > 0)
            hostname = argv[0];
        else
            hostname = "localhost";
		
		DatagramSocket clientSocket = new DatagramSocket();	 
		InetAddress address = InetAddress.getByName(hostname);
		//int port = Integer.parseInt(argv[1]);
		
		System.out.print("please enter your name >>> ");  
		BufferedReader keyboard = new BufferedReader( new InputStreamReader( System.in ) );
		
		name = keyboard.readLine();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream (baos);
		dos.writeUTF (name); 
		byte[] buf= baos.toByteArray(); 
		DatagramPacket packet = new DatagramPacket(buf,buf.length, address, port); 
		clientSocket.send(packet); 
		
		//Greeting from server
		byte[] rbuf = new byte[256];	
		rbuf = new byte[256];	
		packet = new DatagramPacket(rbuf,rbuf.length);
		clientSocket.receive(packet);
				
		ByteArrayInputStream bin = new ByteArrayInputStream (packet.getData(), 0, packet.getLength() );
		BufferedReader reader = new BufferedReader (new InputStreamReader(bin));
		input = reader.readLine();
		System.out.println(input);
		
		while (true) {
			rbuf = new byte[256];	
			packet = new DatagramPacket(rbuf,rbuf.length);
			clientSocket.receive(packet);
			
			bin = new ByteArrayInputStream (packet.getData(), 0, packet.getLength() );
			reader = new BufferedReader (new InputStreamReader(bin));
			input = reader.readLine();
			System.out.print(input);
			
			// Gets guess
			keyboard = new BufferedReader(new InputStreamReader(System.in));
			number = Integer.parseInt(keyboard.readLine());
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream (baos);
			dos.writeInt (number);		
			buf= baos.toByteArray();
			
			packet = new DatagramPacket(buf,buf.length, address, port);
			clientSocket.send(packet); 
			
			rbuf = new byte[256];	
			packet = new DatagramPacket(rbuf,rbuf.length);			
			clientSocket.receive(packet); 
			
			bin = new ByteArrayInputStream (packet.getData(), 0, packet.getLength() );
			reader = new BufferedReader (new InputStreamReader ( bin ) );
			input = reader.readLine();
			System.out.println (input);

			if (input.compareTo("YOUR GUESS WAS CORRECT!!!!") == 0)
				break;
		}
		
		System.out.println("Goodbye!");
		clientSocket.close();
	}

}

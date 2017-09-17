import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.io.*; // for IOException and Input/OutputStream

public class HiloServer {

	private static final int BUFSIZE = 256; // Size of receive buffer

	public static void main(String[] args) throws IOException {

		if (args.length != 1) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port>");

		int servPort = Integer.parseInt(args[0]);

		// Create a server socket to accept client connection requests
		ServerSocket serverSocket = new ServerSocket(servPort);
		System.out.println("Server is listening for new connection at " + serverSocket.getLocalSocketAddress() + " ....");

		byte[] rbuf = new byte[BUFSIZE];

		int msgSize; // Size of received message
		
		while (true) { // Run forever, accepting and servicing connections
			String output;
			int sGuess = 1 + (int)(Math.random() * ((10 - 1) + 1));
			int cGuess;

			Socket clientSocket = serverSocket.accept(); // Get client connection

			SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
			System.out.println("Handling client at " + clientAddress);

			InputStream in = clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();

			msgSize = in.read(rbuf);
			output = "Hi " + new String(rbuf, 0, msgSize) + ". Let's play the guessing Game.";
			out.write(output.getBytes());

			while (true) {
				output = "Which number between 1 and 10 am I thiking of ? >>> ";
				out.write(output.getBytes());

				msgSize = in.read(rbuf);
				cGuess = Integer.parseInt(new String(rbuf, 0, msgSize));

				if (cGuess == sGuess) {
					output = "YOUR GUESS WAS CORRECT!!!!";
					out.write(output.getBytes());
					break;
				} else if (cGuess > sGuess) 
					output = "Too High :( Guess Again";
				else
					output = "Too Low :( Guess Again";

				out.write(output.getBytes());
				rbuf = new byte[BUFSIZE]; // Receive buffer
			}

			System.out.println("Connection with client at " + clientSocket.getRemoteSocketAddress() + " terminated");
			clientSocket.close(); // Close the socket. We are done with this client!
			
		}
		/* NOT REACHED */
	}
}

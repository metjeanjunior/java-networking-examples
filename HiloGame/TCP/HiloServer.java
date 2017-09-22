import java.net.*; // for Socket, ServerSocket, and InetAddressi
import java.io.*; // for IOException and Input/OutputStream

public class HiloServer {

	private static final int BUFSIZE = 256; // Size of receive buffer

	public static void main(String[] args) throws Exception {

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

			InputStream inputStream = clientSocket.getInputStream();
			ObjectInputStream objectInStream = new ObjectInputStream(inputStream);
			OutputStream outputStream = clientSocket.getOutputStream();
			ObjectOutputStream objectOutStream = new ObjectOutputStream(outputStream);

			HiloComData comData = (HiloComData) objectInStream.readObject();
			comData.setLoHi(1,10);
			objectOutStream.writeObject(comData);

			while (true) {
				comData = (HiloComData) objectInStream.readObject();
				cGuess = comData.getGuess();

				if (cGuess == sGuess) {
					comData.setCorrect();
					objectOutStream.writeObject(comData);
					break;
				} else if (cGuess > sGuess) 
					comData.setLoHiBool(false);
				else
					comData.setLoHiBool(true);

				objectOutStream.writeObject(comData);
			}

			System.out.println("Connection with client at " + clientSocket.getRemoteSocketAddress() + " terminated");
			clientSocket.close(); // Close the socket. We are done with this client!
			
		}
	}
}

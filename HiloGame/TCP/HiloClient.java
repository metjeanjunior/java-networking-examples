import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class HiloClient {

	public static void main(String[] args) throws IOException {

		if ((args.length != 2) ) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		}

		String server = args[0]; // Server name or IP address
		// Convert argument String to bytes using the default character encoding

		BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); // from
																							// keyboard
		InetAddress serverAddress = InetAddress.getByName(args[0]); // Server address
		int servPort = Integer.parseInt(args[1]); // get port number

		String clientInput; // from user
		String echoString; // echo from server
		Socket socket = null;
		// Create socket that is connected to server on specified port
		socket = new Socket(server, servPort);

		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();

		int msgSize;
		String serverResponse;

		System.out.print("please enter your name >>> ");
		clientInput = fromKeyboard.readLine();
		out.write(clientInput.getBytes());

		//Greeting from serverAddress
		byte[] rbuf = new byte[256];
		msgSize = in.read(rbuf);
		System.out.println(new String(rbuf, 0, msgSize));


		while (true) {
			rbuf = new byte[256];
			msgSize = in.read(rbuf);
			System.out.print(new String(rbuf, 0, msgSize));

			// Gets guess
			clientInput = fromKeyboard.readLine();

			if (clientInput.equals("exit")) // user want to stop having fun with the server
				break;

			try {
				Integer.parseInt(clientInput);
			}
			catch (Exception e) {
				while (true) {
					System.out.print("Stop Trolling and enter an actual number>>> ");
					
					clientInput = fromKeyboard.readLine();
					try {
						Integer.parseInt(clientInput);
						break;
					}
					catch (Exception es) {
					}
				}

			}

			out.write(clientInput.getBytes());

			rbuf = new byte[256];
			msgSize = in.read(rbuf);
			serverResponse = new String(rbuf, 0, msgSize);
			System.out.println(serverResponse);

			if (serverResponse.compareTo("YOUR GUESS WAS CORRECT!!!!") == 0)
				break;
		}

		System.out.println("Goodbye!");
		socket.close(); // Close the socket and its streams
	}
}

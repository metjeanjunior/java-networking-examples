import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.io.*;

public class HiloClient {

	// This is a bad example
	// We Should use try-catch so client does not see exceptions but...
	public static void main(String[] args) throws Exception {

		if ((args.length != 2) ) { // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		}

		String server = args[0]; // Server name or IP address

		InetAddress serverAddress = InetAddress.getByName(args[0]); // Server address
		int servPort = Integer.parseInt(args[1]); // get port number

		BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); 
		String clientInput; // from user
		Socket socket = null;

		System.out.print("please enter your name >>> ");
		clientInput = fromKeyboard.readLine();

		// Create socket that is connected to server on specified port
		socket = new Socket(server, servPort);

		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutStream = new ObjectOutputStream(outputStream);


		HiloComData comData = new HiloComData(clientInput);
		objectOutStream.writeObject(comData);

		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInStream = new ObjectInputStream(inputStream);

		//Greeting from serverAddress
		comData = (HiloComData) objectInStream.readObject();
		System.out.println("Hi " + comData.getName() + ". Let's play the guessing Game.");

		int lo = comData.getLo();
		int hi = comData.getHi();

		while (true) {
			System.out.print("Which number between " + lo + " and " + hi + " am I thinking of ? >>> ");

			// Gets guess
			clientInput = fromKeyboard.readLine();

			if (clientInput.equals("exit")) // user want to stop having fun with the server
				break;

			// Sanitize input, if user commits id=10t
			try {
				comData.setGuess(Integer.parseInt(clientInput));
			}
			catch (Exception e) {
				while (true) {
					System.out.print("Stop Trolling and enter an actual number>>> ");
					
					clientInput = fromKeyboard.readLine();
					try {
						comData.setGuess(Integer.parseInt(clientInput));
						break;
					}
					catch (Exception es) {
					}
				}

			}

			objectOutStream.writeObject(comData);

			comData = (HiloComData) objectInStream.readObject();

			if (comData.isCorrect())
			{
				System.out.println("YOUR GUESS WAS CORRECT!!!!");
				break;
			}
			else if (comData.isLo())
				System.out.println("Too Low :( Guess Again");
			else
				System.out.println("Too High :( Guess Again");
		}

		System.out.println("Goodbye!");
		socket.close(); // Close the socket and its streams
	}
}

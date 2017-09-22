### This is a simple high low game between a server and a client

* It uses UDP as its underlying protocol. Here is a [TCP](../TCP) version doing the same thing.

* if no host address (IP) is provided, we assume localhost

* I used port 5060 because why not...

* This is simple. Yes I ought to thread this so that I could support multiple games at once. This is not implemented purposely.

* This uses strings as a means of communication between client and server. A protocol using Serializable objects can be found [here](../TCP/)

* An exit command for the client would be nice but nope, you can't leave till you're right.
 * If you really wanted that, add something similar to these lines on the server side:
  ```java
  else if cGuess.compareIgnoreCase('exit')== 0:
  {
	  output = "Thanks for playing the guessing game. But you're a quitter";
	  sbuf = output.getBytes();
	  packet = new DatagramPacket(sbuf, sbuf.length, address, port);
	  serverSocket.send(packet);
	  break;
 }
 ```
 and on the client side the simplest thing is to add a `try-catch` as demonstrated [here](../TCP/HiloClient.java#L60) to  sanitize your input in case `keyboard.readLine().compareTo("exit") == 0` because `number = Integer.parseInt(keyboard.readLine());` will definitely scream at you.

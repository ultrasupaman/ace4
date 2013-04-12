

import java.io.*;
import java.net.*;
import java.util.*;

//import ace2.Message;

public class ChatServer {
  
	private static ArrayList<MessageTriple> history = new ArrayList<MessageTriple>();
	
	public static void main(String[] args) throws Exception{

		int port = 4000;		//Set the port number.

		ServerSocket sock = new ServerSocket(port); 		 //Establish the listen socket.

		while (true) {			//Process HTTP service requests in an infinite loop.
			Socket newRequest = sock.accept();					//Listen for a TCP connection request.			
			
			ChatRequest request = new ChatRequest(newRequest);	 //Construct an object to process the HTTP request message.
			
			Thread thread = new Thread(request); //Create a new thread to process the request.
			
			thread.start();		//Start the thread.
		}

	}

	static class ChatRequest implements Runnable {

		private Socket socket;
		private boolean clientConnected;
		private int currentPoint;
		
		//Constructor
		public ChatRequest(Socket socket) throws Exception {
			this.socket = socket;
			clientConnected = true;
			currentPoint = 0;
		}
		
		//Implement the run() method of the Runnable interface.
		public void run() {
			try {
				processRequest();
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}
		
		private void processRequest() throws Exception {
			//Get a reference to the socket's input and output steams.
			InputStream is = socket.getInputStream();
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());
			
			//Set up input stream filters.
			Scanner scan = new Scanner(is);
			
			while(clientConnected)
			{
				MessageUpdater updater = new MessageUpdater(os, currentPoint);
				Thread messageUpdates = new Thread(updater);
				messageUpdates.start();
				try {
					getMessage(scan);
				} catch (NoSuchElementException e) {
					clientConnected = false;
				}
			}
			System.out.println("Client has disconnected.");
			printHistory();
			//Close streams and socket.
			os.close();
			scan.close();
			socket.close();
		}
		
		private void printHistory()
		{
			System.out.println("\n++++++++++++++++Chat History++++++++++++++++");
			for(MessageTriple message : history)
			{
				System.out.println("User: " + message.getUser());
				System.out.println("Time: " + message.getTime());
				System.out.println("Message: " + message.getMessage());
				System.out.println();
			}
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++\n");
		}
		
		public void getMessage(Scanner scan)
		{
			
//			System.out.println("Got to interpreting the message."); //DEBUG
			String statusLine = scan.nextLine();
			
//			System.out.println("Status line is: " + statusLine); //DEBUG			
			
			StringTokenizer tokens = new StringTokenizer(statusLine, "#");
			String username = tokens.nextToken();
			String timeStamp = tokens.nextToken();
			
//			System.out.println("Got the username & timestamp."); //DEBUG
			
			String message = "";
			message = scan.nextLine();
			System.out.println("Message: " + message);
			
			history.add(new MessageTriple(username, timeStamp, message));
			
		}
				
	}

	static class MessageUpdater implements Runnable 
	{			
		DataOutputStream os;
		int currentPoint;
		
		public MessageUpdater(DataOutputStream os, int currentPoint)
		{
			this.os = os;
			this.currentPoint = currentPoint;
		}
		
		public void run()
		{
			while(true)
			{
				if(history.size() > currentPoint)
				{
					for(MessageTriple msg : history.subList(currentPoint, history.size()))
					{
						printMessage(os, msg);
					}
					currentPoint = history.size();
				}
			}
		}
		
		private void printMessage(DataOutputStream os, MessageTriple msgTrip)
		{
//			System.out.println("Writing the bytes out again."); //DEBUG
			//Write the username.
			try {
				os.writeBytes(msgTrip.getUser() + ":");
			
				// Send the message line.
				os.writeBytes(msgTrip.getMessage() + "\n");
			} catch (IOException e) {
				System.out.println("Couldn't write the message back to client.");
			}			
		}
	}
}

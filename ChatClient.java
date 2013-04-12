
/* Work in progress.
 * Don't expect it to 
 * do anything or be 
 * correct.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;


public class ChatClient {
  
	String username = "User1";
	PrintWriter pout;
	InputStream inputStream;
	InputStreamReader sr;
	Scanner inputReader;
	public void keepChatting(){
		OutputStream out = null;
		Socket sock = null;
		
		
		BufferedReader streamReader;
		inputReader = new Scanner(System.in);
		
				//This is hard coded currently!
		try {
			//A new connection is made
			sock = new Socket("127.0.0.1", 4000);
			
			//An instance of PrintWriter is created for passing the string to the server
			out = sock.getOutputStream();
			pout = new PrintWriter(out, true);
			pout.println(username + " " + Calendar.getInstance().getTime());
			
			System.out.println("Enter your message:");
			pout.println(inputReader.nextLine());
			
			//Getting the returned message from the server
			inputStream = sock.getInputStream();
			sr = new InputStreamReader(inputStream);
			streamReader = new BufferedReader(sr);
			//System.out.println("Returned output was: " + streamReader.readLine());
			
			sock.close();
			
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}

	public static void main(String[] args) {
		ChatClient cc = new ChatClient();
		cc.keepChatting();
	}
	
	public String getUserName(){
		return username;
	}

}

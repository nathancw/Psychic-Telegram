import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Waiter {

	 public static void main(String args[]) {
		  
		  Waiter waiter = new Waiter();
		  
	  }
	 
	 static final int PORT = 4921;
	 DataInputStream din;
	 DataOutputStream dout;
	 BufferedReader in;
	 
	 public Waiter(){
		 
		 
		Socket serverSocket;
		try {
			serverSocket = new Socket("localhost",PORT);
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			
			System.out.println("Connected to localhost in port 4921 - Waiter");
			PrintWriter out = new PrintWriter(serverSocket.getOutputStream(),true);
			out.println(1);
			
			//Loop through
			String readLine;
			while(!((readLine = waitForServerResponse()).equals("Exit"))){
				System.out.println("Waiter read: " + readLine);
			}
			System.out.println("Waiter Exiting!");
			
			
			
		} catch (UnknownHostException e) {
	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
		
	 }
	 
	 public String waitForServerResponse() throws IOException{
		 
		 String readLine;
		 while(((readLine = in.readLine()) != null)){
			 		return readLine;
		 }
		 return "";
		 
	 }

}

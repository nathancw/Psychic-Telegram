import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Chef {
	 static final int PORT = 4921;

	 BufferedReader in;
	 PrintWriter out;
	 
	 public static void main(String args[]) {
	        Chef chef = new Chef();
	 }
	 
	 
	 public Chef(){
		 
		 
		Socket serverSocket;
		try {
			serverSocket = new Socket("localhost",PORT);

			System.out.println("Connected to localhost in port 4921 - Chef");
			out = new PrintWriter(serverSocket.getOutputStream(),true);
			out.println("Chef");
			
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			
			String readLine; 
			while(!((readLine = waitForServerResponse()).equals("Exit"))){				
				System.out.println("Chef read: " + readLine);
				handle(readLine);
			}
			
			System.out.println("Chef exiting!");
		
			
			
			
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
	 
	 public void handle(String str){
		 
		 if(str.charAt(0) == 'T'){
				System.out.println("Sending the order to the waiter.");
				out.println("Waiter,"+str);
			}
			
		 
	 }
}

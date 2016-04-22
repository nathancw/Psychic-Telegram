import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Customer {
	  Socket serverSocket;
	  static final int PORT = 4921;
	  BufferedReader in;
	  
	  public static void main(String args[]) {
	        Customer cust = new Customer();
	 }
	
	  public Customer(){
		  
		  try {
			  
				Socket serverSocket = new Socket("localhost",PORT);
				in= new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				System.out.println("Connected to localhost in port 4921 - Customer");
				
				System.out.println("Outputting order to the server.");

				PrintWriter out = new PrintWriter(serverSocket.getOutputStream(),true);

				out.println(2);
				String orderValues = "Table:54 Order: Chicken";
				out.println(orderValues);
				System.out.println("Order done writing to the server.");
				
				String readLine = waitForServerResponse();
				System.out.println("Customer read: " + readLine);
				

				
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

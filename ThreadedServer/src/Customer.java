import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Customer {
	  Socket serverSocket;
	  static final int PORT = 4921;
	
	  public Customer(){
		  
		  try {
				Socket serverSocket = new Socket("localhost",PORT);
				System.out.println("Connected to localhost in port 4921");
				
				System.out.println("Outputting order to the server.");
				DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream());
				//BufferedReader brOut = new BufferedReader(new OutputStream(serverSocket.getOutputStream()));
				String order = "Order";
				dOut.writeUTF(order);
				String orderValues = "Chicken!!!!!!!!!!!!!!!!!!!!!!";
				dOut.writeUTF(orderValues);
				
			
				System.out.println("Order done writing to the server.");
				
				dOut.close();
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	  }
	
	
}

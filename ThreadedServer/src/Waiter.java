import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Waiter {

	 public static void main(String args[]) {
		  
		  
		  
	  }
	 
	 static final int PORT = 4921;
	 DataInputStream din;
	 DataOutputStream dout;
	 public Waiter(){
		 
		 
		Socket serverSocket;
		try {
			serverSocket = new Socket("localhost",PORT);

			System.out.println("Connected to localhost in port 4921 - Waiter");
			DataOutputStream dout = new DataOutputStream(serverSocket.getOutputStream());
			dout.writeInt(1);
			
			din = new DataInputStream(serverSocket.getInputStream());
			
			while(din.available() > 0){
			String order = din.readUTF();
			System.out.println("Waiter: Got order: " + order);
			}
		} catch (UnknownHostException e) {
	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
		
	 }
}

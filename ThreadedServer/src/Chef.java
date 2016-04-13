import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Chef {
	 static final int PORT = 4921;
	 DataInputStream din;
	 DataOutputStream dout;
	 public Chef(){
		 
		 
		Socket serverSocket;
		try {
			serverSocket = new Socket("localhost",PORT);

			System.out.println("Connected to localhost in port 4921 - Chef");
			DataOutputStream dout = new DataOutputStream(serverSocket.getOutputStream());
			dout.writeInt(0);
			
			din = new DataInputStream(serverSocket.getInputStream());
			
			while(din.available() > 0){
			String order = din.readUTF();
			System.out.println("Chef: Got order: " + order);
			}
		} catch (UnknownHostException e) {
	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
		
	 }
}

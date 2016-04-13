import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    static final int PORT = 4921;
    static Socket[] serverConnections = new Socket[4];
    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for connection.");
        } catch (IOException e) {
            e.printStackTrace();

        }
        
        //Run all the clients because its hilarious and easy
        Chef chef = new Chef();
        Waiter wait = new Waiter();
        Customer cust = new Customer();
        ///////////////////////////////////////////////////////////
        
        
        int count = 0;
        while (true) {
            try {
                socket = serverSocket.accept();
               System.out.println("Connection received from " + serverSocket.getInetAddress().getHostName() + ".");
               serverConnections[count] = socket;
               count++;
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new threa for a client
            new ServerThread(socket,count).start();
        }
    }
    

}

class ServerThread extends Thread {
    protected Socket socket;
    protected int number;
    public ServerThread(Socket clientSocket, int num) {
        this.socket = clientSocket;
        this.number = num;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        DataInputStream din = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            din = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String order;
        String orderValues;
        
        try {
			while (din.available() > 0 ) {
			    try {
			
			    	order= din.readUTF();
			    	orderValues = din.readUTF();
			    	
			        System.out.println("Read line: " + orderValues);
			        if(number == 2 && order.equals("Order")){
			    	   //Zero is chef, 2 is customer, and 1 is Waiter. So send the chef the order
			        	//out = new DataOutputStream(serverConnections[0].getOuputStream());
			        }
			        
			    } catch (IOException e) {
			        e.printStackTrace();
			        return;
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
}
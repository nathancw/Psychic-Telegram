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
    ServerSocket serverSocket;
    Socket socket;
    ServerThread serverThreads[];
    		
    public static void main(String args[]) {
    	Server server = new Server();
    }
    
  
    public Server() {
        serverSocket = null;
        socket = null;

        //Mention the amount of threads we want
        serverThreads = new ServerThread[4];
        
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for connection. - Server");
        } catch (IOException e) {
            e.printStackTrace();

        }
             
        int count = 0;
        DataInputStream din;
        while (true) {
            try {
            	//Accept the connection
                socket = serverSocket.accept();   
                
                din = new DataInputStream(socket.getInputStream());
                int num = din.readInt();
                System.out.println("Connection received from " + serverSocket.getInetAddress().getHostName() + ". and value recieved was: " + num);
                //Create the new thread
                serverThreads[count] = new ServerThread(this,socket);
                serverThreads[count].setNumber(num);
                serverThreads[count].start();
                System.out.println("serverThreads[" + count  +"] created" );
                //Open the input stream and read in which type it is
                
                	
                
               count++;
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                e.printStackTrace();
            }
         
        }
    }
    
    public void handle(String str){
    	serverThreads[1].send("Hello from Server");
    	if(true){
    		System.out.println("Sending the order to the chef.");
    		serverThreads[0].send(str);
    		
    	}
    	
    }
    

}

class ServerThread extends Thread {
	
    protected Socket socket;
    protected int number;
    DataOutputStream out;
    DataInputStream din;
    Server server;
    
    public ServerThread(Server serv, Socket clientSocket) {
        this.socket = clientSocket;
        this.server = serv;
        number = 1234567;

        try {
			din = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void send(String str) {
		try {
			out.writeUTF(str);
			out.flush();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}

	public void run() {
  
        String order;
        String orderValues;
        System.out.println("Running thread: " + number);
        try {
			while (din.available() > 0 ) {
			    try {
			    	
			    	orderValues = din.readUTF();
			    	
			        System.out.println("Read line: " + orderValues + " inside thread: " + number);
			        server.handle(orderValues);
			        out.writeUTF(orderValues);
			        
			    } catch (IOException e) {
			        e.printStackTrace();
			        return;
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    public DataOutputStream getOutputStream(){
    	return out;
    }
    public DataInputStream getInputStream(){
    	return din;
    }
    public void setNumber(int num){
    	this.number = num;
    }
}
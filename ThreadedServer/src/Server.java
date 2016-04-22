import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        BufferedReader in;
        while (true) {
            try {
            	//Accept the connection
                socket = serverSocket.accept();   
                
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int num = Integer.parseInt(in.readLine());
                System.out.println("Connection received from " + serverSocket.getInetAddress().getHostName() + ". and value recieved was: " + num);
                //Create the new thread
                serverThreads[count] = new ServerThread(this,socket);
                serverThreads[count].setNumber(num);
                serverThreads[count].start();
                System.out.println("serverThreads[" + count  +"] created" );
                     
               count++;
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
                e.printStackTrace();
            }
         
        }
    }
    
    public void handle(String str){
    	if(str.charAt(0) == 'W'){
    		serverThreads[1].send(str);
    		serverThreads[1].send("Exit");
    	}
    	
    	else if(str.charAt(0)== 'T'){
    		System.out.println("Sending the order to chef and table to waiter.");
    		serverThreads[0].send(str);
    		
    		serverThreads[1].send(str.substring(0,8));
    	}
    	
    }
    

}

class ServerThread extends Thread {
	
    protected Socket socket;
    protected int number;
    PrintWriter out;
	BufferedReader in;
	Server server;
    
    public ServerThread(Server serv, Socket clientSocket) {
        this.socket = clientSocket;
        this.server = serv;
        number = 1234567;

        try {
        	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void send(String str) {
		System.out.println("Sending: " + str + " to: " + out);
		out.println(str);
		out.flush();
		
	}

	public void run() {
  
        String line;
     
        System.out.println("Running thread: " + number);
        try {
			while (((line = in.readLine()) != null)) {

			        System.out.println("Server read: " + line + " inside thread: " + number);
			        server.handle(line);

			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    public PrintWriter getOutputStream(){
    	return out;
    }
    public BufferedReader getInputStream(){
    	return in;
    }
    public void setNumber(int num){
    	this.number = num;
    }
}
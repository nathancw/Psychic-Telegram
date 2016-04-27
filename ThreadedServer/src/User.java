import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class User {
	  Socket serverSocket;
	  static final int PORT = 4921;
	  int delta = 123456;
	  DataInputStream in;
	  byte[] secretKey;
	  double m;
	  String hmac = "HmacSHA1";
	  int t = 45;
	  int value;
	  
	  public static void main(String args[]) {
	        User user = new User();
	 }
	
	  public User(){
		  
		  Random rand = new Random(delta);
		  value = rand.nextInt();
			  
		  try {
			  
				serverSocket = new Socket("localhost",PORT);
				
				in= new DataInputStream(serverSocket.getInputStream());
				
				System.out.println("Connected to localhost in port 4921 - User");
				
				//Read in the key the trusted authority sends
				int length = in.readInt();
				secretKey = new byte[length];
				in.read(secretKey);
				
				//Change it to the actual secret key
				SecretKeySpec signingKey = new SecretKeySpec(secretKey,hmac);
				
				m = Math.pow(2,(Math.log(2 * delta)/Math.log(2)));
				
				Mac mac = Mac.getInstance(hmac);
				mac.init(signingKey);
				
				byte[] hmacResult = mac.doFinal(ByteBuffer.allocate(4).putInt(t).array());
				
				System.out.println("hmac result: " + hmacResult);
				
				//Change hmacresult ot double so we can mod?
				//BigInteger HMAC = new BigInteger(hmacResult);
				double HMAC = ByteBuffer.wrap(hmacResult).getDouble();
				
				//computer k0
				double userKey = HMAC % m;
				
				System.out.println("User key: " + userKey);
				
				SecretKeySpec userSecretKey = new SecretKeySpec(ByteBuffer.allocate(8).putDouble(userKey).array(),"AES/ECB/NoPadding");
				
				System.out.println(userSecretKey);
				
				Cipher AESencrypt = Cipher.getInstance("AES/ECB/NoPadding");
				AESencrypt.init(Cipher.ENCRYPT_MODE, userSecretKey);
				
				byte[] cipherText = AESencrypt.doFinal(ByteBuffer.allocate(4).putInt(value).array());
				
				DataOutputStream out = new DataOutputStream(serverSocket.getOutputStream());
				out.writeInt(cipherText.length);
				out.write(cipherText);
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
	  }
	  

}

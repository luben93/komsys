import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class client {
	 BufferedReader in;
	public client() {
		try {
			System.out.println("Server: 222222");
			    Socket kkSocket = new Socket("localhost",8080);
			    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
			    in = new BufferedReader(
			        new InputStreamReader(kkSocket.getInputStream()));
			    System.out.println("Server: ");
		 BufferedReader stdIn =
	                new BufferedReader(new InputStreamReader(System.in));
	            String fromServer;
	            String fromUser;

	            while ((fromServer = in.readLine()) != null) {
	                System.out.println("Server: " + fromServer);
	                if (fromServer.equals("Bye."))
	                    break;
	                
	                fromUser = stdIn.readLine();
	                if (fromUser != null) {
	                    System.out.println("Client: " + fromUser);
	                    out.println(fromUser);
	                }
	            }
	        } catch (UnknownHostException ie) {
	            System.err.println("Don't know about host " + "localhost");
	            System.exit(1);
	        } catch (IOException ei) {
	            System.err.println("Couldn't get I/O for the connection to " +
	            		"localhost");
	            System.exit(1);
	        }
	}

}
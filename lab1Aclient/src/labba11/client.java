package labba11;
import java.io.*;
import java.net.*;
public class client {
	
	    public static void main(String[] args) throws IOException {
//TODO Lägg till att när socket timeout sker på server sidan så ska även klienten stängas ner
	    	
	        String serverHostname = "130.229.186.57";
	        int port = 1234;

	        Socket echoSocket = null; PrintWriter out = null; BufferedReader in = null;

	        try {
	            echoSocket = new Socket(serverHostname, port);
	            out = new PrintWriter(echoSocket.getOutputStream(), true);
	            in = new BufferedReader(new InputStreamReader(
	                                        echoSocket.getInputStream()));
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host: " + serverHostname);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for "
	                               + "the connection to: " + serverHostname);
	            System.exit(1);
	        }
	    System.out.println("You are now connected!");  
		BufferedReader stdIn = new BufferedReader(
	                                   new InputStreamReader(System.in));
		String userInput;

	        System.out.print ("> ");
		while ((userInput = stdIn.readLine()) != null) {
		    out.println(userInput);
		    System.out.println("echo: " + in.readLine());
	            System.out.print ("> ");
		}
		  
		out.close(); in.close(); stdIn.close(); echoSocket.close();
	    }
	}




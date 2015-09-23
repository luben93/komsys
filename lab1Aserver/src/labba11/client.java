package labba11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class client {

    public static void main(String[] args) throws IOException {
            /*
//TODO L�gg till att n�r socket timeout sker p� server sidan s� ska �ven klienten st�ngas ner
	    	
	        String serverHostname = "52.16.132.171";
	        int port = 1234;

	        Socket echoSocket = null; PrintWriter out = null; BufferedReader in = null;

	        try {
	            echoSocket = new DatagramSocket(serverHostname, port);
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
		*/

        while (true) {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + modifiedSentence);
            clientSocket.close();
        }
    }


}




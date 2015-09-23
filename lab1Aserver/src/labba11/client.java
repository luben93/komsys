package labba11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class client {

    public static void main(String[] args) throws IOException {
           
		String ip="130.229.182.139";
		ip="localhost";
		System.out.println("ip: "+ip);

		while (true) {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket();
			clientSocket.setSoTimeout(2000);
            InetAddress IPAddress = InetAddress.getByName(ip);
			byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
			System.out.print(">");
			String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 1234);
			clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("<" + modifiedSentence);
            clientSocket.close();
        }
    }


}




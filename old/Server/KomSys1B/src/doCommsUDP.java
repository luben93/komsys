import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class doCommsUDP implements Runnable, arrayClients {
	public static final int PORT = 4950;
	public static final int MAXBUF = 1024;
	public static ArrayList<DatagramSocket> data_sock = new ArrayList<DatagramSocket>();
	DatagramPacket packet = null;
	DatagramSocket sock = null;
	
	public doCommsUDP() {

	}

	public void run() {
		try {
			sock = new DatagramSocket(PORT);

			String message = "";
			boolean sendEcho = true;

			while (sendEcho) {

				byte[] data = new byte[MAXBUF];
				packet = new DatagramPacket(data, data.length);
				System.out.println("Server waiting for message...");
				
				sock.receive(packet);
				
				message = new String(packet.getData()).trim();
				InetAddress clientAddr = packet.getAddress();
				data_sock.add(sock);
				System.out.println("Packet received from "
						+ clientAddr.getHostName());
				System.out.println("Packet contains \"" + message + "\"");
				
				String[] returnValues;
				
				Commands comm = new Commands(packet);
				returnValues = comm.processInput(message);
				
				if(returnValues[1].equalsIgnoreCase("sendToAll")){
					sendToAll(returnValues[0].getBytes());
				}
				else{
					sendToMe(returnValues[0].getBytes());
				}
			}

		} catch (IOException ie) {
			System.out.println(ie.toString());
		} finally {
			sock.close(); // Don't forget...
		}
	}

	private void sendToAll(byte[] data) {
		try {
	
			for (int i = 0; i < array_of_ports.size(); i++) {
				packet = new DatagramPacket(data, data.length, array_of_clients_inet.get(i),
						array_of_ports.get(i));
				data_sock.get(i).send(packet);
				
			}
		} catch (Exception e) {

		}
	}
	
	private void sendToMe(byte[] data) {
		try {	
			packet = new DatagramPacket(data, data.length, packet.getAddress(),
					packet.getPort());
			sock.send(packet);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

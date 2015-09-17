import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDP {
	public static int SERVER_PORT = 0;
	int hej = 13;
	DatagramSocket sock = null;
	InetAddress iaddr = null;
	private Receiver receiver;
	public UDP(int serverPort, DatagramSocket sock) {
		this.sock = sock;
		this.SERVER_PORT = serverPort;
		try {
			iaddr = InetAddress.getLocalHost();
			
			BufferedReader stdIn =
	                new BufferedReader(new InputStreamReader(System.in));
			
			// Create a receiver object with a separate thread.
			receiver = new Receiver(sock);
			receiver.start();
			send("ENTER ROOM");
			while(true){
				String fromUser = stdIn.readLine();
				send(fromUser);
			}
		} catch (Exception e) {

		}
	}

	
	public void send(String msg) {

		byte[] data = msg.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, iaddr,
				SERVER_PORT);
		
		if (msg.equalsIgnoreCase("/quit"))
			cleanUpAndExit();
		try {
			sock.send(packet);
		} catch (IOException ie) {
		}
	}
	private void cleanUpAndExit() {
		sock.close();
		receiver.stop();
		System.exit(0);
	}
	class Receiver implements Runnable {
		private Thread activity = null;
		private DatagramSocket so;

		Receiver(DatagramSocket so) {
			this.so = so;
		}

		void start() {
			if (activity == null) {
				activity = new Thread(this);
				activity.start();
			}
		}

		void stop() {
			activity = null;
		}

		public void run() {
			byte[] data = new byte[1024];

			try {
				while (Thread.currentThread() == activity) {
					
					DatagramPacket packet = new DatagramPacket(data,
							data.length);
					so.receive(packet);
					String msg = new String(data, 0, packet.getLength()).trim(); // Extract
							System.out.println(msg);														// msg
				}
			} catch (IOException ie) {
			} finally {
				so.close();
				System.out.println("Stopped receiving");
			}
		}

	}

}

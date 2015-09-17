import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCP implements Runnable {
	String fromUser = null;
	public TCP(String fromUser) {
		this.fromUser = fromUser;
	}

	public void run() {
		Socket kkSocket = null;
		try {
			
			String fromServer;
			kkSocket = new Socket(fromUser, 1234);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					kkSocket.getInputStream()));
			PrintWriter out = null;
			out = new PrintWriter(kkSocket.getOutputStream(), true);
			
			//Här så kommer meddelandet: TCP Connection succeeded Welcome /192.168.1.130 från servern
			fromServer = in.readLine();
			System.out.println(fromServer);
			//Här så kommer meddelandet som innehåller serverns portnummer från servern
			fromServer = in.readLine();
			DatagramSocket sock = new DatagramSocket();
			out.println(sock.getLocalPort()+"");
			UDP conn_c_UDP = new UDP(Integer.parseInt(fromServer), sock);
		}catch(Exception e){
			
		}finally {
			//System.out.println("UDPClient done.");
			
		}
	}
}

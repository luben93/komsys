import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
	

	public client() {
		try {
			Socket kkSocket = null;
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String fromServer = null;
			String fromUser;
			System.out.println("Enter the machine that you wish to connect to: ");
			fromUser = stdIn.readLine();
			System.out.println(fromUser);
			TCP conn_c = new TCP(fromUser);
			Thread t = new Thread(conn_c);
			t.start();
		}catch(Exception e){
			
		}finally {
	
			System.out.println("UDPClient done.");
		}
	}

}
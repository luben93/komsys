import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class doComms implements Runnable, arrayClients {
	private Socket client;
	
	doComms(Socket client, ArrayList<String> ipAddr) {
		this.client = client;
	}

	public void run() {

		try {
			client.setSoTimeout(50000);
			
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out.println("TCP Connection succeeded Welcome " + client.getInetAddress().toString());
			out.println(4950);
			String fromClient = in.readLine();
			System.out.println(client.getInetAddress().toString());
			array_of_ports.add(Integer.parseInt(fromClient));
		} catch (SocketTimeoutException se) {
			System.out.println("The socket has timed out");
			try {
				for(int i = 0; i < array_of_clients.size();i++){
					if(array_of_clients.get(i).equals(client.getInetAddress().toString())){
						array_of_clients.remove(i);
						array_of_sockets.remove(i);
						array_of_clients_inet.remove(i);
						array_of_ports.remove(i);
						array_of_names.remove(i);
					}
				}
				client.close();
				System.out.println(" Socket to client " + client.getRemoteSocketAddress()+" is now closed ");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(" Could not close socket " + e);
			}
		}catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		}
	}
}

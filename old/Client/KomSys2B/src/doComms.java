import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class doComms implements Runnable, arrayClients {
	private Socket server;
	private String line, input;
	BufferedReader in;
	
	doComms(Socket server, ArrayList<String> ipAddr) {
		this.server = server;
	}

	public void run() {

		try {
			server.setSoTimeout(20000);

			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					server.getInputStream()));
			String inputLine, outputLine;
			// Initiate conversation with client
			GuessNumberProtocol kkp = new GuessNumberProtocol(server.getInetAddress().toString());
			outputLine = kkp.processInput(null);
	
				while ((inputLine = in.readLine()) != null) {
					System.out.println("sss " + server.getSoTimeout());
					outputLine = kkp.processInput(inputLine);
					out.println(outputLine);
					if (inputLine.equals("n")){
						for(int i = 0; i < array_of_clients.size();i++){
							if(array_of_clients.get(i).equals(server.getInetAddress().toString())){
								array_of_clients.remove(i);
							}
						}
						server.close();
						System.out.println(" Socket to client " + server.getRemoteSocketAddress()+" is now closed ");
						break;
					}
				}
		} catch (SocketTimeoutException se) {
			System.out.println("The socket has timed out");
			try {
				for(int i = 0; i < array_of_clients.size();i++){
					if(array_of_clients.get(i).equals(server.getInetAddress().toString())){
						array_of_clients.remove(i);
					}
				}
				server.close();
				System.out.println(" Socket to client " + server.getRemoteSocketAddress()+" is now closed ");
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

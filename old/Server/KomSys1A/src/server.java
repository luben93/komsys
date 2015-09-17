import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server extends Thread implements arrayClients {
	static int maxConnections = 2;
	static int i = 0;
	static ServerSocket serverSocket;
	static Socket clientSocket;


	public static void main(String[] args) {

		try {

			serverSocket = new ServerSocket(1234);

				System.out.println("Waiting for client on port "
						+ serverSocket.getLocalPort() + "...");
				while ((array_of_clients.size() < maxConnections + 1)
						|| (maxConnections == 0)) {

					clientSocket = serverSocket.accept();

					array_of_clients.add(clientSocket.getInetAddress()
							.toString());
					System.out.println(array_of_clients.size());

					doComms conn_c = new doComms(clientSocket, array_of_clients);
					Thread t = new Thread(conn_c);
					t.start();
					System.out.println("Just connected to "
							+ clientSocket.getRemoteSocketAddress());

				}
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ " or listening for a connection");
			System.out.println(e.getMessage());
		} finally {
			try {
				System.out.println("FINALLY TRY"
						+ clientSocket.getRemoteSocketAddress());

				serverSocket.close();
				clientSocket.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

	}

}

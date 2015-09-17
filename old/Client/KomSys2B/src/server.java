import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.Array;
import java.util.ArrayList;

public class server extends Thread implements arrayClients {
	static int maxConnections = 2;
	static int i = 0;
	public static int IpPort = 1;
	static ServerSocket serverSocket;
	static Socket clientSocket;
	private Receiving receiving;

	public server() {

		try {

			serverSocket = new ServerSocket(1234);
			receiving = new Receiving();
			receiving.start();
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			String fromServer = null;
			String fromUser;
			System.out
					.println("Enter the machine that you wish to connect to: ");
			if (IpPort == 1) {
				
				fromUser = stdIn.readLine();
				System.out.println(fromUser);
				TCP conn_c = new TCP(new Socket(), 1, fromUser);
				Thread t = new Thread(conn_c);
				t.start();
			}
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ " or listening for a connection");
			System.out.println(e.getMessage());
		} finally {

		}

	}

	class Receiving implements Runnable {
		private Thread activity = null;
		private DatagramSocket so;

		Receiving() {

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
					System.out.println("Waiting for client on port "
							+ serverSocket.getLocalPort() + "...");

					doComms connection;

					clientSocket = serverSocket.accept();
					IpPort=2;
					array_of_clients.add(clientSocket.getInetAddress()
							.toString());
					System.out.println(array_of_clients.size());

					System.out.println("Just connected to "
							+ clientSocket.getRemoteSocketAddress());

					TCP conn_c = new TCP(clientSocket, 2, "");
					conn_c.run();
					activity = null;

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				so.close();
				System.out.println("Stopped receiving");
			}
		}

	}

}

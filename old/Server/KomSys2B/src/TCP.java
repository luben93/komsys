import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCP implements Runnable {
	Socket fromUser = null;
	int connect = 0;
	String ip = null;
	public TCP(Socket fromUser, int connect, String ip) {
		this.connect = connect;
		this.fromUser = fromUser;
		this.ip = ip;
	}

	public void run() {
		try {
			AudioHandler dh = new AudioHandler();
			for (int i = 1; i < 4; i++) {
				System.out.println("State: " + dh.getState());
				switch (i) {
				case 1:
					dh.invokeReceivedStartSIP(fromUser,connect,ip);
					break;
				case 2:
					dh.invokeReceivedStartStream();
					break;
				case 3:
					dh.invokeReceivedEndCall();
					break;
				}
			}
		} catch (Exception e) {

		} finally {
		}
	}
}

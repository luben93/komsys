import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class StateClose extends State {
	InetAddress ip_to;
	AudioStreamUDP stream;
	Socket ip_from_peer;
	int connect;

	public StateClose(InetAddress ip_to, AudioStreamUDP stream,
			Socket ip_from_peer, int connect) {
		this.ip_to = ip_to;
		this.stream = stream;
		this.ip_from_peer = ip_from_peer;
		this.connect = connect;
	}

	public String getStateName() {
		return "StateClose";
	}

	public State endCall() {
		try {
			PrintWriter out = null;
			System.out.println(" PORT " + ip_from_peer.getLocalPort());
			System.out.println(" PORT1 " + ip_from_peer.getInetAddress());
			out = new PrintWriter(ip_from_peer.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					ip_from_peer.getInputStream()));

			if (connect == 1) {
				out.println("BYE");
				System.out.println(in.readLine());
			} else if (connect == 2) {
				System.out.println(in.readLine());
				out.println("200 OK");
			}

			stream.stopStreaming();
			ip_from_peer.close();

			return new StateStart();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new StateStart();
	}
}

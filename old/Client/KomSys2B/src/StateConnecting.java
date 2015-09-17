import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class StateConnecting extends State {
	InetAddress ip_to;
	int voice_port;
	Socket ip_from_peer;
	int connect;
	public static final int SIP_PORT = 5060;

	public StateConnecting(InetAddress ip_to, int voice_port,
			Socket ip_from_peer, int connect) {
		this.ip_to = ip_to;
		this.connect = connect;
		this.voice_port = voice_port;
		this.ip_from_peer = ip_from_peer;
		System.out.println(" STATE CONN " + ip_from_peer.getInetAddress());
	}

	public String getStateName() {
		return "StateConnecting";
	}

	public State startAudioStreamConnection() {
		/*
		 * This example demonstrates how to connect an AudioStream to a remote
		 * host. Usage: java Caller <callee's ip address> The port corresponding
		 * callee is provided by the user after startup.
		 */

		Scanner scan = new Scanner(System.in);
		AudioStreamUDP stream = null;
		String reply;
		try {
			server.IpPort = 2;
			System.out.println(" STATE CONN 1" + ip_from_peer.getInetAddress());
			// The AudioStream object will create a socket,
			// bound to a random port.
			stream = new AudioStreamUDP();
			int localPort = stream.getLocalPort();
			
			System.out.println("Bound to local port = " + localPort);
			/*System.out.println("What's the remote port number?");
			if (server.IpPort == 2) {
				reply = scan.nextLine().trim();
				int remotePort = Integer.parseInt(reply);
			*/
			InetAddress address = ip_from_peer.getInetAddress();
			System.out.println(address + ", " + voice_port);
			//System.out.println("REMPTE " + remotePort);
			System.out.println("REMPTE " + address.toString());
			stream.connectTo("localhost", localPort);
			//}
			System.out.println("Press ENTER to start streaming");
		stream.startStreaming();

			System.out.println("Press ENTER to stop streaming");
			reply = scan.nextLine();
			stream.stopStreaming();
		} catch (IOException e) {

		} finally {
			if (stream != null)
				stream.close();

		}
		System.out.println(" STATE CONN2 " + ip_from_peer.getInetAddress());
		/*
		 * System.out.println("222222222"); try { AudioStreamUDP stream = new
		 * AudioStreamUDP(); stream.connectTo(ip_to, voice_port);
		 * stream.startStreaming(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		return new StateClose(ip_to, stream, ip_from_peer, connect);
	}
}

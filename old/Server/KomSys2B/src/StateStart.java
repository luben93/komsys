import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class StateStart extends State {
	public String getStateName() {
		return "StateStart";
	}

	public State startSIPConnecting(Socket ip_from_peer, int connect, String ip) {
		String sip_to, sip_from, ip_to, ip_from, fromPeer;
		Socket connectSock = new Socket();
		int voice_port = 0;
		try {
			
			if (connect == 1) {
				PrintWriter out = null;
				Socket tmp = new Socket(ip, 1234);
				out = new PrintWriter(tmp.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						tmp.getInputStream()));
				sip_to = tmp.getInetAddress() + "";
				sip_from = InetAddress.getLocalHost().getHostAddress();
				ip_to = sip_to;
				ip_from = sip_from;
				voice_port = 1235;
				out.println("INVITE " + sip_to + " " + sip_from + " " + ip_to
						+ " " + ip_from + " " + voice_port);
				String readFromPeer;
				readFromPeer = in.readLine();
				if (readFromPeer.startsWith("100 TRYING")) {
					System.out.println(readFromPeer);
				}
				readFromPeer = in.readLine();
				if (readFromPeer.startsWith("180 RINGING")) {
					System.out.println(readFromPeer);
				}
				readFromPeer = in.readLine();
				if (readFromPeer.startsWith("200 OK")) {
					System.out.println(readFromPeer);
				}
				out.println("ACK");
				System.out.println("TJOJOJO");
				System.out.println(" PORT " + ip_from_peer.getLocalPort());
				System.out.println(" PORT1 " + ip_from_peer.getInetAddress());
				System.out.println(" PORT 2 " + tmp.getLocalPort());
				System.out.println(" PORT 3 " + tmp.getInetAddress());
				connectSock = tmp;
			} else if (connect == 2) {
				PrintWriter out = null;
				out = new PrintWriter(ip_from_peer.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						ip_from_peer.getInputStream()));
				fromPeer = in.readLine();
				System.out.println(fromPeer);
				if (fromPeer.startsWith("INVITE")) {
					sip_to = InetAddress.getLocalHost().getHostAddress();
					sip_from = ip_from_peer.getInetAddress() + "";
					ip_to = sip_from;
					ip_from = sip_to;
					voice_port = 1235;
					out.println("100 TRYING");
					out.println("180 RINGING");
					out.println("200 OK");
					
					
				}
				fromPeer = in.readLine();
				System.out.println("MMMMMMMMMMMMMM");
				if (fromPeer.startsWith("ACK")) {
					System.out.println(fromPeer);
				}
				connectSock = ip_from_peer;
			}
			return new StateConnecting(connectSock.getInetAddress(),voice_port, connectSock, connect);
		} catch (IOException e) {
			System.out.println(e);
		}
		return new StateConnecting(connectSock.getInetAddress(),1235,connectSock,connect);
	}
}

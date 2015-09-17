import java.net.Socket;

public abstract class State {
	public abstract String getStateName();

	public State startSIPConnecting(Socket ip_from_peer, int connect, String ip) {
		return this;
	}

	public State startAudioStreamConnection() {
		return this;
	}

	/*public State startMessageSendRec() {
		return this;
	}*/

	public State endCall() {
		return this;
	}
}

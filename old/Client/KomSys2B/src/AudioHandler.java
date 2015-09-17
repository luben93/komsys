import java.net.Socket;


public class AudioHandler {
	private State currentState;
	public AudioHandler() {currentState = new StateStart();}
	public String getState(){return currentState.getStateName();}
	public void invokeReceivedStartSIP(Socket ip_from_peer, int connect, String ip){
	currentState = currentState.startSIPConnecting(ip_from_peer, connect,ip);
	}
	public void invokeReceivedStartStream(){
	currentState = currentState.startAudioStreamConnection();
	}
/*	public void invokeReceivedStartMessage(){
	currentState = currentState.startMessageSendRec();
	}*/
	public void invokeReceivedEndCall(){
	currentState = currentState.endCall();
	}

}

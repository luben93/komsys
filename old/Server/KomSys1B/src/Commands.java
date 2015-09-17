import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class Commands implements arrayClients{

	private String[] returnInfo = new String[2];
	private DatagramPacket client;
	private String name = null;
	
	public Commands(DatagramPacket packet) {
		this.client = packet;
		for(int i = 0; i < array_of_names.size();i++){
			if(array_of_clients_inet.get(i).equals(client.getAddress().toString())){
				name = array_of_names.get(i);
			}
		}
		System.out.println(client.getAddress());
	}

	public String[] processInput(String theInput) throws IOException {
		String theOutput = null;
		char[] theInputCArray = null;
		String sendToWho = "sendToAll";
		
		if(!theInput.equals(null)){
			theInputCArray = theInput.toCharArray();	
		}
		String nickname = null;
		
		theOutput = theInput;
		
		if(theInputCArray[0] == '/'){
			if(theOutput.equalsIgnoreCase("/quit")){
				
				for(int i = 0; i < array_of_names.size();i++){
					if(array_of_clients_inet.get(i).equals(client.getAddress().toString())){
						System.out.println(name);
						array_of_clients.remove(i);
						array_of_sockets.remove(i);
						array_of_clients_inet.remove(i);
						array_of_ports.remove(i);
						array_of_names.remove(i);
					}
				}
				
				//TODO QUIT ??!?!?!??!?
				theOutput = client.getAddress().getHostName() + " disconnected.";
				sendToWho = "sendToAll";
			}
			else if(theInput.equalsIgnoreCase("/who")){
				
				//send a string containing the name of all connected clients to this client
				theOutput = "The clients names are: ";
				for(int i = 0; i < array_of_sockets.size();i++){
					String clientname = array_of_sockets.get(i).getInetAddress().getHostName();
					theOutput += clientname + ", ";
				}
				sendToWho = "sendToAll";
			}
			else if(theInputCArray[1] == 'n' && theInputCArray[2] == 'i' 
					&& theInputCArray[3] == 'c' && theInputCArray[4] == 'k'
					&& theInputCArray[5] == ' ' && theInputCArray[6] == '<'){
				//A nickname is only valid during the session, duplicates are not allowed, old ones are
				//removed.
				int i = 7;
				nickname = "";
				while(theInputCArray[i] != '>'){
					nickname += theInputCArray[i];
					
					theOutput = nickname;
					i++;
					if(i >= theInputCArray.length){
						theOutput = "Unknown command or too long nickname.";
						break;
					}
				}
				sendToWho = "SendToMe";
				if(theOutput.equals(nickname)){
				}
				//TODO add a nick name to this client
			}			
			else if(theInput.equalsIgnoreCase("/help")){
				//listing the available commands
				theOutput = "List of commands: /help, /quit, /who, /nick <nickname>";
				sendToWho = "sendToMe";
			}
			else{
				theOutput = "Unknown command";
			}
		}
		returnInfo[0] = client.getAddress().getHostName() + ": " + theOutput;
		//returnInfo[0] = name + ": " + theOutput;
		returnInfo[1] = sendToWho;
		return returnInfo;
	}
}
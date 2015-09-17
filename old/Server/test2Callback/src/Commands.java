import java.rmi.RemoteException;
import java.util.ArrayList;

public class Commands{

	ArrayList<Notifiable> clientList;
	String clientName;
	String[] returnInfo = new String[2];

	public Commands(ArrayList<Notifiable> clientList, String clientName) {
		this.clientList = clientList;
		this.clientName = clientName;
	}

	public String[] processInput(String theInput) {
		String theOutput = null;
		char[] theInputCArray = null;
		String sendToWho = "sendToAll";	
		
		if(!theInput.equals(null)){
			theInputCArray = theInput.toCharArray();	
		}
		else{
		}
		String newNickname = null;
		
		theOutput = theInput;
		
		if(theInputCArray[0] == '/'){
			if(theInput.equalsIgnoreCase("/quit")){
				theOutput = clientName + " disconnected.";
				sendToWho = "sendToAll";
			}
			else if(theInput.equalsIgnoreCase("/who")){
				//send a string containing the name of all connected clients to this client
				theOutput = "The clients names are: ";
		        for(Notifiable n : clientList) {
					try {
						theOutput +=  n.getName() + ", ";
					} catch (RemoteException e) {
						System.out.println("Name could not be found.");
						e.printStackTrace();
					}
		        }
				sendToWho = "sendToAll";
			}
			else if(theInputCArray[1] == 'n' && theInputCArray[2] == 'i' 
					&& theInputCArray[3] == 'c' && theInputCArray[4] == 'k'
					&& theInputCArray[5] == ' ' && theInputCArray[6] == '<') {
				int i = 7;
				newNickname = "";
				while(theInputCArray[i] != '>'){
					newNickname += theInputCArray[i];
					
					theOutput = newNickname;
					i++;
					if(i >= theInputCArray.length){
						theOutput = "Unknown command or too long nickname.";
						break;
					}
				}
				sendToWho = "SendToAll";
				boolean success = true;
		        for(Notifiable n : clientList) {
					try {
						if(newNickname.equals(n.getName())){
							success = false;
						}					
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		        }
		        if(success){
		        	theOutput = "changed name to: " + newNickname;
			        for(Notifiable n : clientList) {
						try {
							if(clientName.equals(n.getName())){
								n.setName(newNickname);
							}					
						} catch (RemoteException e) {
							e.printStackTrace();
						}
			        }
		        }
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
		returnInfo[0] = clientName + ": " + theOutput;
		returnInfo[1] = sendToWho;
		return returnInfo;
	}
}
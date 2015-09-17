/**
 * AuctionClient.java
 *
 * A very simple chat client. Works with RMI server Server.java
 * Responds to callbacks from the server via the Notifiable interface
 * and the method notifyMessage.
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements Notifiable
{
	private Chat chat; // Reference to remote server object
	private String name;
	
	public Client(Chat chat) throws RemoteException {
		super();
		this.chat = chat;
		try {
			this.name = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/* This method is used by the server to "call back" 
	 * to update clients. */
	synchronized public void notifyMessage(String message) throws RemoteException {
		System.out.println(message);
	}
	
	synchronized public String getName() throws RemoteException {
		return name;
	}
	
	synchronized public void setName(String name) throws RemoteException {
		this.name = name;
	}
	
	public static void main(String [] args) {
	   
	   try {
		   
	   	   String url = "//localhost:2020/ChatTest";
	       Chat chat = (Chat) Naming.lookup(url);
	       Client client = new Client(chat);
	       
	       /* Register for callbacks at the chat server. */
	       chat.registerForNotification(client);
	       
	       client.runClient();
	    }
	    catch (NotBoundException nbe) {
	    	System.out.println("Server is not running.");
	    }
	    catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void runClient() throws RemoteException {
		
		System.out.println("Welcome to the chatroom.");
		System.out.println("Write '/quit' to exit.");
		
		Scanner scan = new Scanner(System.in);
		String fromUser;
		

		while (true) {
			
			fromUser = scan.nextLine();
			if(fromUser != null){
				chat.leaveMessage(fromUser, getName());	
			}
			
			if (fromUser.equalsIgnoreCase("/quit")){
				scan.close();
				break;
			}
		}

		System.out.println("Exiting...");
		// Important: Deregister for notifiation from server!
		chat.deRegisterForNotification(this);
		// Terminate the thread associated with rmi calls
		System.exit(0);
	}
}
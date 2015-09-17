/**
 * Server.java
 *
 * A simple RMI chat server. When a message is put, the server calls back 
 * to clients to update them.
 * To do this, the server maintains a list of Notifiable objects to call back,
 * and provides a remote method, registerForNotification, the clients can call
 * to register on the server.
 *
 * This class also contains the servers main method.
 */

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.util.*;

public class Server extends UnicastRemoteObject implements Chat
{
	/* The list of regisered clients */
    private ArrayList<Notifiable> clientList = null;

    public Server() throws RemoteException {
    	super();
		clientList = new ArrayList<Notifiable>();
    }

	synchronized public void leaveMessage(String message, String name) throws RemoteException {
		
		Commands com = new Commands(clientList, name);
		String[] output = com.processInput(message);
		
		if(output[1] == "sendToMe"){
	    	// Notify one client
	        for(Notifiable n : clientList) {
	        	if(name.equals(n.getName())){
	                n.notifyMessage(output[0]);
	        	}
	        }
		}
		else{
	    	// Notify all clients
	        for(Notifiable n : clientList) {
	                n.notifyMessage(output[0]);
	        }	
		}
	}

	/* Called by clients to register for server callbacks
     */
	private int counter = 1;
    synchronized public void registerForNotification(Notifiable n) throws RemoteException {
		clientList.add(n);
		n.setName("Host " + counter++);
    }
    
    synchronized public void deRegisterForNotification(Notifiable n) throws RemoteException {
		clientList.remove(n);     	
    }

    public static void main(String [] args) {
    	
        try {
			LocateRegistry.createRegistry(2020);
        	
            Server server = new Server();
            Naming.rebind("//localhost:2020/ChatTest", server);
            System.out.println("Chat server running...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

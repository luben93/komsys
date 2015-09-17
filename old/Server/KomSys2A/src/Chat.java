/**
 * Chat.java
 *
 * This interface defines chat services, for clients to call 
 * through remote references.
 *
 * NB: Clients should call registerForNotification
 * to register on the server.
 */

import java.rmi.*;

interface Chat extends Remote {
	
    public void leaveMessage(String message, String name) throws RemoteException;

    /* Called by clients to register for server callbacks
     */
    public void registerForNotification(Notifiable n) throws RemoteException;
    public void deRegisterForNotification(Notifiable n) throws RemoteException;
}
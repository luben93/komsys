/**
 * Notifiable.java
 *
 * The method notifyMessage() is implemented by the client, Client.java.
 * The method is used by the server to "call back" to update clients. 
 */

import java.rmi.*;

public interface Notifiable extends Remote {
	
    public void notifyMessage(String message) throws RemoteException;
    
    public String getName() throws RemoteException;
    
    public void setName(String name) throws RemoteException;
}
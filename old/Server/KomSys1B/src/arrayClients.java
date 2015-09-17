import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


public interface arrayClients {
	static ArrayList<String> array_of_clients = arraylistClient.getInstance().ipAddr; 
	static ArrayList<InetAddress> array_of_clients_inet = arraylistClient.getInstance().ipAddrInet; 
	static ArrayList<Integer> array_of_ports = arraylistClient.getInstance().ports; 
	static ArrayList<Socket> array_of_sockets = arraylistClient.getInstance().sockets;
	static ArrayList<String> array_of_names = arraylistClient.getInstance().name;
}

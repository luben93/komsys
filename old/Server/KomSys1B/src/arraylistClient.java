import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


class arraylistClient{

	  //eventually provides setters and getters
	  public float x;
	  public float y;
	  static ArrayList<String> ipAddr = new ArrayList<String>();
	  static ArrayList<Socket> sockets = new ArrayList<Socket>();
	  static ArrayList<Integer> ports = new ArrayList<Integer>();
	  static ArrayList<InetAddress> ipAddrInet = new ArrayList<InetAddress>();
	  static ArrayList<String> name = new ArrayList<String>();
	  //------------

	  private static arraylistClient instance = null;

	  public static arraylistClient getInstance(){
	    if(instance==null){
	       instance = new arraylistClient();
	      }
	      return instance;
	  }
	}

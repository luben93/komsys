import java.util.ArrayList;


class arraylistClient{

	  //eventually provides setters and getters
	  public float x;
	  public float y;
	  static ArrayList<String> ipAddr = new ArrayList<String>();
	  //------------

	  private static arraylistClient instance = null;
	  
	  public static arraylistClient getInstance(){
	    if(instance==null){
	       instance = new arraylistClient();
	      }
	      return instance;
	  }
	}

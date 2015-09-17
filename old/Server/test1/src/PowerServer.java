import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class PowerServer {
	
	public static void main(String[] args) {
        
        try {
        	LocateRegistry.createRegistry(2020);
        	// Create the distributed object
            PowerImpl pow = new PowerImpl(); 
            // Register the object in the RMI registry
            
            //Naming.bind("power", pow);
        	Naming.rebind("//localhost:2020/power", pow);
        	
            System.out.println("Power initialized");
        } catch (Exception e) {
            System.err.println("Error initializing power: " + e.toString());
        }
	}
}
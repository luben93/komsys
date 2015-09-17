import java.rmi.*;

public class PowerClient {
	
	public static void main(String[] args) {
		
		try {
			// Get a remote reference to the distributed object
			// from the rmi registry
			Power pow = 
				(Power) Naming.lookup("//localhost:2020/power");
				
			double res, x =3.14; 
			int n = 200;
			res = pow.calcPower(x, n);
			System.out.println("" + x + "^" + n + " = " + res);			
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	}
}
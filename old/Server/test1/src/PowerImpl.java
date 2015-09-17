import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PowerImpl extends UnicastRemoteObject implements Power {
	
	public PowerImpl() throws RemoteException {
		super();
	}
	
	public double calcPower(double x, int n) throws RemoteException {
		int absn = Math.abs(n);
		double res = 1;
		for(int i = 0; i < absn; i++) {
			res = res*x;
		}
		return (n >= 0 ? res : 1/res);
	}
}
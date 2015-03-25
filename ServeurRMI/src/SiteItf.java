import java.rmi.Remote;
import java.rmi.RemoteException;


public interface SiteItf extends Remote {
	
	public void addNode(SiteItf node)  throws RemoteException;
	public void spread(byte [] data) throws RemoteException;

}

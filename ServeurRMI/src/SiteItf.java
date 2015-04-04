
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface SiteItf extends Remote {
	
	public void addFils(SiteItf fils)  throws RemoteException;
	public void spread(byte [] data) throws RemoteException;
	public String getNum() throws RemoteException;
	public void addFather(String father) throws RemoteException, MalformedURLException, NotBoundException;
	public void addVoisin(String name) throws MalformedURLException, RemoteException, NotBoundException;
}

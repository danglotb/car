
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class SiteImplTree extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String father;
	private List<String> sons;
	private String name;
	
	public SiteImplTree(String name, String father) throws RemoteException {
		this.name = name;
		this.father = father;
		this.sons = new ArrayList<String>();
	}
	
	public void spread(final byte [] data) throws RemoteException {
		// Propage les donnees a tous ses fils
		System.out.println(" Noeud n° " +  this.name + " : données reçues, je propage à mes fils");
		for (final String son : this.sons) {
			new Thread () {
					public void run() {
						try {
							((SiteItf)(Naming.lookup(son))).spread(data);
						} catch (RemoteException e) {
							e.printStackTrace();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (NotBoundException e) {
							e.printStackTrace();
						}
					}
				}.start();
			
		}
		System.out.println(this.name + "données propagées");
	}

	/**
	 * 
	 */
	public void addConnection(String son) throws MalformedURLException,
			RemoteException, NotBoundException {
		if (!this.sons.contains(son)) 
			this.sons.add(son);
	}
}

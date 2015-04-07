
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * RMI object wich is implements SiteItf
 * This is a node of Graph
 */
public class SiteImplGraph extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 23L;
	
	/**
	 * List of successor
	 */
	private List<String> successor;
	
	/**
	 * Name of the Node
	 */
	private String name;
	
	private boolean spread;
	
	/**
	 * Constructor
	 * @param name : name of the node
	 * @throws RemoteException
	 */
	public SiteImplGraph(String name) throws RemoteException {
		this.name = name;
		this.successor = new ArrayList<String>();
		this.spread = false;
	}

	/**
	 * Method wich spread an array of byte
	 * @param data : array of byte
	 */
	public void spread(final byte [] data) throws RemoteException {
		if (spread) {
			spread = false;
			return;
		}
		// Propage les donnees a tous ses fils
		System.out.println(" Noeud n° " +  this.name + " : données reçues, je propage à mes fils");
		for (final String voisin : this.successor) {
			//Concurrence
			new Thread () {
					public void run() {
						try {
							((SiteItf)(Naming.lookup(voisin))).spread(data);
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
		this.spread = true;
	}
	
	/**
	 * Add a successor
	 * @param name : name of the successor as a String (Logic addresse)
	 */
	public void addConnection(String name) throws MalformedURLException, RemoteException, NotBoundException {
		if (!this.successor.contains(name)) 
			this.successor.add(name);
	}
	
}

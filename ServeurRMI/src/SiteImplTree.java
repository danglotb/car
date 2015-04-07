
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Objet RMI qui implement l'interface SiteItf
 * Cette class represente un noeud d'arbre
 */
public class SiteImplTree extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Père du noeud
	 */
	private String father;
	
	/**
	 * List de fils
	 */
	private List<String> sons;
	
	/**
	 * Nom du noeud
	 */
	private String name;
	
	/**
	 * Constructor
	 * @param name : nom du noeud
	 * @param father : nom du pere
	 * @throws RemoteException
	 */
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
	 * ajoute un fils a l'arbre
	 */
	public void addConnection(String son) throws MalformedURLException,
			RemoteException, NotBoundException {
		if (!this.sons.contains(son)) 
			this.sons.add(son);
	}
}

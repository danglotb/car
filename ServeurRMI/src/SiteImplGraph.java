
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Objet RMI qui implement l'interface SiteItf
 * Cette class represente un noeud de Graphe.
 */
public class SiteImplGraph extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 23L;
	
	/**
	 * List des noeuds voisins
	 */
	private List<String> voisins;
	
	/**
	 * Nom du nom
	 */
	private String name;
	
	/**
	 * Constructor
	 * @param name : le nom du noeud sous forme de String
	 * @throws RemoteException
	 */
	public SiteImplGraph(String name) throws RemoteException {
		this.name = name;
		this.voisins = new ArrayList<String>();
	}

	/**
	 * Methode principale qui propage un tableau d'octet a tous les voisins
	 * @param data : tableau d'octet propagés
	 */
	public void spread(final byte [] data) throws RemoteException {
		// Propage les donnees a tous ses fils
		System.out.println(" Noeud n° " +  this.name + " : données reçues, je propage à mes fils");
		for (final String voisin : this.voisins) {
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
	}
	
	/**
	 * Accesseur sur la chaine de caractères du nom du noeud
	 * @return le nom du nom sous forme de String
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException {
		return this.name;
	}
	
	/**
	 * Ajout un voisin à la liste des voisins
	 * @param name : nom du nom voisin a ajouté sous forme de String
	 * la methode addVoisin du voisin ajouté (le noeud s'ajoute lui-même a la liste)
	 */
	public void addVoisin(String name) throws MalformedURLException, RemoteException, NotBoundException {
		if (!this.voisins.contains(name)) {
			this.voisins.add(name);
			((SiteItf)(Naming.lookup(name))).addVoisin(this.name);
		}
	}
	
	//TODO rm
	
	/**
	 * @Unused
	 */
	public String getNum() throws RemoteException{
		throw new UnsupportedOperationException();
	}

	/**
	 * @Unused
	 */
	@Override
	public void addFils(SiteItf fils) throws RemoteException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * @throw new UnsupportedOperationException 
	 */
	public void addFather(String father) throws RemoteException {
		throw new UnsupportedOperationException();
	}
}

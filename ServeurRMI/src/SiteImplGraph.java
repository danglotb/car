
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class SiteImplGraph extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> voisins;
	private static int num = 1;
	private int monNum;
	private String name;
	
	public SiteImplGraph(String name) throws RemoteException {
		this.monNum = SiteImplGraph.num++;
		this.name = name;
		this.voisins = new ArrayList<String>();
	}

	public void spread(final byte [] data) throws RemoteException {
		// Propage les donnees a tous ses fils
		System.out.println(" Noeud n° " +  this.monNum + " : données reçues, je propage à mes fils");
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
		System.out.println(this.monNum + "données propagées");
	}
	
	public void addVoisin(String name) throws MalformedURLException, RemoteException, NotBoundException {
		if (!this.voisins.contains(name)) {
			this.voisins.add(name);
			((SiteItf)(Naming.lookup(name))).addVoisin(this.name);
		}
	}
	
	public String getNum() throws RemoteException{
		return this.monNum +"";
	}

	/**
	 * @Unused
	 */
	@Override
	public void addFils(SiteItf fils) throws RemoteException {
		
	}
	
	public String getName() throws RemoteException {
		return this.name;
	}

	/**
	 * 
	 * @throw new UnsupportedOperationException 
	 */
	public void addFather(String father) throws RemoteException {
		throw new UnsupportedOperationException();
	}
}

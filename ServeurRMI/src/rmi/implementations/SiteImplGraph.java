package rmi.implementations;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rmi.interfaces.SiteItf;

/**
 * RMI object which is implements SiteItf
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
	 * Name of the Node, need to be unique, in order to be bind into rmiregistry.
	 */
	private String name;
	
	
	/**
	 * Map of the id to know if we spread or not.
	 * the Key is the Idea, and the value is the timeStamp
	 */
	private Map<Integer, Long> idToTimeStamp;
	
	
	/**
	 * Constructor
	 * @param name : Name of Node
	 * @throws RemoteException
	 */
	public SiteImplGraph(String name) throws RemoteException {
		this.name = name;
		this.successor = new ArrayList<String>();
		this.idToTimeStamp = new HashMap<Integer, Long>();
	}

	/**
	 * this method is used to spread the data.
	 * @args : id is used to know if the node already spread it or not
	 */
	public void spread(final byte [] data,final int id) throws RemoteException {
		checkTime();
		if (this.idToTimeStamp.containsKey(id))
			return;
		this.idToTimeStamp.put(id, System.currentTimeMillis());
		// spread to all sons
		System.out.println(" Noeud n° " +  this.name + " : données reçues...");
		System.out.println(new String(data) + "... je propage à mes fils");
		for (final String voisin : this.successor) {
			//Concurrency
			new Thread () {
					public void run() {
						try {
							((SiteItf)(Naming.lookup(voisin))).spread(data,id);
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
		System.out.println(this.name + " données propagées");
	}
	
	/**
	 * Add a successor at the list if it doesn't contain it
	 */
	public void addConnection(String name) throws MalformedURLException, RemoteException, NotBoundException {
		if (!this.successor.contains(name)) 
			this.successor.add(name);
	}
	
	private void checkTime() {
		for (Integer id : this.idToTimeStamp.keySet()) {
			if (System.currentTimeMillis() - this.idToTimeStamp.get(id) > 10000) {
				this.idToTimeStamp.remove(id);
			}
		}
	}
	
}

package rmi.implementations;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import rmi.interfaces.SiteItf;


/**
 * RMI Object which implements SiteItf
 * TreeNode
 */
public class SiteImplTree extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Name of father 
	 */
	private String father;
	
	/**
	 * List of sons
	 */
	private List<String> sons;
	
	/**
	 * Name of the Node, need to be unique, in order to be bind into rmiregistry
	 */
	private String name;
	
	/**
	 * Constructor
	 * @param name : Name of Node
	 * @param father : Name of father
	 * @throws RemoteException
	 */
	public SiteImplTree(String name, String father) throws RemoteException {
		this.name = name;
		this.father = father;
		this.sons = new ArrayList<String>();
	}
	
	/**
	 * this method is used to spread the data.
	 * @args : id is not used in the case of a Tree
	 */
	public void spread(final byte [] data, int id) throws RemoteException {
		// Propage les donnees a tous ses fils
		System.out.println(" Noeud n° " +  this.name + " : données reçues, je propage à mes fils");
		for (final String son : this.sons) {
			new Thread () {
					public void run() {
						try {
							((SiteItf)(Naming.lookup(son))).spread(data, 0);
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
	 * Add a son at the list if it doesn't contain it
	 */
	public void addConnection(String son) throws MalformedURLException,
			RemoteException, NotBoundException {
		if (!this.sons.contains(son)) 
			this.sons.add(son);
	}
}

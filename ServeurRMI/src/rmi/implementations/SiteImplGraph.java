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
	 * Nom du noeud
	 */
	private String name;
	
	private boolean spread;
	
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
	}
	
	/**
	 * Ajout un voisin à la liste des voisins
	 * @param name : nom du nom voisin a ajouté sous forme de String
	 */
	public void addConnection(String name) throws MalformedURLException, RemoteException, NotBoundException {
		if (!this.voisins.contains(name)) 
			this.voisins.add(name);
	}
	
}

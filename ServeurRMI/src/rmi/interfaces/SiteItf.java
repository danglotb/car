package rmi.interfaces;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface de noeud RMI
 */
public interface SiteItf extends Remote {
	
	/**
	 * Methode qui ajoute un lien entre le noeud courant et le nom dont le nom est passé en parametre
	 * @param name nom du voisin a ajouté
	 * TODO : renommer pour que la sémantique fonctionne sur les deux implementation (tree & graph)
	 */
	public void addConnection(String name) throws MalformedURLException, RemoteException, NotBoundException;
	
	/**
	 * Methode de propagation de données
	 * @param data : Tableau d'octet a propagé
	 * @throws RemoteException
	 */
	public void spread(byte [] data) throws RemoteException;
}

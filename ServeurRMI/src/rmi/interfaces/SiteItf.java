package rmi.interfaces;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI Interface
 */
public interface SiteItf extends Remote {
	public void addConnection(String name) throws MalformedURLException, RemoteException, NotBoundException;
	public void spread(byte [] data, int id) throws RemoteException;
}

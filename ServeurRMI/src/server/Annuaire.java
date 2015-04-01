package server;

import java.rmi.RemoteException;

import site.SiteImpl;
import site.SiteItf;

public class Annuaire {

	private SiteItf[] noeuds;
	
	
	
	public Annuaire() {
		noeuds 	= new SiteImpl[6];
	}
	
	public void addSite(SiteItf site, int node){
		if(node < 0 && node > this.noeuds.length)
			throw new ArrayIndexOutOfBoundsException();
		this.noeuds[node] = site;
	}
	
	public void addNode(SiteItf pere, SiteItf fils){
		try {
			pere.addNode(fils);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void spread(int node, byte[] buffer){
		//TODO : todo
	}
	
}

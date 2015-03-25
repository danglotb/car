import java.rmi.RemoteException;


public class Main {

	public static void main(String[] args) throws RemoteException {
		
		SiteItf[] noeuds = new SiteImpl[6];
		noeuds[0] = new SiteImpl(null);
		noeuds[1] = new SiteImpl(noeuds[0]);
		noeuds[2] = new SiteImpl(noeuds[1]);
		noeuds[3] = new SiteImpl(noeuds[1]);
		noeuds[4] = new SiteImpl(noeuds[0]);
		noeuds[5] = new SiteImpl(noeuds[4]);
		noeuds[0].addFils(noeuds[1]);
		noeuds[1].addFils(noeuds[2]);
		noeuds[1].addFils(noeuds[3]);
		noeuds[0].addFils(noeuds[4]);
		noeuds[4].addFils(noeuds[5]);
		
		noeuds[0].spread("toto".getBytes());

	}
	
}
import java.rmi.RemoteException;


public class Main {

	public static void main(String[] args) throws RemoteException {
		
		SiteItf[] noeuds = new SiteImpl[6];
		noeuds[0] = new SiteImpl();
		noeuds[1] = new SiteImpl();
		noeuds[2] = new SiteImpl();
		noeuds[3] = new SiteImpl();
		noeuds[4] = new SiteImpl();
		noeuds[5] = new SiteImpl();
		noeuds[0].addNode(noeuds[1]);
		noeuds[1].addNode(noeuds[2]);
		noeuds[1].addNode(noeuds[3]);
		noeuds[0].addNode(noeuds[4]);
		noeuds[4].addNode(noeuds[5]);
		noeuds[4].addNode(noeuds[5]);
		
		noeuds[0].spread("toto".getBytes());
	}
	
}

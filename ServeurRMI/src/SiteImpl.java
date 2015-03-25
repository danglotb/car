import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class SiteImpl extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SiteItf pere;
	private List<SiteItf> fils;
	private static int num = 1;
	private int monNum;
	
	protected SiteImpl(SiteItf pere) throws RemoteException {
		this.pere = pere;
		this.monNum = SiteImpl.num++;
		this.fils = new ArrayList<SiteItf>();
	}

	public void spread(byte [] data) {
		// Propage les donnees a tous ses fils
		System.out.println(" Noeud n° " +  this.monNum + " : données reçues, je propage à mes fils");
		for (SiteItf fils : this.fils)
			fils.spread(data);
		System.out.println("données propagées");
	}

	public SiteItf getPere() {
		return pere;
	}

	public void setPere(SiteItf pere) {
		this.pere = pere;
	}

	public List<SiteItf> getFils() {
		return fils;
	}

	public void setFils(List<SiteItf> fils) {
		this.fils = fils;
	}
	
	public void addFils(SiteItf fils){
		this.fils.add(fils);
	}
}

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class SiteImpl extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SiteItf pere;
	private SiteItf [] fils;
	
	
	protected SiteImpl() throws RemoteException {
		super();
	}

	public void spread(byte [] data) {
		// Propage les donnees a tous ses fils
		System.out.println("données reçues, je propage à mes fils");
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

	public SiteItf [] getFils() {
		return fils;
	}

	public void setFils(SiteItf [] fils) {
		this.fils = fils;
	}

}

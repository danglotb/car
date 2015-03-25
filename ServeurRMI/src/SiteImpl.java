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

	public void spread() {
		
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

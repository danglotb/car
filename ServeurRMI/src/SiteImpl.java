import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class SiteImpl extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SiteItf> nodes;
	private static int num = 1;
	private int monNum;
	public boolean propage;
	
	protected SiteImpl() throws RemoteException {
		this.monNum = SiteImpl.num++;
		this.nodes = new ArrayList<SiteItf>();
		this.propage = false;
	}

	public void spread(final byte [] data) throws RemoteException {
		// Propage les donnees a tous ses fils
		if (propage) return;
		System.out.println(" Noeud n° " +  this.monNum + " : données reçues, je propage à mes fils");
		for (final SiteItf node : this.nodes) {
				new Thread () {
					public void run() {
						try {
							node.spread(data);
						} catch (RemoteException e) {e.printStackTrace();}
					}
				}.start();
		}
		System.out.println(this.monNum + "données propagées");
		propage = true;
	}

	public List<SiteItf> getFils() throws RemoteException {
		return nodes;
	}

	public void setNodes(List<SiteItf> nodes) throws RemoteException{
		this.nodes = nodes;
	}
	
	public void addNode(SiteItf node) throws RemoteException{
		this.nodes.add(node);
	}
}

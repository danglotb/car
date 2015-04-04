
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
	private String name;
	
	public SiteImpl(String name) throws RemoteException {
		this.name = name;
		this.fils = new ArrayList<SiteItf>();
	}
	
	public SiteImpl(SiteItf pere) throws RemoteException {
		this.pere = pere;
		this.monNum = SiteImpl.num++;
		this.fils = new ArrayList<SiteItf>();
	}

	public void spread(final byte [] data) throws RemoteException {
		// Propage les donnees a tous ses fils
		System.out.println(" Noeud n° " +  this.monNum + " : données reçues, je propage à mes fils");
		for (final SiteItf fils : this.fils) {
			new Thread () {
					public void run() {
						try {
							((SiteItf)(Naming.lookup(fils.getNum()))).spread(data);
						} catch (RemoteException e) {
							e.printStackTrace();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (NotBoundException e) {
							e.printStackTrace();
						}
					}
				}.start();
			
				/*try {
					((SiteItf)(Naming.lookup(fils.getNum()))).spread(data);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}*/
		}
		System.out.println(this.monNum + "données propagées");
	}

	public SiteItf getPere() throws RemoteException {
		return pere;
	}

	public void setPere(SiteItf pere) throws RemoteException {
		this.pere = pere;
	}

	public List<SiteItf> getFils() throws RemoteException {
		return fils;
	}

	public void setFils(List<SiteItf> fils) throws RemoteException{
		this.fils = fils;
	}
	
	public void addFils(SiteItf fils) throws RemoteException{
		this.fils.add(fils);
	}
		
	public String getNum() throws RemoteException{
		return this.monNum +"";
	}
	
	public void addFather(String father) throws RemoteException, MalformedURLException, NotBoundException {
		this.pere = ((SiteItf)(Naming.lookup(father)));
	}
	
	public void addSon(String son) throws MalformedURLException, RemoteException, NotBoundException {
		this.fils.add(((SiteItf)(Naming.lookup(son))));
		((SiteItf)(Naming.lookup(son))).addFather(this.name);
	}

	/**
	 * @unused
	 */
	public void addVoisin(String name) throws MalformedURLException,
			RemoteException, NotBoundException {
		throw new UnsupportedOperationException();
	}
}

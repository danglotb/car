import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class LaunchNodeTree {
	
	/**
	 * Launch a node tree
	 * @param args [0] name of the node
	 *		args[1+] name of his sons 	
	 *
	 * @throws RemoteException
	 * @throws AlreadyBoundException 
	 * @throws MalformedURLException 
	 * @throws NotBoundException 
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {
		
		SiteImplTree node = new SiteImplTree(args[0]);
		System.out.println("arg : "+ args[0] + " " + " node : " + node.getNum());
		Naming.rebind(args[0], node);
		for (int i = 1 ; i < args.length ; i++) {
			node.addSon(args[i]);
		}
		
	}

}

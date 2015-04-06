import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Contient un main pour créer, ajouter des fils et inscrire un noeud de type Tree
 */
public class LaunchNodeTree {
	
	/**
	 * Launch a node tree
	 * @param args [0] name of the node
	 *		args[1+] name of his sons 	
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {
		
		SiteImplTree node = new SiteImplTree(args[0]);
		System.out.println("arg : "+ args[0] + " " + " node : " + node.getNum());
		
		//Incription du node aupres de rmiRegistry 
		Naming.rebind(args[0], node);
		for (int i = 1 ; i < args.length ; i++) {
			// on verifie qu'il s'agit bien d'un fils, et non pas du -1
			if (!args[i].equals("-1"))
				node.addSon(args[i]);
		}
		
	}

}

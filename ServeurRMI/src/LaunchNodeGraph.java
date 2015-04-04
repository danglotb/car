import java.rmi.Naming;


public class LaunchNodeGraph {
	
	
	/**
	 * Lance un noeud Type Graphe
	 * @param args [0] : Name of the node
	 * 	args[1+] : Name of the connected nodes
	 * @throws Exception
	 */
	public static void main(String[] args)  throws Exception {
		
		SiteImplGraph node = new SiteImplGraph(args[0]);
		
		//Incrition du node aupres de rmiRegistry 
		
		Naming.bind(args[0], node);
		
		for (int i = 1 ; i < args.length ; i++) {
			node.addVoisin(args[i]);
		}
		
	}

}

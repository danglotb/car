import java.rmi.Naming;

/**
 * Contient un main pour créer, ajouter des voisins et inscrire un noeud de type Graph
 */
public class LaunchNodeGraph {
	
	/**
	 * Lance un noeud Type Graphe
	 * @param args [0] : Name of the node
	 * 	args[1+] : Name of the connected nodes
	 */
	public static void main(String[] args)  throws Exception {
		
		SiteImplGraph node = new SiteImplGraph(args[0]);
		
		//Incription du node aupres de rmiRegistry 
		Naming.bind(args[0], node);
		
		for (int i = 1 ; i < args.length ; i++) {
			// on verifie qu'il s'agit bien d'un voisin, et non pas du -1
			if (!args[i].equals("-1"))
				node.addConnection(args[i]);
		}
		
	}

}

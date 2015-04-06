
import java.rmi.Naming;


//TODO rm this main


/**
 * Launch Server
 */
public class Main {

	public static void main(String[] args) throws Exception {
		
		SiteItf[] noeuds = new SiteImplTree[6];
		noeuds[0] = new SiteImplTree("");
		noeuds[1] = new SiteImplTree(noeuds[0]);
		noeuds[2] = new SiteImplTree(noeuds[1]);
		noeuds[3] = new SiteImplTree(noeuds[1]);
		noeuds[4] = new SiteImplTree(noeuds[0]);
		noeuds[5] = new SiteImplTree(noeuds[4]);
		noeuds[0].addFils(noeuds[1]);
		noeuds[1].addFils(noeuds[2]);
		noeuds[1].addFils(noeuds[3]);
		noeuds[0].addFils(noeuds[4]);
		noeuds[4].addFils(noeuds[5]);
		
		for (int i = 0 ; i < noeuds.length ; i++)
				Naming.bind(i+1+"", noeuds[i]);
			
	}
	
}

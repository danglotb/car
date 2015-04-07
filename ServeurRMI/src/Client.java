

import java.rmi.Naming;
import java.util.Random;


/**
 * Client
 */
public class Client {
	
	/**
	 * 
	 * @param args [0] : name of the node which will start the spread
	 * @param args [1] : String to Spread : will be transform into a array of bytes
	 * @throws Exception
	 */
	public static void main(String[] args)  throws Exception {
		SiteItf s = (SiteItf) Naming.lookup(args[0]);
		s.spread(args[1].getBytes(), new Random().nextInt());
	}

}

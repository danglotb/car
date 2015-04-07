package rmi.executables;


import java.rmi.Naming;
import java.util.Random;

import rmi.interfaces.SiteItf;


/**
 * Client
 */
public class Launch {
	
	/**
	 * 
	 * @param args [0] : name of the node which will start the spread
	 * @param args [1] : String to Spread : will be transform into a array of bytes
	 * it generates an idea with the hash of the string to spread, and a random from the current time
	 * @throws Exception
	 */
	public static void main(String[] args)  throws Exception {
		SiteItf s = (SiteItf) Naming.lookup(args[0]);
		s.spread(args[1].getBytes(), (args[1].hashCode()+new Random().nextInt()));
	}

}

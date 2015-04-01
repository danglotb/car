

import java.rmi.Naming;


/**
 * Client
 *
 */
public class Client {
	
	public static void main(String[] args)  throws Exception {

		SiteItf s = (SiteItf) Naming.lookup(1+"");
		s.spread("toto".getBytes());
		
	}

}

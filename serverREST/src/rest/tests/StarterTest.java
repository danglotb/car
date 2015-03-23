package rest.tests;

import rest.Starter;

/**
 * Class de Test pour Starter
 * Herite de Thread pour lancer le Starter
 */
public class StarterTest extends Thread {
	
	/**
	 * methode run() de Thread, appel la methode main() the Starter.java
	 */
	public void run() {
		try {
			Starter.main(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

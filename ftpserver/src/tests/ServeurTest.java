package tests;

import java.io.IOException;

import serverftp.Serveur;

public class ServeurTest extends Thread {
	
	public ServeurTest() {
		
	}
	
	public void run() {
		try {
			Serveur.main(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopServ() { 
		Serveur.endService();
	}

}

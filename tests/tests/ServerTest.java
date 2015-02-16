package tests;

import java.io.IOException;

import serverftp.Server;

public class ServerTest extends Thread {
	
	public ServerTest() {
		
	}
	
	public void run() {
		String args [] = {"filesys"};
		try {
			Server.main(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopServ() { 
		Server.endService();
	}

}

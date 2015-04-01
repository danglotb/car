package client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Stub {	
	
	private Socket socket;

	public Stub(String adr, int port) {
		try {
			this.socket = new Socket(adr, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public String spread(byte [] data) {
		try {
			PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return "";
	}
	
	

}

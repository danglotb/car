package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Skel {
	
	private ServerSocket servSock;
	private Socket sock;
	private InputStream in;
	private Annuaire a;
	
	public Skel(int port) {
		try {
			this.servSock = new ServerSocket(port);
			this.sock = servSock.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void recieve(){
		try {
			byte[] buff = null;
			in = sock.getInputStream();
			in.read(buff);
			a.spread(1, buff);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

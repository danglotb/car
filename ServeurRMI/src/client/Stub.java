package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.RemoteStub;

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
	
	public void spread(int index, byte [] data) {
		byte [] sendData = new byte[data.length+1];
		sendData = data;
		sendData[data.length] = (byte)index;
		try {
			DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
			out.write(sendData);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}

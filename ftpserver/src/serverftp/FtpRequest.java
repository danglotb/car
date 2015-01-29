package serverftp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class FtpRequest extends Thread {

	/**
	 * Socket server
	 */
	private Socket serv;
	
	/**
	 * Boolean to loop on the processing
	 */
	private boolean end;

	/**
	 * Constructor
	 * @param serv
	 */
	public FtpRequest(Socket serv) {
		this.serv = serv;
		String ready = "220 ready\n";
		OutputStream out;
		try {
			out = serv.getOutputStream();
			DataOutputStream db = new DataOutputStream(out);
			db.writeBytes(ready);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.end = false;
	}

	public void run() {
		while (!end) {
			try {
				processRequest();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method processing request
	 * @throws IOException
	 */
	public void processRequest() throws IOException {
		InputStream in = this.serv.getInputStream();
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		String req = bf.readLine();
		Scanner sc = new Scanner(req);
		sc.useDelimiter(" ");
		String type = sc.next();
		String rep = "";
		/* switching on the type of the request */
		switch (type) {
		case "USER":
			rep = processUser(sc.next());
		case "PASS":
			rep = processPass(sc.next());
		case "QUIT":
			rep = processQuit();
		default:
			
		}
		/* send the response */
		OutputStream out;
		out = serv.getOutputStream();
		DataOutputStream db = new DataOutputStream(out);
		db.writeBytes(rep);
		/* close */
		sc.close();
	}

	/**
	 * method processing USER request
	 * @param req : username send by the user
	 * @return response for a USER request
	 */
	public String processUser(String req) {
		return "331 User name okay, need password.\n";
	}
	
	/**
	 * method processing PASS request
	 * @param req : password send by the user
	 * @return response for a PASS request
	 */
	public String processPass(String req) {
		return "";
	}
	
	/**
	 * method processing QUIT request
	 * set the boolean end to true to end the service
	 * @return response for a QUIT request
	 */
	public String processQuit() {
		this.end = true;
		return "";
	}
	/* TODO other process */

}

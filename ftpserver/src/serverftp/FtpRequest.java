package serverftp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FtpRequest extends Thread {

	private String current_directory;
	
	private String user;
	
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
	public FtpRequest(Socket serv, String directory) {
		this.serv = serv;
		this.user = "";
		this.current_directory = directory;
		OutputStream out;
		try {
			out = serv.getOutputStream();
			DataOutputStream db = new DataOutputStream(out);
			db.writeBytes(DefConstant.READY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.end = false;
	}

	/**
	 * main method which is calling processRequest while the end of service
	 */
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
			case DefConstant.USER:
				rep = processUser(sc.next());
				break;
			case DefConstant.PASS:
				rep = processPass(sc.next());
				break;
			case DefConstant.LIST:
				rep = processList();
				break;
			case DefConstant.SYST:
				System.out.println(DefConstant.SYST_INFO);
				rep = DefConstant.SYST_INFO;
				break;
			case DefConstant.QUIT:
				rep = processQuit();
				break;
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
		System.out.println("user");
		if (Server.userExist(req)) {
			this.user = req;
			return DefConstant.GOOD_USER;
		} else
			return DefConstant.WRONG_USER_OR_PASS;
	}
	
	/**
	 * method processing PASS request
	 * @param req : password send by the user
	 * @return response for a PASS request
	 */
	public String processPass(String req) {
		System.out.println("mdp");
		if (this.user.equals(""))
			return DefConstant.NEED_USER;
		if (Server.getPass(this.user, req)){ 
			System.out.println(DefConstant.GOOD_PASS);
			return DefConstant.GOOD_PASS;
			
		} else
			return DefConstant.WRONG_USER_OR_PASS;
	}
	
	/**
	 * method processing RETR request
	 * @param fileName : file to be sent on the server (
	 */
	public void processRetr(String fileName){
		
		
	}
	
	/**
	 *  method processing LIST request
	 *  @return fileList : list of files of current directory
	 */
	/* TODO: implement correct return codes */
	public String processList(){
		File directory = new File(DefConstant.CURRENT_DIR);
		File[] files = directory.listFiles();
		List<String> fileList = new ArrayList<String>();
		for(File file : files){
			fileList.add(file.toString());
		}
			return "350 request";
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

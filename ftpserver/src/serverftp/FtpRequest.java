package serverftp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FtpRequest extends Thread {

	/**
	 * Current directory of the ftp server
	 */
	private String currentDirectory;

	/**
	 * boolean represent the type of data connection (false = active connection, true = passiv connection)
	 */
	private boolean passivConnection;
	
	/**
	 * port used for the data connection
	 */
	private int port;
	
	/**
	 * adresse (as string) used for the data connection
	 */
	private String adr;
	
	/**
	 * Socket used for the data connection
	 */
	private Socket dataSocket;
	
	/**
	 * ServerSocket used for the data connection in case of a passiv connection
	 */
	private ServerSocket dataServerSocket;

	/**
	 * String of the username 
	 */
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
	 * 
	 * @param serv
	 */
	public FtpRequest(Socket serv, String directory) {
		this.serv = serv;
		this.user = "";
		this.passivConnection = false;
		// Adding "\" before directory name if not already here, won't be
		// understand by ftp client otherwise
		this.currentDirectory = (directory.startsWith("\\") ? directory : "\\"
				+ directory);
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
	 * 
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
		if (type != DefConstant.USER && this.user.equals(""))
			rep = DefConstant.NEED_USER;
		/* switching on the type of the request */
		switch (type) {
		case DefConstant.USER:
		case DefConstant.AUTH:
			rep = processUser(sc.next());
			break;
		case DefConstant.PASS:
			rep = processPass(sc.next());
			break;
		case DefConstant.PWD:
			System.out.println(257 + " " + this.currentDirectory + "\n");
			rep = DefConstant.SEND_PATH + this.currentDirectory + "\n";
			break;
		case DefConstant.PORT:
			rep = processPort(sc.next());
			break;
		case DefConstant.PASV:
			rep = processPasv();
			break;
		case DefConstant.LIST:
		case DefConstant.NLST:
			System.out.println(DefConstant.LIST);
			rep = processList();
			break;
		case DefConstant.SYST:
			System.out.println(DefConstant.SYST_INFO);
			rep = DefConstant.SYST_INFO;
			break;
		case DefConstant.FEAT:
			rep = DefConstant.FEAT_ERR;
			break;
		case DefConstant.TYPE:
			rep = processType();
			break;
		case DefConstant.RETR:
			System.out.println(DefConstant.RETR);
			rep = processRetr(sc.next());
			break;
		case DefConstant.STOR:
			System.out.print(DefConstant.STOR);
			rep = processStor(sc.next());
			break;
		case DefConstant.QUIT:
			rep = processQuit();
			break;
		default:
			System.out.println("unknown message type : " + type);
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
	 * method processing STOR equest
	 * 
	 * @param req
	 *            : filename
	 * @return
	 */
	public String processStor(String fileName) {
		Path path = Paths.get(fileName);
		OutputStream out;
		DataOutputStream db;
		InputStream in;
		BufferedReader bf;
	    try {
	    	out = this.serv.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(DefConstant.ACCEPT_REQ);
			
			this.dataSocket = new Socket(adr, port);
			in = this.dataSocket.getInputStream();;
			bf = new BufferedReader(new InputStreamReader(in));
			byte[] buffer = bf.readLine().getBytes();
			this.dataSocket.close();
			Files.write(path,buffer);
			} catch (IOException e) {
				e.printStackTrace();
		}
	    return DefConstant.ACCEPT_REQ;

	}

	/**
	 * method processing USER request
	 * 
	 * @param req
	 *            : username send by the user
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
	 * 
	 * @param req
	 *            : password send by the user
	 * @return response for a PASS request
	 */
	public String processPass(String req) {
		System.out.println("mdp");
		if (this.user.equals(""))
			return DefConstant.NEED_USER;
		if (Server.getPass(this.user, req)) {
			System.out.println(DefConstant.GOOD_PASS);
			return DefConstant.GOOD_PASS;

		} else
			return DefConstant.WRONG_USER_OR_PASS;
	}

	/**
	 * method processing RETR request
	 * 
	 * @param fileName
	 *            : file to be sent on the server (
	 */
	public String processRetr(String fileName) {
		Path path = Paths.get(fileName);
		OutputStream out;
		DataOutputStream db;
		byte[] buffer;
	    try {
	    	out = this.serv.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(DefConstant.ACCEPT_REQ);
			
			try {
				buffer = Files.readAllBytes(path);
			} catch (IOException e) {
				e.printStackTrace();
				return DefConstant.FILE_ERROR;
			}
			
			try {
	
			this.dataSocket = new Socket(adr, port);
			out = this.dataSocket.getOutputStream();
			db = new DataOutputStream(out);
			} catch (IOException e) {
				e.printStackTrace();
				return DefConstant.TCP_CONNECTION_FAILURE;
			}
			db.write(buffer);
			this.dataSocket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
	    	return DefConstant.FILE_TRANSFERT_SUCCESSFUL;
	}

	/**
	 * method processing LIST request
	 * 
	 * @return fileList : list of files of current directory
	 */
	/* TODO: implement correct return codes */
	public String processList() {
		File directory = new File(this.currentDirectory.substring(1));
		File[] files = directory.listFiles();
		String fileList = "";
		for (File file : files) {
			fileList += file.toString()+"\n";
		}
		System.out.println(fileList);
		OutputStream out;
		DataOutputStream db;
		try {
			out = this.serv.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(DefConstant.ACCEPT_REQ);
			
			this.dataSocket = new Socket(adr, port);
			out = this.dataSocket.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(fileList + "\n");
			this.dataSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DefConstant.END_REQ;
	}

	/**
	 * method process the type request
	 * 
	 * @return
	 */
	public String processType() {
		OutputStream out;
		try {
			out = this.serv.getOutputStream();
			DataOutputStream db = new DataOutputStream(out);
			db.writeBytes(DefConstant.SEND_TYPE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DefConstant.ACCEPT_TYPE;
	}

	/**
	 * method processing QUIT request set the boolean end to true to end the
	 * service
	 * 
	 * @return response for a QUIT request
	 */
	public String processQuit() {
		this.end = true;
		return "";
	}

	/**
	 * method to create the data connection
	 * 
	 * @param args
	 *            h1,h2,h3,h4,p1,p2
	 * @return
	 */
	public String processPort(String args) {
		Scanner sc = new Scanner(args);
		sc.useDelimiter(",");
		this.passivConnection = true;
		this.adr = sc.next();
		this.adr += "."+sc.next();
		this.adr += "."+sc.next();
		this.adr += "."+sc.next();
		this.port = Integer.parseInt(sc.next()) * 256 + Integer.parseInt(sc.next());
		return DefConstant.ACCEPT_PORT;
	}
	
	/**
	 * method to create a data connection in passiv 
	 * 
	 * @return
	 */
	public String processPasv() {
			OutputStream out;
			DataOutputStream db;
		 try {
			out = this.serv.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(DefConstant.REQ_PASV);
			
			this.passivConnection = true;
			
			this.dataServerSocket = new ServerSocket(DefConstant.DATA_PORT);
			this.dataSocket = this.dataServerSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return DefConstant.ACCEPT_PASV;
	}

	/**
	 * proxy method for create the good dataSocket (passiv or activ)
	 */
	public void openDataSocket() {
		InputStream in;
		try {
			in = this.serv.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			String req = bf.readLine();
			System.out.println(req);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

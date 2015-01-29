package serverftp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Serveur {

	/**
	 * Boolean to run the servers
	 */
	private static boolean end;
	/**
	 * Map wich is associated a login to a password (db)
	 */
	private static Map<String,String> accounts = new HashMap<String,String>();

	/**
	 * Check if the user is in the db
	 * @param login : the user who wants to connect to the server
	 * @return true if it is in the db, else false 
	 */
	public static boolean userExist(String login) {
		return accounts.containsKey(login);
	}
	
	/**
	 * check if the password correspond to the password store in the db
	 * @param login : key
	 * @param password : the password given by the user
	 * @return true if it macth, else false
	 */
	public static boolean getPass(String login, String password) {
		return accounts.get(login).equals(password);
	}
	
	/**
	 * stop the server
	 */
	public static void endService() {
		end = true;
	}
	
	/**
	 * Method to read and build the map accounts
	 */
	private static void buildMap(String path) {
		BufferedReader buffer;
		Scanner sc;
		String line;
		try {
			buffer = new BufferedReader(new FileReader(path+"/.account"));
			line = buffer.readLine();
			while (line != null) {
				sc = new Scanner(line);
				sc.useDelimiter(":");
				accounts.put(sc.next(), sc.next());
				line = buffer.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * main function, call a FtpRequest (Thread) object for each client connected to the server
	 * @param args : the file system
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		end = false;
		ServerSocket sock = new ServerSocket(1032);
		buildMap(args[0]);
		while (!end) {
			System.out.println("Waiting for client...");
			Socket serv = sock.accept();
			System.out.println("Client connected...");
			FtpRequest req = new FtpRequest(serv);
			req.start();
		}
		sock.close();
	}

}

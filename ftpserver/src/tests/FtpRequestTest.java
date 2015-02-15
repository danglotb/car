package tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import serverftp.DefConstant;

public class FtpRequestTest {

	static ServerTest serv;
	static Socket client;
	
	static Socket data;
	static ServerSocket dataSocket;
	
	static OutputStream out;
	static String msg;
	static DataOutputStream db;
	static InputStream in;
	static BufferedReader bf;
	
	/**
	 * set up the server and the client : call testProcessUSERAuthen() to login the client test
	 */
	@BeforeClass
	public static void setUp() {
		try {
			serv = new ServerTest();
			serv.start();
			dataSocket = new ServerSocket(8224);
			client = new Socket("localhost", 1032);
			/*verif connection is avaible */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			assertTrue(msg.equals("220 ready"));
			
			testProcessUSERAuthen();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void tearDown() {
		serv.stopServ();
	}
	
	/**
	 * test the authentication of the user
	 * Call the testProcessPort() in order to etablish the data connection
	 */
	public static void testProcessUSERAuthen() {
		try {
			msg = DefConstant.USER +" toto\n";
			/* envoie du username */
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			/* test de la reponse */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();

			assertTrue(msg.equals(DefConstant.GOOD_USER.substring(0,DefConstant.GOOD_USER.length()-1)));
			
			/* envoie du mot de passe */
			msg = DefConstant.PASS + " toto\n";
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			/* test de la reponse du server */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue(msg.equals(DefConstant.GOOD_PASS.substring(0,DefConstant.GOOD_PASS.length()-1)));
			
			testProcessPortInit();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	/**
	 * Etablishing the data connection
	 */
	public static void testProcessPortInit() {
		try {
			msg = DefConstant.PORT+" 127,0,0,1,32,32\n";
			/* envoie de la commande PORT*/
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			/* test de la reponse du server */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.ACCEPT_PORT));
			
		} catch (Exception e) {}
	}
	
	@Test
	public void testProcessLIST() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			msg = DefConstant.LIST+"\n";
			/* envoie de la commande LIST*/
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			/* test de la reponse du server */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.ACCEPT_REQ));
			
			data = dataSocket.accept();
			
			in = data.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			while (msg != null) {
				list.add(msg);
				msg = bf.readLine();
			}
			
			for (String s : list)
				System.out.println(s);
			
	/*		in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.FILE_TRANSFERT_SUCCESSFUL));
	*/	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testProcessRETR() {
		try {
			msg = DefConstant.RETR+" ftpserver/filesys/test\n";
			/* envoie de la commande LIST*/
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.ACCEPT_REQ));
			
			data = dataSocket.accept();
			
			in = data.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			System.out.println(msg);
			
			/* get the file 
			
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.FILE_TRANSFERT_SUCCESSFUL));
			
			*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testProcessSTOR() {
		byte[] buffer;
		try {
			Path p = Paths.get("teststor");
			buffer = Files.readAllBytes(p);
			
			msg = DefConstant.STOR+" ftpserver/filesys/test\n";
			/* envoie de la commande LIST*/
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.ACCEPT_REQ));
			
			data = dataSocket.accept();
			
			out = data.getOutputStream();
			db = new DataOutputStream(out);
			db.write(buffer);
			
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
/*			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.FILE_TRANSFERT_SUCCESSFUL));
	*/		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testProcessQUIT() {
		
	}
	
	/** 
	 * a copy of the test Process, in order to test the changement of the type of the data connection
	 */
	//@Test
	public void testProcessPORT() {
		try {
			msg = DefConstant.PORT+" 127,0,0,1,32,32\n";
			/* envoie de la commande PORT*/
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			/* test de la reponse du server */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.ACCEPT_PORT));
			
		} catch (Exception e) {}
	}
	
	@Test
	public void testProcessPASV() {
		try {
			msg = DefConstant.PASV+"\n";
			/* envoie de la commande PASV*/
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);

			/* test de la reponse du server */
/*			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.ACCEPT_PASV));
*/			
			data = new Socket("localhost", DefConstant.DATA_PORT);
			
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			assertTrue((msg+"\n").equals(DefConstant.ACCEPT_PASV));
			
		} catch (Exception e) {}
	}
	
}

package tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import serverftp.DefConstant;

public class FtpRequestTest {

	static ServerTest serv;
	static Socket client;
	static OutputStream out;
	static String msg;
	static DataOutputStream db;
	static InputStream in;
	static BufferedReader bf;
	
	@BeforeClass
	public static void setUp() {
		try {
			serv = new ServerTest();
			serv.start();
			client = new Socket("localhost", 1032);
			/*verif connection is avaible */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			assertTrue(msg.equals("220 ready"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void tearDown() {
		serv.stopServ();
	}
	
	@Test
	public void testProcessUSERAuthen() {
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
			
			testProcessPort();
			testProcessLIST();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Warning, need to stay at the root of filesys
	 */
	public void testProcessLIST() {
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testProcessRETR() {
		
	}
	
	public void testProcessSTOR() {
		
	}
	
	public void testProcessQUIT() {
		
	}
	
	public void testProcessPort() {
		try {
			msg = DefConstant.PORT+" 127,0,0,1,32,32,\n";
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
	
}

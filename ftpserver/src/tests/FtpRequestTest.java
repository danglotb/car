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
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Warning, need to stay at the root of filesys
	 */
	@Test
	public void testProcessLIST() {
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<String> sample = new ArrayList<String>();
		sample.add("test_folder");
		sample.add("new_test");
		sample.add("test");
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
			
			while (msg != null) {
				files.add(msg);
				msg = bf.readLine();
			}
			
		//	assertArrayEquals(sample.toArray(), files.toArray());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testProcessRETR() {
		
	}
	
	@Test
	public void testProcessSTOR() {
		
	}
	
	@Test
	public void testProcessQUIT() {
		
	}
	
}

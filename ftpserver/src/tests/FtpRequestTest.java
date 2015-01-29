package tests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void testProcessUSER() {
		msg = "USER toto\n";
		try {
			/* envoie du username */
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			/* test de la reponse */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();

			assertTrue(msg.equals("331 User name okay, need password."));
		} catch (IOException e) {
			e.printStackTrace();
		}
/*		assertTrue(ftp.processUser(username).equals( "331 User name okay, need password."));
		assertFalse(ftp.processUser(username).equals("430 Invalid username or password"));
*/	}
	
	@Test
	public void testProcessPASS() {
		
	}
	
	@Test
	public void testProcessLIST() {
		
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
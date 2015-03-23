package rest.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class general de Test pour la passerelle REST
 * 
 * \/ ! \\ verifiez que le serveur FTP est egalement lance (sur le port 9874 pour le moment...)
 * 
 */
public class RESTTest {
	
	/**
	 * StarterTest pour lancer la passerelle REST dans un autre Thread
	 */
	private static StarterTest st;
	
	
	/**
	 * Initialise les differentes classes utiles aux tests.
	 * Lance notamment la passerelle REST dans un autre Thread grace au StarterTest
	 */
	@BeforeClass
	public static void setUp() {
		st = new StarterTest();
		st.run();
	}
	
	/**
	 * Test la fonction getFileList() en envoyant une requete GET à l'url :
	 * http://127.0.0.1:8080/rest/api/file/
	 */
	@Test
	public void getFileListTest() {
		String urlStr = "http://127.0.0.1:8080/rest/api/file/";
		URL url;
		String ret = "";
		try {
			url = new URL(urlStr);
			URLConnection uConnection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uConnection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				ret +=inputLine;
			System.out.println(ret);
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test la methode d'envoye d'un fichier avec une requete POST
	 */
	@Test
	public void addFileTest() {
		
	}
	
}

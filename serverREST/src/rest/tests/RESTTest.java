package rest.tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.junit.Test;

/**
 * Class general de Test pour la passerelle REST
 * 
 * 
 * 
 * \/ ! \\ verifiez que la passerelle et le serveur FTP sont lances lance (sur le port 9874
 * pour le moment...)
 * 
 */
public class RESTTest {

	/**
	 * Test la fonction getFileList() en envoyant une requete GET à l'url :
	 * http://127.0.0.1:8080/rest/api/file/
	 */
	//@Test
	public void getFileListTest() {
		String urlStr = "http://127.0.0.1:8080/rest/api/file/";
		URL url;
		String ret = "";
		try {
			url = new URL(urlStr);
			URLConnection uConnection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					uConnection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				ret += inputLine;
			System.out.println(ret);
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/**
	 * Test la methode de connection
	 */
	public void connectionTest() {
		try {
			String url = "http://127.0.0.1:8080/rest/api/file";
			
			URL obj = new URL(url);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			con.setRequestMethod("POST");
	 
			String urlParameters = "username=user&password=12345";
			
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test la methode d'envoye d'un fichier avec une requete POST
	 */
	@Test
	public void addFileTest() {
		String strURL = "http://127.0.0.1:8080/rest/api/file/";
		String content, tmp;
		OutputStreamWriter writer = null;
		BufferedReader reader = null;
		try {
			
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("testAddFile")));

			content="content=";
			
			while ((tmp = reader.readLine()) != null)
				content+= URLEncoder.encode(tmp,"UTF-8");
			
			content+="&amp;name="+URLEncoder.encode("testAddFile","UTF-8");
			
			URL url = new URL(strURL);
			URLConnection uConnection = url.openConnection();
			uConnection.setDoOutput(true);
			
			writer = new OutputStreamWriter(uConnection.getOutputStream());
			writer.write(content);
			writer.flush();
			
			writer.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

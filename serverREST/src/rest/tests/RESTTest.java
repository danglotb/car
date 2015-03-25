package rest.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Class general de Test pour la passerelle REST
 * 
 * 
 * 
 * \/ ! \\ verifiez que la passerelle et le serveur FTP sont lances lance (sur
 * le port 9874 pour le moment...)
 * 
 */
public class RESTTest {

	/**
	 * Test la fonction getFileList() en envoyant une requete GET à l'url :
	 * http://127.0.0.1:8080/rest/api/file/
	 */
	// @Test
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


	/**
	 * Test la methode de connection
	 */
	@Test
	public void connectionTest() {
		try {
			String urlStr = "http://127.0.0.1:8080/rest/api/file";
			URL url = new URL(urlStr);

			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("username", "user");
			params.put("password", "12345");

			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(
						String.valueOf(param.getValue()), "UTF-8"));
			}

			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length",
					String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);

			Reader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			for (int c; (c = in.read()) >= 0; System.out.print(">>" + (char) c))
				;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test la methode d'envoye d'un fichier avec une requete POST
	 */
	@Test
	public void addFileTest() {
		try {
			String urlStr = "http://127.0.0.1:8080/rest/api/file";
			URL url = new URL(urlStr);

			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("content", "content_test".getBytes());
			params.put("name","file_test_add");

			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (postData.length() != 0)
					postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(
						String.valueOf(param.getValue()), "UTF-8"));
			}

			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length",
					String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);

			Reader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			for (int c; (c = in.read()) >= 0; System.out.print(">>" + (char) c))
				;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

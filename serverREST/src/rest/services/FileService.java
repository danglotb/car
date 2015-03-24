package rest.services;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

/**
 * Class qui communique avec le serveur FTP, et qui effectue les requetes
 * 
 * Les methodes de cet objet sont appelees par l'objet FileRestService apres
 * qu'il ait effectue la correspondance entre la requete HTTP et la methode
 * 
 */
public class FileService {

	private String cwd = null;
	private final String FTP_ADR = "127.0.0.1";
	private final int FTP_PORT = 9874;
	private FTPClient ftp;

	public void connectionFTP(String username, String password) {

		this.ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		this.ftp.configure(config);
		try {
			this.ftp.connect(FTP_ADR, FTP_PORT);
			this.ftp.login(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnectFTP() {
		try {
			this.ftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCwd() {
		try {
			return this.ftp.printWorkingDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setCwd(String cwd) {
		this.cwd = cwd;
	}

	/**
	 * @return la liste des fichiers du repertoire courant sous la forme d'un
	 *         tableau HTML
	 */
	public String getFileList(String cwd) {
		String htmlCode = GenerateHTML.getHeader()
				+ "<div id=\"table_liste_fichiers\">\n" + "<table>\n";
		try {
			if (cwd != null) {
				ftp.changeWorkingDirectory(cwd);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			FTPFile[] files = this.ftp.listFiles();
			// Scanner sc;
			htmlCode += "<tr><td rowspan=\5\"><a href=\"cwd/parent\">Parent Directory</a></td></tr>";
			for (FTPFile f : files) {
				htmlCode += "<tr>\n";
				// sc = new Scanner(f.toFormattedString());
				// sc.useDelimiter("\\s+");
				// htmlCode +=f.toFormattedString();
				String[] elements = f.toFormattedString().split("\\s+");
				// while (sc.hasNext())
				// htmlCode += "<td>"+sc.next()+"</td>";
				htmlCode += "<td>" + elements[0] + "</td>";
				// htmlCode += "<td>"+elements[1]+"</td>";
				// htmlCode += "<td>"+elements[2]+"</td>";
				htmlCode += "<td>" + elements[3] + "</td>";
				htmlCode += "<td>" + elements[4] + "</td>";
				htmlCode += "<td>" + elements[5] + "</td>";
				htmlCode += "<td>" + elements[6] + "</td>";
				// htmlCode += "<td>"+elements[7]+"</td>";
				if (elements[0].startsWith("d"))
					htmlCode += "<td><a onclick=\"reload()\" href=cwd/" + elements[8] + ">"
							+ elements[8] + "</a></td>";
				else
					htmlCode += "<td><a onclick=\"reload()\" href=" + elements[8] + ">"
							+ elements[8] + "</a></td>";
				// htmlCode += "<td>"+elements[9]+"</td>";

				htmlCode += "</tr>\n";
			}
		} catch (Exception e) {
			System.out.println("Exception !");
		}
		return htmlCode + "</table>\n" + "</div>\n" + "</body>\n" + "" +
				"<script>function reload(){" +
				"	window.location.reload();" +
				"}</script></html>\n";
	}

	/**
	 * Ajoute un fichier au serveur
	 * 
	 * @param content
	 *            : contenu du fichier a ajouter
	 * @param name
	 *            : nom du fichier a ajouter
	 */
	public void addFile(byte[] content, String name) {
		try {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			InputStream in = new ByteArrayInputStream(content);
			ftp.storeFile(name, in);
			in.close();
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Telecharge un fichier depuis le serveur
	 * 
	 * @param name
	 *            : nom du fichier a telecharger
	 * @param pathname
	 *            : path complet du fichier
	 * @return : le contenu du fichier sous forme d'un tableau d'octets (byte
	 *         [])
	 */
	public byte[] getFile(String pathname, String name) {
		try {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			if (pathname != null)
				ftp.changeWorkingDirectory(pathname);
			InputStream in = ftp.retrieveFileStream(name);
			if (in == null)
				System.out.println("ERROR RETR");
			else {
				byte [] b = new byte[1024];
				while (-1 != in.read(b)) ;
				in.close();
				ftp.disconnect();
				return b;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Supprime un fichier du serveur
	 * 
	 * @param filename
	 *            : nom fichier a supprimer
	 * @return - true si le serveur a reussis a le suppimer - faux sinon
	 */
	public boolean removeFile(String filename) {
		boolean res = false;
		try {
			res = ftp.deleteFile(filename);
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public void changeWorkingDirectory(String path) {
		try {
			ftp.changeWorkingDirectory(path);
			ftp.disconnect();
			this.cwd = path;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getLoginPage() {
		String htmlCode = GenerateHTML.getHeader()
				+ "<div id=\"login_form\">\n"
				+ "<form method=\"post\" action=\".\">\n"
				+ "<input type=\"text\" name=\"username\" placeholder=\"Username\"/>"
				+ "<input type=\"password\" name=\"password\" placeholder=\"Password\"/>"
				+ "<input type=\"submit\" name=\"Connect\"/>" + "</form>\n"
				+ "</div>\n";

		return htmlCode;
	}

	public String changeToParentDirectory(String pathname) {
		try {
			ftp.changeWorkingDirectory(pathname);
			ftp.changeToParentDirectory();
			this.cwd = ftp.printWorkingDirectory();
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.cwd;
	}

}

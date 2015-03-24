package rest.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

/**
 * Class qui communique avec le serveur FTP, et qui effectue les 
 * requetes
 * 
 * Les methodes de cet objet sont appelees par l'objet FileRestService apres qu'il ait
 * effectue la correspondance entre la requete HTTP et la methode
 * 
 */
public class FileService {
	
	
	private final String FTP_ADR = "127.0.0.1";
	private final int FTP_PORT = 9874;
	private String username;
	private String password;
	private String cwd;
	
	private FTPClient connectionFTP(String addr, int port, String username, String password){

		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		try {
			ftp.connect("127.0.0.1",9874);
			if(this.username != null && this.password != null){
				ftp.login(this.username, this.password);
				//if(this.cwd != null)
				//	this.changeWorkingDirectory(this.cwd);
			} else 
				ftp.login(username,password);
		} catch(Exception e){
			e.printStackTrace();
		}
		return ftp;
	}
	
	private void disconnectFTP(FTPClient ftp){
		try {
			ftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLogin() {
		return username;
	}

	public void setLogin(String login) {
		this.username = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCwd() {
		if(cwd == null && this.username != null && this.password != null){
			try {
				FTPClient ftp = connectionFTP(this.FTP_ADR, this.FTP_PORT, this.username, this.password);
				return ftp.printWorkingDirectory();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cwd;
	}

	public void setCwd(String cwd) {
		this.cwd = cwd;
	}
	/**
	 * @return la liste des fichiers du repertoire courant sous la forme d'un tableau HTML
	 */
	public String getFileList(String cwd) {
		String htmlCode = GenerateHTML.getHeader() + 
				"<div id=\"table_liste_fichiers\">\n"+
					"<table>\n";
		FTPClient ftp = this.connectionFTP("127.0.0.1", 9874, this.username, this.password);
		try {
			if(cwd != null){
				ftp.changeWorkingDirectory(cwd);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			FTPFile[] files = ftp.listFiles();
		//	Scanner sc;
			htmlCode +="<tr><td rowspan=\5\"><a href=\"cwd/parent\">Parent Directory</a></td></tr>";
			for (FTPFile f : files) {
				htmlCode += "<tr>\n";
				//sc = new Scanner(f.toFormattedString());
				//sc.useDelimiter("\\s+");
				//htmlCode +=f.toFormattedString();
				String[] elements = f.toFormattedString().split("\\s+");
				//while (sc.hasNext()) 
					//htmlCode += "<td>"+sc.next()+"</td>";
				htmlCode += "<td>"+elements[0]+"</td>";
				//htmlCode += "<td>"+elements[1]+"</td>";
				//htmlCode += "<td>"+elements[2]+"</td>";
				htmlCode += "<td>"+elements[3]+"</td>";
				htmlCode += "<td>"+elements[4]+"</td>";
				htmlCode += "<td>"+elements[5]+"</td>";
				htmlCode += "<td>"+elements[6]+"</td>";
				//htmlCode += "<td>"+elements[7]+"</td>";
				if(elements[0].startsWith("d"))
					htmlCode += "<td><a href=cwd/"+elements[8]+">"+elements[8]+"</a></td>";
				else
					htmlCode += "<td><a href="+elements[8]+">"+elements[8]+"</a></td>";
				//htmlCode += "<td>"+elements[9]+"</td>";

				htmlCode += "</tr>\n";
				this.disconnectFTP(ftp);
			}
		} catch (Exception e) {
			System.out.println("Exception !");
		}
		return htmlCode + "</table>\n"+
				"</div>\n"+"</body>\n"+
				"</html>\n";
	}

	/**
	 * Ajoute un fichier au serveur
	 * @param content : contenu du fichier a ajouter
	 * @param name : nom du fichier a ajouter
	 */
	public void addFile(byte[] content, String name) {
		FTPClient ftp = this.connectionFTP("127.0.0.1", 9874, "user", "12345");
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
	 * @param name : nom du fichier a telecharger
	 * @param pathname : path complet du fichier
	 * @return : le contenu du fichier sous forme d'un tableau d'octets (byte [])
	 */
public byte[] getFile(String pathname, String name){
		byte[] buffer = null;
		FTPClient ftp = this.connectionFTP("127.0.0.1", 9874, "user", "12345");
		try {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			if(pathname != null)
				ftp.changeWorkingDirectory(pathname);
			InputStream in = ftp.retrieveFileStream(name);
			buffer = new byte[in.available()];
			in.read(buffer);
			in.close();
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	/**
	 * Supprime un fichier du serveur
	 * @param filename : nom fichier a supprimer
	 * @return - true si le serveur a reussis a le suppimer
	 * 		   - faux sinon
	 */
	public boolean removeFile(String filename) {
		boolean res = false;
		FTPClient ftp = this.connectionFTP("127.0.0.1", 9874, "user", "12345");
		try {
			res = ftp.deleteFile(filename);
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}



	public void changeWorkingDirectory(String path) {
		FTPClient ftp = this.connectionFTP("127.0.0.1", 9874, "user", "12345");
		try {
			ftp.changeWorkingDirectory(path); 
			ftp.disconnect();
			this.cwd = path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public String getLoginPage() {
		String htmlCode = GenerateHTML.getHeader() + 
				"<div id=\"login_form\">\n"+
					"<form method=\"post\" action=\".\">\n"+
						"<input type=\"text\" name=\"username\" placeholder=\"Username\"/>"+
						"<input type=\"password\" name=\"password\" placeholder=\"Password\"/>"+
						"<input type=\"submit\" name=\"Connect\"/>"+
					"</form>\n"+
				"</div>\n";

		return htmlCode;
	}

	public String changeToParentDirectory(String pathname) {
		FTPClient ftp = this.connectionFTP("127.0.0.1", 9874, "user", "12345");
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

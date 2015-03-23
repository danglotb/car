package rest.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

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

	/**
	 * @return la liste des fichiers du repertoire courant sous la forme d'un tableau HTML
	 */
	public String getFileList() {
		String htmlCode = "<html>\n<table style=\"border: 1px solid black;\">\n";
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		try {
			ftp.connect("127.0.0.1",9874);
			ftp.login("user","12345");
			FTPFile[] files = ftp.listFiles();
			Scanner sc;
			for (FTPFile f : files) {
				htmlCode += "<tr>\n";
				sc = new Scanner(f.toFormattedString());
				sc.useDelimiter("\\s+");
				while (sc.hasNext()) 
					htmlCode += "<td>"+sc.next()+"</td>";
				htmlCode += "</tr>\n";
				ftp.disconnect();
			}
		} catch (Exception e) {
			System.out.println("Exception !");
		}
		return htmlCode + "</table>\n</html>";
	}

	/**
	 * Ajoute un fichier au serveur
	 * @param content : contenu du fichier a ajouter
	 * @param name : nom du fichier a ajouter
	 */
	public void addFile(byte[] content, String name) {
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		try {
			ftp.connect("127.0.0.1",9874);
			ftp.login("user","12345");
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
	 * @return : le contenu du fichier sous forme d'un tableau d'octets (byte [])
	 */
	public byte[] getFile(String name){
		byte[] buffer = null;
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		try {
			ftp.connect("127.0.0.1",9874);
			ftp.login("user","12345");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
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
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		try {
			ftp.connect("127.0.0.1",9874);
			ftp.login("user","12345");
			res = ftp.deleteFile(filename);
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}


}

package com.example.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;

import serverftp.DefConstant;

import com.example.Starter;
import com.example.exceptions.FileAlreadyExistsException;
import com.example.exceptions.FileNotFoundException;

public class FileService {
	private ConcurrentMap<String, File> files = new ConcurrentHashMap<String, File>();

	private OutputStream out;
	private DataOutputStream db;
	private InputStream in;
	private BufferedReader bf;

	public String getFile() {
		String msg = DefConstant.LIST + "\n";
		try {

			Socket client = Starter.connect();

			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);

			in = client.getInputStream();
			msg = bf.readLine();

			System.out.println(msg);

		} catch (IOException e) {
			e.printStackTrace();
		}// TODO : CHANGE DAT SHIT

		return null;
	}

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

	public java.io.File getByPath(String filePath) {

		Socket client = Starter.connect();
		String msg = "";
		InputStream input;
		DataInputStream dataIn;
		BufferedReader buffer;
		byte[] buff = null;
		try {
			msg = DefConstant.PORT + " 127,0,0,1,32,32\n";
			/* envoie de la commande PORT */
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);

			/* test de la reponse du server */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();

			msg = DefConstant.RETR + " " + filePath + "\n";
			/* envoie de la commande LIST */
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);

			Socket dataSocket = new ServerSocket(8224).accept();
			input = dataSocket.getInputStream();
			dataIn = new DataInputStream(input);

			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();

			// TODO:fix bug, buff = null
			dataIn.read(buff);
			FileOutputStream file = new FileOutputStream(filePath);
			file.write(buff);
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new java.io.File(filePath);
	}

	public File addFile(byte[] content, String name) {
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		try {
			ftp.connect("127.0.0.1",9874);
			ftp.login("user","12345");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//System.out.println("connected " + content );
			//path = path.replaceAll("%2F", "/");
			//System.out.println("path : " + path + " " + "content : " + content);
			InputStream in = new ByteArrayInputStream(content);
			/*BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			String str;
			while((str = bf.readLine()) != null){
				System.out.println(str);
			}*/
	        System.out.println("in build");
			boolean ret = ftp.storeFile(name, in);
			System.out.println("ret :" + ret);
			System.out.println("file stored");
			in.close();
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		File file = new File(path, name);
		if (files.putIfAbsent(path, file) != null) {
			throw new FileAlreadyExistsException(path);
		}*/
		return null;
	}

	public void removeFile(String filePath) {
		if (files.remove(filePath) == null) {
			throw new FileNotFoundException(filePath);
		}
	}

}

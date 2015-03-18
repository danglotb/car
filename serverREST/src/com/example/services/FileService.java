package com.example.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import serverftp.DefConstant;

import com.example.Starter;
import com.example.exceptions.FileAlreadyExistsException;
import com.example.exceptions.FileNotFoundException;
import com.example.model.File;

public class FileService {
	private ConcurrentMap<String, File> files = new ConcurrentHashMap<String, File>();
	
	private OutputStream out;
	private DataOutputStream db;
	private InputStream in;
	private BufferedReader bf;
		
	public String getFile() {
		String msg = DefConstant.LIST+"\n";
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
		}//TODO : CHANGE DAT SHIT
		
		return null;
	}
	
	public String getFileList() {
		Socket data;
		ServerSocket dataSocket;
		String msg = DefConstant.LIST+"\n";
		String htmlCode;
		htmlCode = "<html>"; 
		try {
			
			dataSocket = new ServerSocket(8224);
			
			Socket client = Starter.connect();
			
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			bf.readLine();
			
			/* Check if it is good
			if (DefConstant.ACCEPT_REQ.equals(bf.readLine()))
				and then configure the data connection
			*/
			
			/* Sending the request PORT config the data connection */
			db.writeBytes(DefConstant.PORT+" 127,0,0,1,32,32\n");
			
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			bf.readLine();
			
			/* Check if it is good 
			if (DefConstant.ACCEPT_PORT.equals(bg.readLine()+"\n"))
				and then open the data Socket
			 */
			
			data = dataSocket.accept();
			
			in = data.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			
			while ((msg = bf.readLine() ) != null) {
				htmlCode += msg + "</ br>";
			}
			
			System.err.println(htmlCode);
			
			dataSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		htmlCode += "</html>";
		
		return htmlCode;
 	}
	
	public File getByPath(final String filePath) {
		final File file = files.get(filePath);	
		if(file == null) {
			throw new FileNotFoundException(filePath);
		}
		return file;
	}

	public File addFile(String filePath, String name) {
		File file = new File(filePath, name);			
		if( files.putIfAbsent(filePath, file) != null ) {
			throw new FileAlreadyExistsException(filePath);
		}
		return file;
	}
	
	public void removeFile(String filePath) {
		if( files.remove(filePath) == null ) {
			throw new FileNotFoundException(filePath);
		}
	}

	
}

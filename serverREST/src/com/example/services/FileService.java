package com.example.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
			
			Socket client;
			client = new Socket("localhost", 1032);
			
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			in = client.getInputStream();
			bf.readLine();
			
			System.out.println();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}//TODO : CHANGE DAT SHIT
		
		return null;
	}
	
	public String getByPath(String filePath) {
		Socket client = Starter.connect();
		String msg = "";
		try {
			msg = DefConstant.PORT+" 127,0,0,1,32,32\n";
			/* envoie de la commande PORT*/
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			/* test de la reponse du server */
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
			
		} catch (Exception e) {}
		
		try {
			msg = DefConstant.RETR + " " + filePath +"\n";
			/* envoie de la commande LIST*/
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return filePath;
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

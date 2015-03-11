package com.example.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import serverftp.DefConstant;

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
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}//TODO : CHANGE DAT SHIT
		
		return null;
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

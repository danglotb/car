package com.example.exceptions;

import javax.ws.rs.WebApplicationException;


public class FileNotFoundException extends  WebApplicationException {

	private static final long serialVersionUID = 8651274799054282354L;

	public FileNotFoundException(String filePath) {
		System.err.println("File not found");
		
	}
}

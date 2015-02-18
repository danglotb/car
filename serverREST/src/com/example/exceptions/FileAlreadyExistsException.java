package com.example.exceptions;

import javax.ws.rs.WebApplicationException;

public class FileAlreadyExistsException extends  WebApplicationException {
	private static final long serialVersionUID = 0564600154541L;

	public FileAlreadyExistsException(String filePath){
		System.err.println("File " + filePath + " already exists !");
	}

}

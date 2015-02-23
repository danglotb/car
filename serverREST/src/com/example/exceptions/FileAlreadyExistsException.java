package com.example.exceptions;

public class FileAlreadyExistsException extends WebApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileAlreadyExistsException(String filePath){
		System.err.println("File " + filePath + " already exists !");
	}

}

package com.example.exceptions;

public class FileAlreadyExistsException extends Exception {
	
	public FileAlreadyExistsException(String filePath){
		System.err.println("File " + filePath + " already exists !");
	}

}

package com.example.tests;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.config.AppConfig;
import com.example.model.File;

public class FileTest {

	
	protected AppConfig  configure(){
		return new AppConfig();
	}
	
	
	protected File createFile(String filePath, String name){
		File file = new File(filePath, name);
		Entity<File> fileEntity = Entity.entity(file, MediaType.APPLICATION_JSON);
	//	File savedFile = target("/" + filePath).request(fileEntity).readEntity(File.class);
		return file;
	}
}

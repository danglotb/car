package com.example.rs;

import java.io.File;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.services.FileService;

@Path("/file")
//@Consumes(MediaType.APPLICATION_JSON)
public class FileRestService {
	
	@Inject	
	private FileService fileService;

	public FileRestService(){
		fileService = new FileService();
	}
	
/*	@Produces( { MediaType.APPLICATION_JSON  } )
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addFile(@Context final UriInfo uriInfo ,
			@FormParam("name") final String name, 
			@FormParam("path") final String path){
		System.out.println("name " + name + " path : " + path);
		fileService.addFile(path, name);
		System.out.println("name " + name + "path : " + path);
		return Response.created(uriInfo.getRequestUriBuilder().path(name).build()).build();
	}*/
	
	@GET
	public void addFile(@PathParam("name") final String name, 
			@PathParam("path") final String path){
		System.out.println("name " + name + " path : " + path);
		fileService.addFile(path, name);
		System.out.println("name " + name + "path : " + path);
	}
	
	@Produces({"text/html"})
	@GET
	public String getFileList() {
		return fileService.getFileList();
	}

	@Produces({MediaType.APPLICATION_OCTET_STREAM})
	@Path("/{filePath}")
	@GET
	public Response getFile(@PathParam("filePath") String filePath) {
		System.out.println("filePath : " + filePath);
		File file = fileService.getByPath(filePath);
		ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition",
	        "attachment; filename=" + file.getName());
	    return response.build();
	}

}

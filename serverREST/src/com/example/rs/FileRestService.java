package com.example.rs;

import java.net.Socket;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.Starter;
import com.example.model.File;
import com.example.services.FileService;

@Path("/file")
//@Consumes(MediaType.APPLICATION_JSON)
public class FileRestService {
	
	@Inject	
	private FileService fileService;

	public FileRestService(){
		fileService = new FileService();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addFile(@Context final UriInfo uriInfo ,@FormParam("name") String name, @FormParam("filePath") String filePath){
		fileService.addFile(filePath, name);
		System.out.println("name " + name + " filepath : " + filePath);
		return Response.created(uriInfo.getRequestUriBuilder().path(name).build()).build();
	}
	
	@Produces({ MediaType.APPLICATION_JSON})
	@GET
	public String getFileList() {
		return fileService.getFileList();
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{filePath}")
	@GET
	public File getFile(@QueryParam("filePath") final String filePath) {
		return fileService.getByPath(filePath);
	}

}

package com.example.rs;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.example.model.File;
import com.example.services.FileService;

@Path("/file")
public class FileRestService {

	@Inject private FileService fileService;
	
	@Produces( { MediaType.APPLICATION_JSON } )
	@GET
	public Collection<File> getFiles(@QueryParam( "page") @DefaultValue( "1" ) final int page ) {
		return fileService.getFile(page, 5);
	}
	
	
	@Produces( { MediaType.APPLICATION_JSON } )
	@Path ("/{filePath}")
	@GET
	public File getFile(@QueryParam( "filePath") final String filePath) {
		return fileService.getByPath(filePath);
	}
	
	
}

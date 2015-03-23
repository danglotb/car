package rest.rs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import rest.services.FileService;


@Path("/file")
@Consumes(MediaType.APPLICATION_JSON)
public class FileRestService {
	
	@Inject	
	private FileService fileService;

	public FileRestService(){
		fileService = new FileService();
	}
	
	@Produces( { MediaType.APPLICATION_JSON  } )
	@POST
	@Consumes("multipart/form-data")
	public Response addFile(@Context final UriInfo uriInfo , @Multipart("content") byte[] content, @FormParam("name")String name){
		fileService.addFile(content, name);
		return Response.created(uriInfo.getRequestUriBuilder().path(uriInfo.getPath()).build()).build();
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
		byte[] buffer = fileService.getFile(filePath);
		File file = new File(filePath);
		try {
			OutputStream out = new FileOutputStream(file);
			out.write(buffer);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition",
	        "attachment; filename=" + file.getName());
	    return response.build();
	}
	
	@Path("/{filename}")
	@Consumes
	@DELETE
	public Response removeFile(@PathParam("filename") String filename){
		
		boolean isDeleted = fileService.removeFile(filename);
		ResponseBuilder response = Response.ok((Object) isDeleted);
	    response.header("Content-Disposition",
	        "attachment; filename=" + filename);
	    return response.build();
	}

}

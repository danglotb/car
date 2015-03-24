package rest.rs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
	
	@Context
	private HttpServletRequest request;
	
	@Inject	
	private FileService fileService;

	public FileRestService(){
		fileService = new FileService();
	}
	
	private void getCurrentSession(){
		HttpSession session = request.getSession(true);
		
		String login = (String) session.getAttribute("login");
		String password = (String) session.getAttribute("password");
		String cwd = (String) session.getAttribute("cwd");
		if(login == null || password == null || cwd == null){
			//session.setAttribute("login", arg1);
			//session.setAttribute("password", arg1);
			//session.setAttribute("cdw", arg1);
		}
			
	}
	
	@GET
	@Path("/logout")
	public Response deconnection(@Context UriInfo uriInfo){
		HttpSession session = request.getSession();
		session.invalidate();
		return Response.created(uriInfo.getRequestUriBuilder().path(uriInfo.getPath()).build()).build();

	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response connection(@Context UriInfo uriInfo, @FormParam("username")String username, @FormParam("password") String password){
		fileService.setLogin(username);
		fileService.setPassword(password);
		HttpSession session = request.getSession(true);
		session.setAttribute("login", username);
		session.setAttribute("password", password);
		//session.setAttribute("cdw", fileService.getCwd());
		
		return Response.created(uriInfo.getRequestUriBuilder().path(uriInfo.getPath()).build()).build();
	}
	
	@Produces( { MediaType.APPLICATION_JSON  } )
	@POST
	@Consumes("multipart/form-data")
	public Response addFile(@Context final UriInfo uriInfo , @Multipart("content") byte[] content, @FormParam("name")String name){
		fileService.addFile(content, name);
		System.out.println(name + " " + content);
		return Response.created(uriInfo.getRequestUriBuilder().path(uriInfo.getPath()).build()).build();
	}
	
	@Produces({"text/html"})
	@GET
	public String getFileList() {
		HttpSession session = request.getSession(true);
		String login = (String) session.getAttribute("login");
		String password = (String) session.getAttribute("password");
		String cwd = (String) session.getAttribute("cwd");
		System.out.println("login : " + login + " password : " + password + " cwd :" + cwd);
		if(login == null || password == null )
			return fileService.getLoginPage();
		if(cwd == null)
			session.setAttribute("cwd", fileService.getCwd());
		
		return fileService.getFileList((String)session.getAttribute("cwd"));
	}

	@Produces({MediaType.APPLICATION_OCTET_STREAM})
	@Path("/{filePath}")
	@GET
	public Response getFile(@PathParam("filePath") String filename) {
		HttpSession session = request.getSession();
		String filepath = null;
		if(session.getAttribute("cwd") != null)
			filepath = (String) session.getAttribute("cwd");
		System.out.println("filePath : "+ filepath + "/"+ filename);
		byte[] buffer = fileService.getFile(filepath, filename);
		File file = new File(filename);
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
	
	@Path("/cwd/{path}")
	@GET
	public Response changeWorkingDirectory(@PathParam("path") String path){
		HttpSession session = request.getSession(true);
		String currPath = "";
		if(path.equals("parent")){
			currPath = this.fileService.changeToParentDirectory((String)session.getAttribute("cwd"));
		} else {
			currPath = session.getAttribute("cwd") + "/" + path;
			this.fileService.changeWorkingDirectory(path);
		}
		session.setAttribute("cwd", currPath);

	    return null;
	}

}

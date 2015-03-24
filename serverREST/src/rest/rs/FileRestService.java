package rest.rs;

import java.io.File;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import rest.services.FileService;


/**
 * Classe qui fait correspondre les Requetes (de type POST, GET, DELETE, PUT) a des Methode
 * de l'objet FileService
 */
@Path("/file")
@Consumes(MediaType.APPLICATION_JSON)
public class FileRestService {
	
	@Context
	private HttpServletRequest request;
	
	/**
	 * Objet FileService qui traite les requetes
	 */
	@Inject	
	private FileService fileService;

	/**
	 * Constructor
	 */
	public FileRestService(){
		fileService = new FileService();
	}

	
	@GET
	@Path("/logout")
	public Response deconnection(@Context UriInfo uriInfo){
		HttpSession session = request.getSession();
		session.invalidate();
		return Response.ok().build();

	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response connection(@Context UriInfo uriInfo, @FormParam("username")String username, @FormParam("password") String password){
		System.out.println("Connection");
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		fileService.connectionFTP(username, password);
		session.setAttribute("cdw", fileService.getCwd());
		fileService.disconnectFTP();
		return Response.ok().build();
	}
	
	/**
	 * Methode qui ajoute un fichier au serveur FTP
	 * @param uriInfo : URL : /api/rest/fil
	 * @param content : contenue du fichier a envoyer
	 * @param name : nom du fichier
	 * @return 
	 */
	@Produces("text/html")
	@POST
	@Consumes("multipart/form-data")
	public Status addFile(@Context final UriInfo uriInfo , @Multipart("content") byte[] content, @FormParam("name")String name){
		System.out.println("IN ADD FILE");
		HttpSession session = request.getSession(true);	
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		if(username == null || password == null){
			return Response.Status.UNAUTHORIZED;
		}
		fileService.connectionFTP(username, password);
		fileService.addFile(content, name);
		System.out.println(name + " " + content);
		fileService.disconnectFTP();
		//return "<script>location.reload(true)</script>";
		return null;
	}
	
	/**
	 * Methode qui recupere le contenu du dossier courant
	 * @return une Chaine HTML qui represente le tableau du contenu du repertoire
	 */
	@Produces({"text/html"})
	@GET
	public String getFileList() {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		String cwd = (String) session.getAttribute("cwd");
		fileService.connectionFTP(username, password);
		System.out.println("login : " + username + " password : " + password + " cwd :" + cwd);
		if(username == null || password == null )
			return fileService.getLoginPage();
		if(cwd == null)
			session.setAttribute("cwd", fileService.getCwd());
		String fileList = fileService.getFileList((String)session.getAttribute("cwd"));
		fileService.disconnectFTP();
		return fileList;
	}

	/**
	 * Methode qui recupere un fichier depuis le server
	 * @param filePath : chemin jusqu'au fichier que l'on souhaite recuperer
	 * @return
	 */
	@Produces({MediaType.APPLICATION_OCTET_STREAM})
	@Path("/{filePath}")
	@GET
	public Response getFile(@PathParam("filePath") String filename) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		String cwd = (String) session.getAttribute("cwd");
		fileService.connectionFTP(username, password);
		String filepath = null;
		if(cwd != null)
			filepath = cwd;
		System.out.println("filePath : "+ filepath + "/"+ filename);
		File file = fileService.getFile(filepath, filename);
		ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition",
	        "attachment; filename=" + file.getName());
		fileService.disconnectFTP();

	    return response.build();
	}
	
	/***
	 * Methode qui supprime un fichier du serveur
	 * @param filename : chemin jusqu'au fichier que l'on souhaite supprimer
	 * @return
	 */
	@Path("/{filename}")
	@Consumes
	@DELETE
	public Response removeFile(@PathParam("filename") String filename){
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		fileService.connectionFTP(username, password);
		System.out.println("DELETE");
		boolean isDeleted = fileService.removeFile(filename);
		ResponseBuilder response = Response.ok((Object) isDeleted);
	    response.header("Content-Disposition",
	        "attachment; filename=" + filename);
	    
	    //fileService.disconnectFTP();
	    return response.build();
	}
	
	@Path("/cwd/{path}")
	@GET
	public String changeWorkingDirectory(@PathParam("path") String path){
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		fileService.connectionFTP(username, password);
		String currPath = "";
		if(path.equals("parent")){
			currPath = this.fileService.changeToParentDirectory((String)session.getAttribute("cwd"));
		} else {
			currPath = session.getAttribute("cwd") + "/" + path;
			this.fileService.changeWorkingDirectory(path);
		}
		session.setAttribute("cwd", currPath);
		fileService.disconnectFTP();

	    return null;
	}

}

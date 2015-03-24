package rest.rs;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/connection")
public class ConnectionServerService {

	@POST
	public Response serverConnection(@Context final UriInfo uriInfo, @FormParam("username") String username, @FormParam("password") String password){
		return Response.created(uriInfo.getBaseUriBuilder().path("file").build()).build();
	}
	
}

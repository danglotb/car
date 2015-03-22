package com.example.rs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import serverftp.DefConstant;


@Path("/connection")
public class ConnectionServerService {

	private ServerSocket dataSocket;
	private Socket client;
	private InputStream in;
	private OutputStream out;
	private BufferedReader bf;
	private DataOutputStream db;
	
	@POST
	public Response serverConnection(@Context final UriInfo uriInfo, @FormParam("username") String username, @FormParam("password") String password){
		String msg;
		try {
			dataSocket = new ServerSocket(8224);
			client = new Socket("localhost", 1032);
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			msg = DefConstant.USER + " " + username + " \n";
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
			msg = DefConstant.PASS + " "+ password +" \n";
			out = client.getOutputStream();
			db = new DataOutputStream(out);
			db.writeBytes(msg);		
			in = client.getInputStream();
			bf = new BufferedReader(new InputStreamReader(in));
			msg = bf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//TODO : trouve a quoi ca sert (et s'en servir ?)
		//return Response.created(uriInfo.getRequestUriBuilder().path("connected").build()).build();
		return Response.created(uriInfo.getBaseUriBuilder().path("file").build()).build();

	}
	
}

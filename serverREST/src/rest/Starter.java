package rest;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import rest.config.AppConfig;


/**
 * Class Starter fournit dans l'archive TP2.
 * 
 * Elle contient un main qui
 * 	- initialise un serveur sur le port 8080
 * 	- ajoute le servletHolder
 * 	- demarre le serveur	
 * 
 */
public class Starter {
	public static void main( final String[] args ) throws Exception {
		Server server = new Server( 8080 );
		        
 		// Register and map the dispatcher servlet
 		final ServletHolder servletHolder = new ServletHolder( new CXFServlet() );
 		final ServletContextHandler context = new ServletContextHandler(); 		
 		context.setContextPath( "/" );
 		context.addServlet( servletHolder, "/rest/*" ); 	
 		context.addEventListener( new ContextLoaderListener() );
 		
 		context.setInitParameter( "contextClass", AnnotationConfigWebApplicationContext.class.getName() );
 		context.setInitParameter( "contextConfigLocation", AppConfig.class.getName() );
 		 		
        server.setHandler( context );
        server.start();
        server.join();	
	}
	
	
	
}


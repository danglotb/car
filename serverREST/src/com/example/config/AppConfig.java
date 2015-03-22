package com.example.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import car.HelloWorldResource;

import com.example.rs.ConnectionServerService;
import com.example.rs.FileRestService;
import com.example.rs.JaxRsApiApplication;
import com.example.rs.PeopleRestService;
import com.example.services.FileService;
import com.example.services.PeopleService;

@Configuration
public class AppConfig {	
	@Bean( destroyMethod = "shutdown" )
	public SpringBus cxf() {
		return new SpringBus();
	}
	
	@Bean @DependsOn( "cxf" )
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint( jaxRsApiApplication(), JAXRSServerFactoryBean.class );
		
		List<Object> serviceBeans = new ArrayList<Object>();
		serviceBeans.add(peopleRestService());
		serviceBeans.add(new HelloWorldResource());
		serviceBeans.add(fileRestService());
		serviceBeans.add(new ConnectionServerService());
		
		factory.setServiceBeans(serviceBeans);
		factory.setAddress( "/" + factory.getAddress() );
		factory.setProviders( Arrays.< Object >asList( jsonProvider() ) );
		return factory.create();
	}
	
	@Bean 
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}
	
	@Bean 
	public PeopleRestService peopleRestService() {
		return new PeopleRestService();
	}
	
	@Bean 
	public PeopleService peopleService() {
		return new PeopleService();
	}
	
	@Bean
	public FileService fileService(){
		return new FileService();
	}
	
	@Bean
	public FileRestService fileRestService(){
		return new FileRestService();
	}
		
	@Bean
	public JacksonJsonProvider jsonProvider() {
		return new JacksonJsonProvider();
	}
}

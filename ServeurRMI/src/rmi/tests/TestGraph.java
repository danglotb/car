package rmi.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rmi.implementations.SiteImplGraph;

public class TestGraph {

		private SiteImplGraph[] graph;
		private static Registry rmi;
		
		@AfterClass
		public static void closeRMI(){
			try {
				UnicastRemoteObject.unexportObject(rmi,true);
			} catch (NoSuchObjectException e) {
				fail("Error, cannot close Registry");
			}
		}
		
		@BeforeClass
		public static void initRMI(){
			try {
				rmi = LocateRegistry.createRegistry(1099);
			} catch (RemoteException e) {
				fail("Error registry creation");
			}
		}
		
		@Before
		public void init(){
			graph = new SiteImplGraph[4];
			try {
				graph[0] = new SiteImplGraph("1");
				graph[1] = new SiteImplGraph("2");
				Naming.bind("2", graph[1]);
				graph[0].addConnection("2");
				graph[1].addConnection("3");
				graph[2] = new SiteImplGraph("3");
				Naming.bind("3", graph[2]);
				graph[3] = new SiteImplGraph("4");
				Naming.bind("4", graph[3]);
				graph[2].addConnection("4");

			} catch (RemoteException | MalformedURLException | NotBoundException | AlreadyBoundException e) {
				e.printStackTrace();
				fail("Error creating nodes");
			}	
		}
		
		@Test
		public void testSpreadData(){
			byte[] data = "toto".getBytes();
			try {
				graph[0].spread(data, 0);
				Thread.sleep(100);
			} catch (RemoteException | InterruptedException e) {
				fail("Spread error");
			}
			assertTrue(Arrays.equals(data,graph[0].getData()));
			assertTrue(Arrays.equals(data,graph[1].getData()));
			assertTrue(Arrays.equals(data,graph[2].getData()));
			assertTrue(Arrays.equals(data,graph[3].getData()));
		}
		
}

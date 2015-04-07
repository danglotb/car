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

import rmi.implementations.SiteImplTree;

public class TestTree {

		private SiteImplTree[] tree;
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
			tree = new SiteImplTree[4];
			try {
				tree[0] = new SiteImplTree("1", null);
				tree[1] = new SiteImplTree("2", "1");
				Naming.bind("2", tree[1]);
				tree[0].addConnection("2");
				tree[1].addConnection("3");
				tree[2] = new SiteImplTree("3", "1");
				Naming.bind("3", tree[2]);
				tree[3] = new SiteImplTree("4", "2");
				Naming.bind("4", tree[3]);
				tree[2].addConnection("4");

			} catch (RemoteException | MalformedURLException | NotBoundException | AlreadyBoundException e) {
				e.printStackTrace();
				fail("Error creating nodes");
			}	
		}
		
		@Test
		public void testSpreadData(){
			try {
				tree[0].spread("toto".getBytes(), 0);
				Thread.sleep(5);

			} catch (RemoteException | InterruptedException e) {
				fail("Spread error");
			}
			assertTrue(Arrays.equals("toto".getBytes(),tree[3].getData()));
		}
		
}

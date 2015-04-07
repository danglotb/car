package rmi.tests;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import rmi.implementations.SiteImplTree;

public class TestTree {

		private SiteImplTree[] tree;
		
		
		@BeforeClass
		public void initRMI(){
			try {
				LocateRegistry.createRegistry(10023);
			} catch (RemoteException e) {
				fail("Error registry creation");
			}
		}
		
		@Before
		public void init(){
			tree = new SiteImplTree[4];
			try {
				tree[0] = new SiteImplTree("1", "-1");
				tree[1] = new SiteImplTree("2", "1");
				tree[2] = new SiteImplTree("3", "1");
				tree[3] = new SiteImplTree("4", "2");
			} catch (RemoteException e) {
				e.printStackTrace();
			}	
		}
		
}

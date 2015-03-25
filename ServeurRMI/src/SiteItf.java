import java.rmi.Remote;


public interface SiteItf extends Remote{
	
	public void spread();
	
	public void addFils(SiteItf fils);

}

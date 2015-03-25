import java.rmi.Remote;


public interface SiteItf extends Remote{
	
	
	public void addFils(SiteItf fils);
	public void spread(byte [] data);

}

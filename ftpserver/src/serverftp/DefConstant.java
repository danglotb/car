package serverftp;

public class DefConstant {
	
	public static final String ACCOUNT_FILE = ".account";

	/* list of type of request */
	
	public static final String USER = "USER";
	public static final String PASS = "PASS";
	public static final String QUIT = "QUIT";
	
	/* Response of the server */
	
	public static final String READY = "220 ready\n";
	
	
	public static final String GOOD_USER = "331 User name okay, need password.\n";
	
}

package serverftp;

public class DefConstant {
	
	/* file where is stored tuples of login / pass */
	public static final String ACCOUNT_FILE = ".account";

	/* list of type of request */
	public static final String USER = "USER";
	public static final String PASS = "PASS";
	public static final String RETR = "RETR";
	public static final String STOR = "STOR";
	public static final String LIST = "LIST";
	public static final String PWD  = "PWD";
	public static final String CWD  = "CWD";
	public static final String CDUP = "CDUP";
	public static final String QUIT = "QUIT";
	
	/* Response of the server */
	
	public static final String READY = "220 ready\n";
	public static final String GOOD_USER = "331 User name okay, need password.\n";
	
}

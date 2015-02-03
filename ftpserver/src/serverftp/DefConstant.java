package serverftp;

public class DefConstant {
	
	/* file where is stored tuples of login / pass */
	public static final String ACCOUNT_FILE = ".account";
	
	/* list of type of request */
	public static final String AUTH = "AUTH";
	public static final String USER = "USER";
	public static final String PASS = "PASS";
	public static final String SYST = "SYST";
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
	public static final String GOOD_PASS =  "230 User logged in, proceed. Logged out if appropriate.\n";
	public static final String NEED_USER = "332 Need account for login.\n";
	public static final String SYST_INFO = "215 LINUX";
	
	public static final String WRONG_USER_OR_PASS = "430 Invalid username or password.\n";
	
}

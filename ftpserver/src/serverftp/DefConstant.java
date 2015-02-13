package serverftp;

public class DefConstant {
	
	/* file where is stored tuples of login / pass */
	public static final String ACCOUNT_FILE = ".account";
	
	public static final int DATA_PORT = 1034;
	
	/* list of type of request */
	public static final String AUTH = "AUTH";
	public static final String USER = "USER";
	public static final String PASS = "PASS";
	public static final String SYST = "SYST";
	public static final String RETR = "RETR";
	public static final String STOR = "STOR";
	public static final String LIST = "LIST";
	public static final String FEAT = "FEAT";
	public static final String PWD  = "PWD";
	public static final String CWD  = "CWD";
	public static final String CDUP = "CDUP";
	public static final String TYPE = "TYPE";
	public static final String QUIT = "QUIT";
	
	/* request for data socket */
	public static final String PASV = "PASV";
	
	/* Response of the server */
	
	public static final String READY = "220 ready\n";
	public static final String GOOD_USER = "331 User name okay, need password.\n";
	public static final String GOOD_PASS =  "230 User logged in, proceed. Logged out if appropriate.\n";
	public static final String NEED_USER = "332 Need account for login.\n";
	public static final String SYST_INFO = "215 Linux\n";
	public static final String FEAT_ERR = "211 no-features\n";
	public static final String SEND_PATH = "257 ";
	public static final String SEND_TYPE = "150 File status okay; about to open data connection.\n";
	
	public static final String ACCEPT_PASV = "227 =127,0,0,1,"+DATA_PORT/256+","+DATA_PORT%256+"\n";
	
	public static final String WRONG_USER_OR_PASS = "430 Invalid username or password.\n";
	
}

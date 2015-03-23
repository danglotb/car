package serverftp;

public class DefConstant {
	
	/* file where is stored tuples of login / pass */
	public static final String ACCOUNT_FILE = ".account";
	public static final int DATA_PORT = 1034;
	public static final String ANONYMOUS = "anonymous";
	
	/* list of type of request */
	public static final String AUTH = "AUTH";
	public static final String USER = "USER";
	public static final String PASS = "PASS";
	public static final String SYST = "SYST";
	public static final String PORT = "PORT";
	public static final String PASV = "PASV";
	public static final String RETR = "RETR";
	public static final String STOR = "STOR";
	public static final String LIST = "LIST";
	public static final String NLST = "NLST";
	public static final String FEAT = "FEAT";
	public static final String PWD  = "PWD";
	public static final String XPWD  = "XPWD";
	public static final String CWD  = "CWD";
	public static final String CDUP = "CDUP";
	public static final String TYPE = "TYPE";
	public static final String QUIT = "QUIT";
	
	/* Response of the server */
	public static final String READY = "220 ready\r\n";
	public static final String GOOD_USER = "331 User name okay, need password.\r\n";
	public static final String GOOD_PASS =  "230 User logged in, proceed. Logged out if appropriate.\r\n";
	public static final String NEED_USER = "332 Need account for login.\r\n";
	public static final String SYST_INFO = "215 UNIX Type: L8\r\n";
	public static final String FEAT_ERR = "211 no-features\r\n";
	public static final String SEND_PATH = "257 ";
	public static final String CWDOK = "250 okay\r\n";
	public static final String SEND_TYPE = "150 File status okay; about to open data connection.\r\n";
	public static final String ACCEPT_TYPE = "200 accept Type\r\n";
	public static final String ACCEPT_REQ = "150  Starting file transfer\r\n";
	public static final String ACCEPT_PASV = "227 (127,0,0,1,"+DATA_PORT/256+","+DATA_PORT%256+")\r\n";
	public static final String ACCEPT_PORT = "200 data connection PORT\r\n";
	public static final String FILE_TRANSFERT_SUCCESSFUL = "226 File transfert completed\r\n";
	public static final String TCP_CONNECTION_FAILURE = "426 TCP connection broken by client or network failure\r\n";
	public static final String FILE_ERROR = "451 File read or write error\r\n";
	public static final String WRONG_USER_OR_PASS = "430 Invalid username or password.\r\n";
	public static final String CLOSE_CONNECTION = "221 Service cloising control connection.\r\n";
	
}

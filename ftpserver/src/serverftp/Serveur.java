package serverftp;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {

	private static boolean end;

	public static void endService() {
		end = true;
	}
	
	public static void main(String[] args) throws IOException {
		end = false;
		ServerSocket sock = new ServerSocket(1032);
		while (!end) {
			System.out.println("Waiting for client...");
			Socket serv = sock.accept();
			System.out.println("Client connected...");
			FtpRequest req = new FtpRequest(serv);
			req.start();
			
		}
		sock.close();
	}

}

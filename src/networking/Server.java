package networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Server {
	public static final int port = 40013;
	private static final int sleepTime = 200;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		ServerSocket serverSocket = new ServerSocket(port);
		Socket clientSocket;
		InetAddress h = InetAddress.getLocalHost();
		String s = h.getCanonicalHostName();
		System.out.println("host: " + h.getByName(s));
		
		System.out.println("Waiting for players...");
		clientSocket = serverSocket.accept();
		Scanner in = new Scanner(System.in);
		
		String message;
		while (!( message = in.nextLine()).equals("quit")){
			clientSocket.getOutputStream().write(56);
		}
	}
}

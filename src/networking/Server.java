package networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Network {
	public static final int port = 40013;
	private static final int sleepTime = 200;
	private static boolean isPlaying;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		ServerSocket serverSocket = new ServerSocket(port);

		
		InetAddress h = InetAddress.getLocalHost();
		String s = h.getCanonicalHostName();
		System.out.println("host: " + h.getByName(s));

		System.out.println("Waiting for players...");
		Socket clientSocket = serverSocket.accept();
		
		init(clientSocket);
		
		System.out.println("Found player!");
		
		if(!handshake()){
			System.out.println("Handshake failed :(");
			System.exit(-1);
		} else {
			System.out.println("Handshake succeeded! :)");
		}
		
		run();
		
		
	}

	public static boolean handshake() throws IOException{
		if (readLine().equals("hello")){
			writeLine("hello");
			if (readLine().equals("newgame")){
				if (!isPlaying){
					writeLine("ready");
					return true;
				} else {
					writeLine("reject");
				}
			}
		}
		return false;
	}
}

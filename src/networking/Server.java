package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
		
		socketInput = clientSocket.getInputStream();
		socketOutput = clientSocket.getOutputStream();

		System.out.println("Found player!");
		
		if (readLine().equals("hello")){
			writeLine("hello");
			if (readLine().equals("newgame")){
				if (!isPlaying){
					writeLine("ready");
				} else {
					writeLine("reject");
				}
			}
		}
		
		
		
		clientSocket.setSoTimeout(Numbers.soTimeout);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in),Numbers.bufferSize);

		
		String message;
		boolean quit = false;
		
		while (!quit){
			System.out.println(readLine());
			if (in.ready()){
				writeLine(in.readLine());				
			}
		}
	}

}

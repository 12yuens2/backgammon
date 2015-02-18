package networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Network {
	public static void main(String[] args) throws UnknownHostException, IOException{
		Socket call = new Socket("pc2-116-l", Server.port);
		Scanner in = new Scanner(System.in);
		
		socketInput = call.getInputStream();
		socketOutput = call.getOutputStream();
		
		if(!handshake(call)){
			System.out.println("Handshake failed :(");
			System.exit(-1);
		} else {
			System.out.println("Handshake succeeded! :)");
		}
		
		call.setSoTimeout(Numbers.soTimeout);
		String message;
		boolean quit = false;
		
		while (!( message = in.nextLine()).equals("quit")){
			System.out.println(readLine());
			writeLine(message);
		}
		
	}
	
	public static boolean handshake(Socket other) throws IOException{
		writeLine("hello");
		if (readLine().equals("hello")){
			writeLine("newgame");
			if (readLine().equals("ready")){
				return true;
			}
		}
		return false;
	}
}

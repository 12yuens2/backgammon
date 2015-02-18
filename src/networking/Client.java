package networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException{
		Socket call = new Socket("pc2-116-l", Server.port);
		Scanner in = new Scanner(System.in);
		
		String message;
		while (!( message = in.nextLine()).equals("quit")){
			call.getOutputStream().write(55);
		}
		
	}
}

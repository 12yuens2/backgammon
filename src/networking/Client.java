package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Network {
	public static void main(String[] args) throws UnknownHostException, IOException{
		Socket call = new Socket("pc2-042-l", Server.port);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in),Numbers.bufferSize);

		
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
		
		while (!quit){
			System.out.println(readLine());
			if (in.ready()){
				writeLine(in.readLine());
			}
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

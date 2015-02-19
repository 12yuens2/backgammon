package networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Network {
	public static void main(String[] args) throws UnknownHostException, IOException{
		Socket call = new Socket("pc2-042-l", Server.port);

		init(call);
		
		if(!handshake()){
			System.out.println("Handshake failed :(");
			System.exit(-1);
		} else {
			System.out.println("Handshake succeeded! :)");
		}
		
		run();
		
	}
	
	public static boolean handshake() throws IOException{
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

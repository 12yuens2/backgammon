package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public abstract class Network {
	public static final int port = 40013;
	protected static InputStream socketInput;
	protected static OutputStream socketOutput;
	
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in),Numbers.bufferSize);
	
	public static void init(Socket s) throws IOException{
		s.setSoTimeout(Numbers.soTimeout);
		socketInput = s.getInputStream();
		socketOutput = s.getOutputStream();
		
		run();
		
	}
	
	protected static void run() throws IOException {
		boolean quit = false;
		String sendingText;
		String recievedText;
		while (!quit){
			recievedText = readLine();
			if (in.ready()){
				sendingText = in.readLine();
				writeLine(sendingText);
			}
			if (recievedText != null){
				System.out.println(recievedText);
			}
		}
	}

	public static void writeLine(String line) {
		if (line == null || line.length() < 0){
			return;
		}

		try {
			int l = line.length();
			if (l > Numbers.bufferSize) {
				//	report("line too long (" + l + ") truncated to " + Numbers.bufferSize);
				l = Numbers.bufferSize;
			}
			socketOutput.write(line.getBytes(), 0, l);
		}
		catch (java.io.IOException e) {
			//	error("sendLine() problem " + e.getClass().getName());
		}
	}


	public static String readLine() throws IOException {
		String line = null;
		try{
			byte buffer[] = new byte[Numbers.bufferSize];
			int l = socketInput.read(buffer);
			if (l > 0) {
				line = new String(buffer, 0, l);
			}
		} catch (SocketTimeoutException e) {
			//ignore :)
		}
		return line;
		
	}
}

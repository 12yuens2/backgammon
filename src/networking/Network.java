package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public abstract class Network {

	final static int chatPort   =  4242; // the port number to be used
	final static int soTimeout  =    10; // ms to wait for socket read
	final static int readRetry  =    10; // # re-try of handshake
	final static int sleepTime  =   200; // ms to sleep - 200 is fine
	final static int bufferSize =   128; // # chars in line 

	public static final int port = 40013;
	protected static InputStream socketInput;
	protected static OutputStream socketOutput;

	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in),Network.bufferSize);

	public static void init(Socket s) throws IOException{
		s.setSoTimeout(Network.soTimeout);
		socketInput = s.getInputStream();
		socketOutput = s.getOutputStream();

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
			if (l > Network.bufferSize) {
				//	report("line too long (" + l + ") truncated to " + Numbers.bufferSize);
				l = Network.bufferSize;
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
			byte buffer[] = new byte[Network.bufferSize];
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

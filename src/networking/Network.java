package networking;

import game.Game;
import game.Move;
import game.PossibleMove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public abstract class Network {

	final static int chatPort   =  4242; // the port number to be used
	final static int soTimeout  =    10; // ms to wait for socket read
	final static int readRetry  =    10; // # re-try of handshake
	final static int sleepTime  =   200; // ms to sleep - 200 is fine
	final static int bufferSize =   128; // # chars in line 

	public static final int port = 40013;
	protected static InputStream socketInput;
	protected static OutputStream socketOutput;

	public static boolean isActive;
	
	static String sendingText;
	static String recievedText;
	
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in),Network.bufferSize);

	public static void init(Socket s) throws IOException{
		s.setSoTimeout(Network.soTimeout);
		s.setTcpNoDelay(true);
		socketInput = s.getInputStream();
		socketOutput = s.getOutputStream();

	}

	public static void addText(String s){
		sendingText = s;
	}
	
	public static void processText(String s){
		String processedText = s
				.replaceAll(":",",")
				.replaceAll(";","")
				.replaceAll("\\(","")
				.replaceAll("\\)","")
				.replaceFirst("\\-", "|")
				.trim();
		System.out.println(processedText);
		String[] turn = processedText.split(",");
		int[][] turnInts = new int[turn.length][2];
		for (int i = 0; i < turn.length; i++){
			try {
				turnInts[i][0] = Integer.parseInt(turn[i].split("\\|")[0]);
				turnInts[i][1] = Integer.parseInt(turn[i].split("\\|")[1]);				
			} catch (NumberFormatException e){
				System.out.println("Badly formatted turn string :(");
			}
		}
		Move.setDice(Game.gameBoard,turnInts[0]);
		for (int i = 1; i < turnInts.length; i++){
			if (turnInts[i][0] == -1 && turnInts[i][1] == -1){
				Move.passTurn(Game.gameBoard);
				return;
			}
			PossibleMove move = Move.find(turnInts[i][0],turnInts[i][1]);
			Move.executeMove(Game.gameBoard,move, false);
		}
	}
	
	public static void run() throws IOException {
		if (isActive){
			recievedText = readLine();
			if (sendingText != null){
				writeLine(sendingText);
				sendingText = null;
			}
			if (recievedText != null){
				System.out.println("NEW MOVE RECIEVED: \n" + recievedText);
				processText(recievedText);
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
	
	public static String getHostName(){
		InetAddress h;
		try {
			h = InetAddress.getLocalHost();
			String s = h.getCanonicalHostName();
			return ("" + InetAddress.getByName(s));
		} catch (UnknownHostException e) {
			return "???";
		} 
	}
}

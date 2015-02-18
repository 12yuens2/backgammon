package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class Network {
	public static final int port = 40013;
	protected static InputStream socketInput;
	protected static OutputStream socketOutput;
			
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

		byte buffer[] = new byte[Numbers.bufferSize];
		int l = socketInput.read(buffer);
		if (l > 0) {
			line = new String(buffer, 0, l);
		}

		return line;
	}
}

import java.awt.TextField;

/* Protocol:
 * 
 * input: 8-bits 
 * 	  opcode: 4-bits
 * 	       0 = client to server to set field
 *    	   1 = server to client to set "X"
 *         2 = server to client to set "O"
 *         3 = server to client to indicate win
 *         4 = server to client to indicate loss
 *         5 = server to client to indicate tie
 *         6 = server to client to indicate new round
 *         7 = server to client to indicate failure
 *    x: 2-bits X-coordinate
 *    y: 2-bits Y-coordinate
 * output: 
 *     decode command and value of x and y
 */

public class Protocol {
	public static final int SET = 0, SETX = 1, SETO = 2, WIN = 3, LOSS = 4,
	TIE = 5, NEWROUND = 6, FAIL = 7;
	public static final int DEFAULTPORT = 31137;

	public static int opcode(int input) {
		return (input & 0xF0) >> 4;
	}

	public static int x(int input) {
		return (input & 0xC) >> 2;
	}

	public static int y(int input) {
		return input & 3;
	}
	
	public static URI parseHost(TextField hostURI) {
		String[] socket = hostURI.getText().split(":");
		String hostIP = socket[0];
		int hostPort;
		
		if (socket.length > 1) {
			try {
				int port = Integer.parseInt(socket[1]);
				hostPort = (port >= 1024 && port < 0xFFFF) ? port : Protocol.DEFAULTPORT;
			} catch (NumberFormatException e) {
				hostPort = Protocol.DEFAULTPORT;
			}
		} else {
			hostPort = Protocol.DEFAULTPORT;
		}
		return new URI(hostIP, hostPort);
	}
}

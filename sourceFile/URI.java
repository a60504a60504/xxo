
public class URI {
	private final String IP;
	private final int Port;

	URI(String IP, int Port) {
		this.IP = IP;
		this.Port = Port;
	}

	public String getIP() {
		return IP;
	}
	
	public int getPort() {
		return Port;
	}
}

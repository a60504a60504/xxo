import java.io.IOException;
import java.net.Socket;

class Communicate2Client extends Thread {
	/**
	 * 
	 */
	private final ServerThread server;
	private Socket client;

	public Communicate2Client(ServerThread server, Socket client) {
		super("Client");
		this.server = server;
		this.client = client;
	}

	@Override
	public void run() {
		while (!this.server.done) {
			try {
				int got = client.getInputStream().read();
				if (got == -1)
					throw new IOException();
				if (Protocol.opcode(got) == Protocol.SET)
					this.server.set(client, Protocol.x(got), Protocol.y(got));
			} catch (IOException e) {
				this.server.killclient(this.server.client1);
				this.server.killclient(this.server.client2);
				break;
			}
		}
	}
}
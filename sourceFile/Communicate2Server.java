import java.io.IOException;

class Communicate2Server extends Thread {
	/**
	 * 
	 */
	private final ClientBox client;

	/**
	 * @param client
	 */
	Communicate2Server(ClientBox client) {
		this.client = client;
	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				byte got = (byte)this.client.server.getInputStream().read();
				int x = Protocol.x(got);
				int y = Protocol.y(got);
				if (Protocol.opcode(got) == Protocol.SETX) {
					this.client.grid[x][y] = ServerThread.States.X;
				} else if (Protocol.opcode(got) == Protocol.SETO) {
					this.client.grid[x][y] = ServerThread.States.O;
				}
				if (Protocol.opcode(got) == Protocol.WIN) {
					this.client.done = true;
					this.client.won = true;
				}
				if (Protocol.opcode(got)  == Protocol.LOSS) {
					this.client.done = true;
					this.client.won = false;
				}
				if (Protocol.opcode(got) == Protocol.TIE) {
					this.client.done = true;
					this.client.tie = true;
				}
				if (Protocol.opcode(got) == Protocol.NEWROUND) {
					this.client.done = false;
					this.client.tie = false;
					this.client.won = false;
					ServerThread.clearGrid(this.client.grid);
				}
				if (Protocol.opcode(got) == Protocol.FAIL) {
					new MessageBox(this.client.getTitle(), "Kicked by Server");
					this.client.setVisible(false);
					break;
				}
				this.client.repaint();
			} catch (IOException e) {
				if (!isInterrupted()) {
					new MessageBox(this.client.getTitle(), "Connection failed: " + e.getMessage());
					this.client.setVisible(false);
				}
				break;
			}
		}
	}
}
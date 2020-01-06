import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

class Mouse extends MouseAdapter {
	/**
	 * 
	 */
	private ClientBox client;

	/**
	 * @param client
	 */
	Mouse(ClientBox client) {
		this.client = client;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!this.client.done)
			set(e.getX()/100, e.getY()/100);
	}
	
	private void set(int x, int y) {
		try {
			int b = (x&3) << 2;
			b |= (y&3);
			this.client.server.getOutputStream().write(b);
		} catch (IOException e) {
			new MessageBox(this.client.getTitle(), "Connection failed: " + e.getMessage());
			this.client.setVisible(false);
		}
	}
}
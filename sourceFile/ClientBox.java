import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public final class ClientBox extends Frame {
	private static final long serialVersionUID = 0L;
	ServerThread.States[][] grid = new ServerThread.States[3][3];
	Socket server;
	boolean done = false;
	boolean won;
	boolean tie = false;
	private Communicate2Server get = new Communicate2Server(this);

	public ClientBox(URI objURI) throws UnknownHostException, IOException {
		super("Tic-Tac-Toe");
		setSize(300, 300);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int)(Math.random()*(dim.getHeight()-300)), (int)(Math.random()*(dim.getHeight()-300)));

		setResizable(false);
		try {
			this.server = new Socket(objURI.getIP(), objURI.getPort());
		} catch (IOException e) {
			dispose();
			throw e;
		}
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing (WindowEvent e) {
				done = true;
				get.interrupt();
				try {
					ClientBox.this.server.close();
				} catch (IOException io) {}
				dispose();
			}
		});
		addMouseListener(new Mouse(this));
		ServerThread.clearGrid(grid);
		get.start();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawLine(100, 0, 100, 300);
		g.drawLine(200, 0, 200, 300);
		g.drawLine(0, 100, 300, 100);
		g.drawLine(0, 200, 300, 200);
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == ServerThread.States.X) {
					g.drawString("X", i*100+50, j*100+50);
				} else if (grid[i][j] == ServerThread.States.O) {
					g.drawString("O", i*100+50, j*100+50);
				}
			}
		}
		if (done) {
			if (tie) {
				g.drawString("TIE", 100, 100);
			} else {
				if (won) {
					g.drawString("WINNER", 100, 100);
				} else {
					g.drawString("LOSER", 100, 100);
				}
			}
		}
	}


}

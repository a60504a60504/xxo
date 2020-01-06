import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainApplication extends Frame implements ActionListener {
	private static final long serialVersionUID = 0L;
	private TextField hostURI;
	private String hostIP;
	private int hostPort;
	private Button startClient, startServer, stopServer;
	private Server server = null;

	public MainApplication() {
		super("Tic-Tac-Toe");
		setLayout(new GridLayout(0,2));

		startClient = new Button("Start Client");
		startClient.addActionListener(this);
		add(startClient);

		hostURI = new TextField("localhost:31137");
		add(hostURI);

		startServer = new Button("Start Server");
		startServer.addActionListener(this);
		add(startServer);
		stopServer = new Button("Stop Server");
		stopServer.addActionListener(this);
		add(stopServer);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing (WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(200, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int)(Math.random()*(dim.getHeight()-200)), (int)(Math.random()*(dim.getHeight()-200)));
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startClient) {
			try {
				parseHost();
				new Client(hostIP, hostPort);
			} catch (IOException io) {
				new MessageBox(getTitle(), "Error connecting to Server: " + io.getMessage());
			}
		} else if (e.getSource() == startServer) {
			if (server != null)
				server.interrupt();
			parseHost();
			server = new Server(hostPort);
			server.start();
		} else if (e.getSource() == stopServer) {
			if (server != null)
				server.interrupt();
			server = null;
		}
	}

	public void parseHost() {
		String[] socket = hostURI.getText().split(":");
		hostIP = socket[0];
		if (socket.length > 1) {
			try {
				int port = Integer.parseInt(socket[1]);
				this.hostPort = (port >= 1024 && port < 0xFFFF) ? port : Protocol.DEFAULTPORT;
			} catch (NumberFormatException e) {
				this.hostPort = Protocol.DEFAULTPORT;
			}
		} else {
			this.hostPort = Protocol.DEFAULTPORT;
		}
	}

	public static void main(String[] args) {
		new MainApplication();
	}
}

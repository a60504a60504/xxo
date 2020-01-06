import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainApplication extends Frame implements ActionListener {
	private static final long serialVersionUID = 0L;
	private TextField hostURI;
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
		URI objURI;
		if (e.getSource() == startClient) {
			try {
				objURI = Protocol.parseHost(hostURI);
				new Client(objURI);
			} catch (IOException io) {
				new MessageBox(getTitle(), "Error connecting to Server: " + io.getMessage());
			}
		} else if (e.getSource() == startServer) {
			if (server != null)
				server.interrupt();
			objURI = Protocol.parseHost(hostURI);
			server = new Server(objURI);
			server.start();
		} else if (e.getSource() == stopServer) {
			if (server != null)
				server.interrupt();
			server = null;
		}
	}

	public static void main(String[] args) {
		new MainApplication();
	}
}

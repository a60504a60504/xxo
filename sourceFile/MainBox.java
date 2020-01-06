import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainBox extends Frame implements ActionListener {
	private static final long serialVersionUID = 0L;
	private TextField hostURI;
	private Button startClient, startServer, stopServer;
	private ServerThread server = null;

	public MainBox() {
		super("Tic-Tac-Toe");
		createClientButton();
		createUriTextfield();
		createStartServerButton();
		createStopServerButton();
		createWindow();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startClient) {
			startClientHandler();
		} else if (e.getSource() == startServer) {
			startServerHandler();
		} else if (e.getSource() == stopServer) {
			stopServerHandler();
		}
	}

	private void createClientButton() {
		startClient = new Button("Start Client");
		startClient.addActionListener(this);
		add(startClient);
	}

	private void createUriTextfield() {
		hostURI = new TextField("localhost:31137");
		add(hostURI);
	}

	private void createStartServerButton() {
		startServer = new Button("Start Server");
		startServer.addActionListener(this);
		add(startServer);
	}

	private void createStopServerButton() {
		stopServer = new Button("Stop Server");
		stopServer.addActionListener(this);
		add(stopServer);
	}

	private void createWindow() {
		setLayout(new GridLayout(0, 2));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(400, 400);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (Math.random() * (dim.getHeight() - 200)), (int) (Math.random() * (dim.getHeight() - 200)));
		setVisible(true);
	}

	private void startClientHandler() {
		URI objURI;
		try {
			objURI = Protocol.parseHost(hostURI);
			new ClientBox(objURI);
		} catch (IOException io) {
			new MessageBox(getTitle(), "Error connecting to Server: " + io.getMessage());
		}
	}
	
	private void startServerHandler() {
		URI objURI;
		if (server != null)
			server.interrupt();
		objURI = Protocol.parseHost(hostURI);
		server = new ServerThread(objURI);
		server.start();
	}
	
	private void stopServerHandler() {
		if (server != null)
			server.interrupt();
		server = null;
	}
}

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

public class MainBox extends Frame implements ActionListener {
	public TextField hostURI;
	public Button startClient, startServer, stopServer;
	public ServerThread server = null;
	private static final long serialVersionUID = 0L;
	private EventHandler event;
	private ButtonFactory btn;

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
			event = new StartClientHandler();
		} else if (e.getSource() == startServer) {
			event = new StartServerHandler();
		} else if (e.getSource() == stopServer) {
			event = new StopServerHandler();
		}
		event.createHandler(this);
	}

	private void createClientButton() {
		btn = new ClientButton();
		btn.createButton(this);
	}

	private void createStartServerButton() {
		btn = new ServerStartButton();
		btn.createButton(this);
	}

	private void createStopServerButton() {
		btn = new ServerStopButton();
		btn.createButton(this);
	}
	
	private void createUriTextfield() {
		hostURI = new TextField("localhost:31137");
		add(hostURI);
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
}

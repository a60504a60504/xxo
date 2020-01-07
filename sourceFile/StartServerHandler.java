import java.awt.TextField;

public class StartServerHandler implements EventHandler {
	private TextField hostURI;
	
	@Override
	public void createHandler(MainBox mainBox) {
		hostURI = mainBox.hostURI;
		URI objURI;
		if (mainBox.server != null)
			mainBox.server.interrupt();
		objURI = Protocol.parseHost(hostURI);
		mainBox.server = new ServerThread(objURI);
		mainBox.server.start();
	}


}

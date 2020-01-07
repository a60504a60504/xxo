import java.awt.TextField;
import java.io.IOException;

public class StartClientHandler implements EventHandler {
	private TextField hostURI;

	@Override
	public void createHandler(MainBox mainBox) {
		hostURI = mainBox.hostURI;
		URI objURI;
		try {
			objURI = Protocol.parseHost(hostURI);
			new ClientBox(objURI);
		} catch (IOException io) {
			new MessageBox(mainBox.getTitle(), "Error connecting to Server: " + io.getMessage());
		}
	}
}

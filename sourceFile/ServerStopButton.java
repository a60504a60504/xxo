import java.awt.Button;

public class ServerStopButton implements ButtonFactory{

	@Override
	public void createButton(MainBox mainbox) {
		mainbox.stopServer = new Button("Stop Server");
		mainbox.stopServer.addActionListener(mainbox);
		mainbox.add(mainbox.stopServer);
	}

}

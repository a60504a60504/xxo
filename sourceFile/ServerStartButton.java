import java.awt.Button;

public class ServerStartButton implements ButtonFactory{

	@Override
	public void createButton(MainBox mainbox) {
		mainbox.startServer = new Button("Start Server");
		mainbox.startServer.addActionListener(mainbox);
		mainbox.add(mainbox.startServer);
	}

}

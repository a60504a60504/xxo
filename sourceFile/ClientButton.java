import java.awt.Button;

public class ClientButton implements ButtonFactory{

	@Override
	public void createButton(MainBox mainbox) {
		mainbox.startClient = new Button("Start Client");
		mainbox.startClient.addActionListener(mainbox);
		mainbox.add(mainbox.startClient);		
	}

}

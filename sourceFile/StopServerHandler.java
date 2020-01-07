
public class StopServerHandler implements EventHandler {

	@Override
	public void createHandler(MainBox mainBox) {
		if (mainBox.server != null)
			mainBox.server.interrupt();
		mainBox.server = null;
	}

}

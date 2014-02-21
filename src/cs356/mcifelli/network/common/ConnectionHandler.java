package cs356.mcifelli.network.common;

public class ConnectionHandler implements IConnectionHandler{
	@Override
	public void HandleConnected(Connection sender) {
		Report(sender, "CONNECTED");
	}
	
	@Override
	public void HandleDisconnecting(Connection sender) {
		Report(sender, "DISCONNECTING");
	}

	@Override
	public void HandleDisconnected(Connection sender, String message) {
		if (message == null)
			Report(sender, "DISCONNECTED");
		else 
			Report(sender, "DISCONNECTED: " + message);
	}

	@Override
	public void Report(Connection sender, String message) {
		System.out.println(sender + "|:|" + message);
	}

	@Override
	public void MessageReceived(Connection sender, String from, String message) {
		Report(sender, "MESSAGE RECEIVED FROM " + from + ":\t" + message);
	}

	@Override
	public void MessageSent(Connection sender, String to, String message) {
		Report(sender, "MESSAGE SENT TO " + to + ":\t" + message);
	}

	@Override
	public void HandleConnecting(Connection sender) {
		Report(sender, "CONNECTING");
	}
}

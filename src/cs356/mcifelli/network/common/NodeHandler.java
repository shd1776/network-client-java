package cs356.mcifelli.network.common;

public class NodeHandler implements INodeHandler {
	@Override
	public void HandleListenerStarted(Node sender) {
		Report(sender, "LISTENER STARTED");
	}

	@Override
	public void HandleListenerStopped(Node sender) {
		Report(sender, "LISTENER STOPPED");
	}

	@Override
	public void HandleAddressCollision(Node node, String clientAddress) {
		Report(node, "CLIENT COLLISION FROM ADDRESS " + clientAddress);
	}

	@Override
	public void HandleClientConnected(Node node, Connection newConnection) {
		Report(node, "CLIENT CONNECTED: " + newConnection.getAddress());
	}

	@Override
	public void Report(Object sender, String report) {
		System.out.println(sender.toString() + "|:|" + report);
	}
}

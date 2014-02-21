package cs356.mcifelli.network.common;

public interface IConnectionHandler {
	void HandleConnected(Connection sender);
	void HandleDisconnected(Connection sender, String message);
	void Report(Connection sender, String message);
	void MessageReceived(Connection sender, String from, String message);
	void MessageSent(Connection sender, String from, String message);
	void HandleDisconnecting(Connection sender);
	void HandleConnecting(Connection sender);
}

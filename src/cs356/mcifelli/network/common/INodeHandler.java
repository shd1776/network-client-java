package cs356.mcifelli.network.common;

public interface INodeHandler extends IReporter{
	void HandleListenerStarted(Node node);
	void HandleListenerStopped(Node sender);
	void HandleAddressCollision(Node node, String clientAddress);
	void HandleClientConnected(Node node, Connection newConnection);
}

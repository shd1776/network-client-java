package cs356.mcifelli.network.common;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;

public class Node extends ConnectionHandler implements Runnable{
	private INodeHandler handler;
	private int port;
	private Dictionary<String, Connection> connections = new Hashtable<String, Connection>();
	private ServerSocket listener;
	private String name;
	
	private Node(INodeHandler handler, String name) {
		this.handler = handler;
		this.name = name;
	}
	
	public Node(INodeHandler handler, String name, int port) {
		this(handler, name);
		this.port = port;
	}
	
	public Node(INodeHandler handler, String name, ServerSocket listener) {
		this(handler, name, listener.getLocalPort());
		this.listener = listener;
	}
	
	public String toString() {
		return "NODE[" + port + "]";
	}

	@Override
	public void run() {
		if (listener == null) {
			try {
				// Bind listener to specified port.
				listener = new ServerSocket(port);
			} catch (IOException e) {
				// Notify handler of binding failure.
				handler.HandleListenerStopped(this);
				return;
			}
			// Notify handler of binding success.
			handler.HandleListenerStarted(this);
		}
		
		while (true) {
			// Get next client connection
			try {
				Socket newSocket = listener.accept();
				String clientAddress = newSocket.getInetAddress().getHostAddress();
				
				synchronized (connections) {
					if (connections.get(clientAddress) == null) {
						// Create new connection using that socket and add it to the list of connections.
						Connection newConnection = new Connection(this, newSocket);
						connections.put(newSocket.getInetAddress().getHostAddress(), newConnection);
						handler.HandleClientConnected(this, newConnection);
						new Thread(newConnection).start();
					} else {
						// Notify the handler that another client tried to connect from the same address.
						handler.HandleAddressCollision(this, clientAddress);
						try {
							newSocket.close();
						} catch (Exception e) {
							handler.Report(this, e.getMessage());
						}
					}
				}
			} catch (IOException e) {
				handler.HandleListenerStopped(this);
				break;
			}
		}
	}
	
	public void Send(String to, String message) {
		synchronized (connections) {
			// Look for existing connection.
			Connection conn = connections.get(to);
			
			// Create new connection if one does not exist.
			if (conn == null) conn = new Connection(this, to, port);
			
			// Send message
			conn.Send(name, to, message);
		}
	}

	public void Connect(String address) {
		synchronized (connections) {
			Connection connection = connections.get(address);
			if (connection != null) connection.Connect();
			else {
				connection = new Connection(this, address, port);
				connections.put(address, connection);
				connection.Connect();
				new Thread(connection).start();
			}
		}
	}
	
	@Override
	public void HandleDisconnected(Connection sender, String message) {
		super.HandleDisconnected(sender, message);
		synchronized (connections) {
			connections.remove(sender.getAddress());
		}
	}

	public void Disconnect(String address) {
		synchronized (connections) {
			
			Connection connection = connections.get(address);
			if (connection != null) {
				
				connection.Disconnect();
			}
		}
	}
}

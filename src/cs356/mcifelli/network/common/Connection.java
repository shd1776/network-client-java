package cs356.mcifelli.network.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class Connection implements Runnable{
	public enum Status {
		CONNECTING,
		CONNECTED,
		DISCONNECTING,
		DISCONNECTED
	}
	private IConnectionHandler handler;
	private Socket socket;
	private String address;
	private int port;
	//private Queue<Packet> awaitingSend = new LinkedList<Packet>();
	
	private Connection(IConnectionHandler handler) {
		this.handler = handler;
	}
	
	public Connection(IConnectionHandler handler, Socket socket) {
		this(handler);
		this.socket = socket;
		this.address = this.socket.getInetAddress().getHostAddress();
	}
	
	public Connection(IConnectionHandler handler, String address, int port) {
		this(handler);
		this.address = address;
		this.port = port;
	}
	
	public String toString() {
		return "CONNECTION[" + address + ":" + port + "]";
	}
	
	public boolean Connect() {
		if (socket != null) return true;
		
		handler.HandleConnecting(this);
		try {
			socket = new Socket(address, port);
		} catch (IOException e) {
			handler.HandleDisconnected(this, e.getMessage());
			return false;
		}
		
		handler.HandleConnected(this);
		return true;
	}
	
	public void Disconnect() {
		// Notify handler 
		handler.HandleDisconnecting(this);
		
		// Close the socket
		try {
			socket.close();
		} catch (IOException e) {
			
		}
		
		// Notify n handler of disconnect.
		handler.HandleDisconnected(this, null);
	}
	
	private boolean Send(byte[] data) {
		try {
			socket.getOutputStream().write(data);
		} catch (IOException e) {
			handler.HandleDisconnected(this, e.getMessage());
			return false;
		}
		return true;
	}
	
	private boolean Send(String message) {
		try {
			return Send(message.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}
	
	public boolean Send(Packet outgoing) {
		return Send(outgoing.toString());
	}
	
	public boolean Send(String from, String to, String message) {
		return Send(new MessagePacket(from, to, message));
	}

	public String getAddress() {
		return address;
	}

	@Override
	public void run() {
		DataInputStream stream = new DataInputStream(socket.getInputStream());
		while(socket != null && socket.isConnected() && !socket.isClosed()) {
			try {
				InputStream stream = socket.getInputStream();
				
				byte code = (byte) stream.read();
				
				// Read length bytes from stream.
				byte[] packetLengthBytes = new byte[4];
				
				if (stream.read(packetLengthBytes, 0, packetLengthBytes.length) == -1) break;
				int packetLength = Integer.
				
				// Read data from stream.
				
				// Build and handle incoming packet.
				handlePacket(new Packet(code, ""));
			} catch (IOException e) {
				handler.HandleDisconnected(this, e.getMessage());
				return;
			}
		}
		handler.HandleDisconnected(this, null);
	}
	
	private void handlePacket(Packet incoming) {
		switch (incoming.getCode()) {
			case Packet.Codes.PING:
				Send(new Packet(Packet.Codes.PONG, incoming.getId()));
				break;
			case Packet.Codes.ACK:
				
				break;
			case Packet.Codes.GOODBYE:
				Send(new Packet(Packet.Codes.ACK, incoming.getId()));
				Disconnect();
				break;
			case Packet.Codes.HELLO:
				
				break;
			case Packet.Codes.MESSAGE:
				
				break;
			case Packet.Codes.NACK:
				
				break;
		}
	}
}

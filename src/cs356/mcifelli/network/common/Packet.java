package cs356.mcifelli.network.common;

import java.io.UnsupportedEncodingException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Packet {
	public class Codes {
		public static final byte NACK = 0;
		public static final byte ACK = 1;
		public static final byte HELLO = 2;
		public static final byte GOODBYE = 3;
		public static final byte PING = 4;
		public static final byte PONG = 5;
		public static final byte MESSAGE = 6;
	}
	
	private byte code;
	//private Dictionary<String, String> objects = new Hashtable<String, String>();
	private String id;
	
	public Packet(byte code, String id) {
		this.code = code;
		this.id = id;
	}
	
	public Packet(byte[] serializedData) {
		if (serializedData.length > 0) 
			this.code = serializedData[0];
	}
	
	public byte[] Serialize() {
		String packet = code + "";
		try {
			return packet.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public byte getCode() {
		return code;
	}

	public String getId() {
		return id;
	}
}

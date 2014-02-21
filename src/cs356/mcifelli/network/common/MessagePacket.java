package cs356.mcifelli.network.common;

public class MessagePacket extends Packet{
	private String from;
	private String to;
	private String message;
	
	public MessagePacket(String from, String to, String message) {
		super(Packet.Codes.MESSAGE);
		this.from = from;
		this.to = to;
		this.message = message;
	}
	
	public MessagePacket(byte[] serializedData) {
		super(Packet.Codes.MESSAGE);
		
		// Deserialize into string
		String deserializedString = new String(serializedData);
		
		int lastIndex = 0;
		int currIndex = deserializedString.indexOf(' ');
		
		// Extract from
		from = deserializedString.substring(0, lastIndex);
		lastIndex = currIndex;
		currIndex = deserializedString.indexOf(' ', lastIndex);
		
		// Extract to
		to = deserializedString.substring(lastIndex, currIndex);
		lastIndex = currIndex;
		currIndex = deserializedString.indexOf(' ', lastIndex);
		
		// Extract message
		message = deserializedString.substring(lastIndex, currIndex);
	}
}


public class Message {
	MessageType type;
	int phase = 0;
	int hop = 0;
	int idmessage;
	
	
	public Message(MessageType type) {
		this.type = type;
	}
	
	/**
	 * This constructor is for PROBE type of messages
	 * @param type
	 * @param id
	 * @param ph
	 * @param hop
	 */
	public Message(MessageType type, int id, int ph, int hop) {
		this.type = type;
		this.idmessage = id;
		this.phase = ph;
		this.hop = hop;
	}
	
	/**
	 * This constructor is for TERMINATE type of messages
	 * @param type
	 * @param id
	 */
	public Message(MessageType type, int id) {
		this.type = type;
		this.idmessage = id;
	}
	
	/**
	 * This constructor is for REPLY type of messages
	 * @param type
	 * @param id
	 * @param phase
	 */
	public Message(MessageType type, int id, int phase) {
		this.type = type;
		this.idmessage = id;
		this.phase = phase;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public int getHop() {
		return hop;
	}

	public void setHop(int hop) {
		this.hop = hop;
	}

	public int getidmessage() {
		return idmessage;
	}

	public void setidmessage(int idmessage) {
		this.idmessage = idmessage;
	}
	
	
	
}

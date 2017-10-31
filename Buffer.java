import java.util.Observable;

/**
 * /**
 * Created by tphadke on 8/29/17.
 *
 *Buffer simulates like a channel between two processors
 * Messages being shared between processors are stored in buffer.
 * Both buffer & Processor class extends Observable is being observed by its observer processor
 * Whenever a message is set on buffer, buffer notifies its observer processor
 * Buffer notifies any observer by calling update() method.
 * 
 * @author 
 *
 */
public class Buffer extends Observable {
	Message message;
	String tag;
	
	
	public Buffer(String label) {
		this.tag = label;
	}
	
	public void setMessage(Message message) {
		this.message = message;
		setChanged();
		notifyObservers();
	}

	public Message getMessage() {
		return message;
	}

}

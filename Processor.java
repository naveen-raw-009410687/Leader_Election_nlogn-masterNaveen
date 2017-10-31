import java.util.Observable;
import java.util.Observer;

public class Processor extends Thread implements Observer {
	
	Buffer rightInBuf;
	 Buffer leftInBuf;
	 Buffer rightOutBuf;
	 Buffer leftOutBuf;
	 int processorID;
	 boolean leader;
	 Processor left;
	 Processor right;
	 
	 boolean replyFromLeft;
	 boolean replyFromRight;

	public Processor(int id, Buffer rightInBuf, Buffer leftInBuf, Buffer rightOutBuf, Buffer leftOutBuf) 
	
	{
		this.processorID = id;
		this.leader = false;
		this.rightInBuf = rightInBuf;
		this.leftInBuf = leftInBuf;
		this.rightOutBuf = rightOutBuf;
		this.leftOutBuf = leftOutBuf;
		this.leftInBuf.addObserver(this);
		this.rightInBuf.addObserver(this);
		this.replyFromLeft = false;
		this.replyFromRight = false;
	}
		
	public int getProcessorID() {
		return processorID;
	}

	public void setProcessorID(int processorID) {
		this.processorID = processorID;
	}

	public boolean isLeader() {
		return leader;
	}

	public void setLeader(boolean leader) {
		this.leader = leader;
	}

	public Processor getLeft() {
		return left;
	}

	public void setLeft(Processor left) {
		this.left = left;
		left.setRight(this);
	}

	public Processor getRight() {
		return right;
	}

	public void setRight(Processor right) {
		this.right = right;
	}

	//public boolean isAsleep() {
	//	return asleep;
//	}

	public void setAsleep(boolean asleep) {
	//	this.asleep = asleep;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Buffer buff = (Buffer) arg0;
		Message message = buff.getMessage();
		MessageType type = message.getType();
		int jId = message.getidmessage();
		int receivedPhase = message.getPhase();
		int receivedHop = message.getHop();
		
		switch(type) {
		case PROBE:
			System.out.println("Processor "+this.processorID+" received probe from Processor"+jId);
			if(buff == this.leftInBuf) {
				if(jId == this.processorID) {
					try {
						terminateAsLeader();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if((jId > this.processorID) && (receivedHop < Math.pow(2, receivedPhase))) {
					sendMessageToBuffer(new Message(MessageType.PROBE, jId, receivedPhase, receivedHop+1),this.rightOutBuf);
				}
				if((jId > this.processorID) && (receivedHop >= Math.pow(2, receivedPhase))) {
					sendMessageToBuffer(new Message(MessageType.REPLY, jId, receivedPhase), this.leftOutBuf);
				}
				if(jId < this.processorID) {
					swallow(jId);
				}
				
			}
			if(buff == this.rightInBuf) {
				if(message.getidmessage() == this.processorID) {
					try {
						terminateAsLeader();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if((jId > this.processorID) && (receivedHop < Math.pow(2, receivedPhase))) {
					sendMessageToBuffer(new Message(MessageType.PROBE, jId, receivedPhase, receivedHop+1),this.leftOutBuf);
				}
				if((jId > this.processorID) && (receivedHop >= Math.pow(2, receivedPhase))) {
					sendMessageToBuffer(new Message(MessageType.REPLY,jId, receivedPhase), this.rightOutBuf);
				}
				if(jId < this.processorID) {
					swallow(jId);
				}
			}
			break;
		
		case TERMINATE:
			if(jId == this.left.processorID) {
				System.out.println("TERMINATE message has traversed the ring. Terminating all processes.");
				this.interrupt();
			}
			else {
				System.out.println("Processor"+this.processorID+" is terminating..");
				sendMessageToBuffer(message, this.leftOutBuf);
				this.interrupt();
			}
			
			break;
		
		case REPLY:
			System.out.println("Processor "+this.processorID+" received reply message from "+jId);
			if(buff == this.leftInBuf) {
				this.replyFromLeft = true;
				if(jId != this.processorID) {
					sendMessageToBuffer(message, this.rightOutBuf);
				}
				else if(this.replyFromRight) {
					System.out.println("Intermediate Leader in this phase " + receivedPhase + " is Processor" + this.processorID);
					sendMessageToBuffer(new Message(MessageType.PROBE, this.processorID, receivedPhase+1, 1), this.leftOutBuf);
				}
			}
			if(buff == this.rightInBuf) {
				this.replyFromRight = true;
				if(jId != this.processorID) {
					sendMessageToBuffer(message, this.leftOutBuf);
				} else if(this.replyFromLeft) {
					System.out.println("Intermediate Leader in this phase " + receivedPhase + " is Processor" + this.processorID);
					sendMessageToBuffer(new Message(MessageType.PROBE, this.processorID, receivedPhase+1, 1), this.rightOutBuf);
				}
			}
			break;
		
		default:
			break;
		}
	}
	
	private void swallow(int jId) {
		// TODO Auto-generated method stub
		System.out.println("Message received from Processor "+jId+ " is swallowed at Processor "+this.processorID);
	}



	private void terminateAsLeader() throws InterruptedException {
		
		sendMessageToBuffer(new Message(MessageType.TERMINATE, this.processorID), this.leftOutBuf);
		this.leader = true;
		System.out.println("Processor "+this.processorID+" is terminating as leader.");
		
	}



	public void sendMessageToBuffer(Message msg, Buffer Buf) {
		Buf.setMessage(msg);
	}
	
	//@Override
	/*
	 * 
	 * Below run() method will 
	 * 
	 * Upon receiving no message
	if asleep then
	asleep = false
	phase = 0
	send [my-id, out, 1] to left and right*/
	@Override
	public void run() {
	
		{
			System.out.println("Processor "+this.processorID+" sending probe to Processor "+this.left.getProcessorID());
			sendMessageToBuffer(new Message(MessageType.PROBE, this.processorID, 0, 1), this.leftOutBuf);
			System.out.println("Processor "+this.processorID+" sending probe to Processor "+this.right.getProcessorID());
			sendMessageToBuffer(new Message(MessageType.PROBE, this.processorID, 0, 1), this.rightOutBuf);	
		}
	}
}

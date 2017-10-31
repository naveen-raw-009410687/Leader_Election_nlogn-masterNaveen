import javafx.css.StyleableStringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by tphadke on 8/29/17.
 * processor has its own message buffer, id, children if applicable,
 * a parent process if itself is not root, and a neighbor pj which may
 * or may not be the parent, but sends the message to this processor.
 */
public class Processor implements Observer {
    //Each processsor has a message Buffer to store messages
    Buffer messageBuffer ;
    Integer id ;
    Integer currentLeader;
    public Processor right;
    public Processor left;
    //private static final Object lockObject = new Object();
    
    
    /**
     * initialize processor's params
     */
    public Processor(Integer id) {
        messageBuffer = new Buffer();
        this.id = id; //This is an invalid value. Since only +ve values are acceptable as processor Ids.
        //Each processor is observing itself;
        messageBuffer.addObserver(this);
        this.currentLeader = -1;
        
    }
    
    public void setRight(Processor right) {
        this.right = right;
    }
    
    public void setLeft(Processor left) {
        this.left = left;
    }
    
    /**
     * send message to this processor with reference to sender as pj
     * @param message
     */
    //This method will add a message to this processors buffer.
    //Other processors will invoke this method to send a message to this Processor
    public void sendMessgeToMyBuffer(Message message){
        messageBuffer.setMessage(message);
    }
    
    
    /**
     * this method is called when the message buffer is updated with a new message
     * @param observable
     * @param arg
     */
    //This is analogous to recieve method.Whenever a message is dropped in its buffer this Pocesssor will respond
    //TODO: implement the logic of receive method here
    //      Hint: Add switch case for each of the conditions given in receive
    public void update(Observable observable, Object arg) {
        Message m = (Message) arg;
        System.out.println("This Processor id: " + this.id);
        System.out.println("Message Received: " + m.getId());
        System.out.println("Message Type: " + m.getMessage());
        String message = m.getMessage();
        int mId = m.getId();
        int hops = m.getHops();
        int phase = m.getPhase();
        
        
        
        if(message.equals("probe"))
        {
            if(mId == this.left.id)
            {
                if((mId > this.id) && (hops < Math.pow(2,phase)))
                {
                    Message lprobe = new Message(mId,"probe",phase,hops+1);
                    left.sendMessgeToMyBuffer(lprobe);
                }
                else if((mId > this.id) && (hops >= Math.pow(2,phase)))
                {
                    Message lprobe = new Message(mId,"reply",phase,hops);
                    left.sendMessgeToMyBuffer(lprobe);
                }
                else
                {
                    //swallow
                }
            }
            else if(mId == this.right.id) {
                
                if ((mId > this.id) && (hops < Math.pow(2, phase))) {
                    Message rprobe = new Message(mId, "probe", phase, hops + 1);
                    right.sendMessgeToMyBuffer(rprobe);
                } else if ((mId > this.id) && (hops >= Math.pow(2, phase))) {
                    Message rprobe = new Message(mId, "reply", phase, hops);
                    right.sendMessgeToMyBuffer(rprobe);
                } else {
                    //swallow
                }
            }
            else if(mId == this.id)
            {
                System.out.println("Leader has been selected, sending terminate messages.");
                Message term = new Message(mId,"terminate");
                left.sendMessgeToMyBuffer(term);
                right.sendMessgeToMyBuffer(term);
                // begin sending terminating message to all nodes
            }
        }
        else if(message.equals("reply"))
        {
            if(mId == this.left.id)
            {
                if(mId == this.id)
                {
                    System.out.println("Entering next phase with: " + this.id);
                    Message lprobe = new Message(mId,"probe",phase+1,1);
                    left.sendMessgeToMyBuffer(lprobe);
                }
                else
                {
                    right.sendMessgeToMyBuffer(m);
                }
            }
            else if(mId == this.right.id)
            {
                if(mId == this.id)
                {
                    System.out.println("Entering next phase with: " + this.id);
                    Message rprobe = new Message(mId,"probe",phase+1,1);
                    right.sendMessgeToMyBuffer(rprobe);
                }
                else
                {
                    left.sendMessgeToMyBuffer(m);
                }
            }
        }
        else if(message.equals("terminate"))
        {
            if(this.id == mId)
            {
                System.out.println("Terminate Message Received by Leader: " + this.id);
            }
            else
            {
                right.sendMessgeToMyBuffer(m);
                
            }
        }
        
        
        
    }
    
    
}
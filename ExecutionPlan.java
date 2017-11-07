

/**
 * 
 * @author 
 *Leader Election algorithm implementation steps init & executionPlan  
 */
public class ExecutionPlan {
	
	Processor p0, p1, p2, p3, p4,p5;

	public static void main(String[] args) throws InterruptedException {
		ExecutionPlan a = new ExecutionPlan();
		a.initiate();
		a.executionPlan();
	}
	
	/**this is the same Ring leader election as with did in class 
	 * This method creates Bufs and processors required for algorithm 
	 * We have Buf In & Out bidirectional both side for each processor hence 4 connections each Processor 
	 * */
	
	public void initiate() {
		
		//Buffer from Processor p0 to p1
		Buffer Buf01 = new Buffer("Buf01");
		//Buf from Processor p1 to p2
		Buffer Buf12 = new Buffer("Buf12");
		//Buf from Processor p2 to p3
		Buffer Buf23 = new Buffer("Buf23");
		//Buf from Processor p3 to p4
		Buffer Buf34 = new Buffer("Buf34");
		//Buf from Processor p4 to p5
		Buffer Buf45 = new Buffer("Buf45");
		//Buf from Processor p5 to p0
		Buffer Buf50 = new Buffer("Buf50");
		
		//Buf from Processor p0 to p5
		Buffer Buf05 = new Buffer("Buf05");
		//Buf from Processor p5 to p4
		Buffer Buf54 = new Buffer("Buf54");
		//Buf from Processor p4 to p3
		Buffer Buf43 = new Buffer("Buf43");
		//Buf from Processor p3 to p2
		Buffer Buf32 = new Buffer("Buf32");
		//Buf from Processor p2 to p1
		Buffer Buf21 = new Buffer("Buf21");
		//Buf from Processor p1 to p0
		Buffer Buf10 = new Buffer("Buf10");
		
		//Creating and building ring topology distributed NW processors 
		//Assigning Id, in-Buf and out-Buf to each processor 
		//Buffer rightInBuf, Buffer leftInBuf, Buffer rightOutBuf, Buffer leftOutBuf
		// Processor(int id, Buffer rightInBuf, Buffer leftInBuf, Buffer rightOutBuf, Buffer leftOutBuf) 
		
		p0 = new Processor(10,Buf50, Buf10, Buf05, Buf01);
		p1 = new Processor(22, Buf01, Buf21, Buf10, Buf12);
		p2 = new Processor(11, Buf12, Buf32, Buf21, Buf23);
		p3 = new Processor(66, Buf23, Buf43, Buf32, Buf34);
		p4 = new Processor(50, Buf34, Buf54, Buf43, Buf45);
		p5 = new Processor(44, Buf45, Buf05, Buf54, Buf50);
		
		//feeding Processors into Ring topology 
		p0.setLRing(p1);
		p1.setLRing(p2);
		p2.setLRing(p3);
		p3.setLRing(p4);
		p4.setLRing(p5);
		p5.setLRing(p0);
		
		
	}
	
	/**
	 * This method starts each processor thread
	 */
	public void executionPlan() throws InterruptedException {
		
		p0.start();
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
//		
		
	}
}

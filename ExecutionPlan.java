

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
	 * This method creates channels and processors required for algorithm 
	 * We have channel In & Out bidirectional both side for each processor hence 4 connections each Processor 
	 * */
	
	public void initiate() {
		
		//Channel from Processor p0 to p1
		Buffer channel01 = new Buffer("channel01");
		//Channel from Processor p1 to p2
		Buffer channel12 = new Buffer("channel12");
		//Channel from Processor p2 to p3
		Buffer channel23 = new Buffer("channel23");
		//Channel from Processor p3 to p4
		Buffer channel34 = new Buffer("channel34");
		//Channel from Processor p4 to p5
		Buffer channel45 = new Buffer("channel45");
		//Channel from Processor p5 to p0
		Buffer channel50 = new Buffer("channel50");
		
		//Channel from Processor p0 to p5
		Buffer channel05 = new Buffer("channel05");
		//Channel from Processor p5 to p4
		Buffer channel54 = new Buffer("channel54");
		//Channel from Processor p4 to p3
		Buffer channel43 = new Buffer("channel43");
		//Channel from Processor p3 to p2
		Buffer channel32 = new Buffer("channel32");
		//Channel from Processor p2 to p1
		Buffer channel21 = new Buffer("channel21");
		//Channel from Processor p1 to p0
		Buffer channel10 = new Buffer("channel10");
		
		//Creating and building ring topology distributed NW processors 
		//Assigning Id, in-channel and out-channel to each processor 
		//Buffer rightInChannel, Buffer leftInChannel, Buffer rightOutChannel, Buffer leftOutChannel
		// Processor(int id, Buffer rightInChannel, Buffer leftInChannel, Buffer rightOutChannel, Buffer leftOutChannel) 
		
		p0 = new Processor(10,channel50, channel10, channel05, channel01);
		p1 = new Processor(22, channel01, channel21, channel10, channel12);
		p2 = new Processor(11, channel12, channel32, channel21, channel23);
		p3 = new Processor(66, channel23, channel43, channel32, channel34);
		p4 = new Processor(50, channel34, channel54, channel43, channel45);
		p5 = new Processor(44, channel45, channel05, channel54, channel50);
		
		//Assigning left node to each processor as we are dealing with ring topology
		p0.setLeft(p1);
		p1.setLeft(p2);
		p2.setLeft(p3);
		p3.setLeft(p4);
		p4.setLeft(p5);
		p5.setLeft(p0);
		
		
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
		p0.join();
		p1.join();
		p2.join();
		p3.join();
		p4.join();
		p5.join();
		
	}
}

package lab.two.part.two;

public class LoopingProg implements Runnable {
	// need to know how many times to loop
	public int numberOfLoops;
	
	// create an instance with the number of loops that needs to be done
	LoopingProg (int numberOfLoops) {
		this.numberOfLoops = numberOfLoops;
	}
	
	// a method needed for the threading mechanism to work 
	public void run() {
		for (int i= 0; i < this.numberOfLoops; i++) {
			try {
			    Thread.sleep(500);
			    System.out.println("."+i);
			} catch(InterruptedException e) {
			    System.out.println("got interrupted!");
			}
		}
	}

}

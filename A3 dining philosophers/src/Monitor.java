/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	public enum Status {THINKING, HUNGRY, EATING};
    protected Status[] philosophers;
    
    protected int nbPhilosophers;
    protected boolean isSomeoneTalking;
    
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		nbPhilosophers = piNumberOfPhilosophers;
		philosophers = new Status[nbPhilosophers];
        isSomeoneTalking = false;
        
        for (int i = 0; i < nbPhilosophers; i++) {
        	philosophers[i] = Status.THINKING;
        }
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	private synchronized void test(int i) {
		//If hungry and the philosophers right and left are not eating (chopsticks are available)
		if((philosophers[i] == Status.HUNGRY) && (philosophers[(i - 1 + nbPhilosophers) % nbPhilosophers] != Status.EATING) 
				&& (philosophers[(i + 1) % nbPhilosophers] != Status.EATING)) {
			philosophers[i] = Status.EATING;
			notifyAll();
		}
	}
	
	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		int i = piTID - 1;
		philosophers[i] = Status.HUNGRY;
		
		test(i);
		
		//If the philosopher isn't eating, wait and try again
		while(philosophers[i] != Status.EATING) {
			try {
				wait();
			} catch (InterruptedException e) {
                System.out.println(e);
            }
            test(i);
		}
		
		philosophers[i] = Status.EATING;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		int i = piTID - 1;
		philosophers[i] = Status.THINKING;
		test((i - 1  + nbPhilosophers) % nbPhilosophers);
		test((i + 1) % nbPhilosophers);
		
		notifyAll();
	}

	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk(final int piTID)
	{
		int i = piTID - 1;
		
		while(isSomeoneTalking || philosophers[i] == Status.EATING) {
			try{
				wait();
			} catch (InterruptedException e) {
	            System.out.println(e);
	        }
		}
		
		isSomeoneTalking = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		isSomeoneTalking = false;
		notifyAll();
	}
}

// EOF

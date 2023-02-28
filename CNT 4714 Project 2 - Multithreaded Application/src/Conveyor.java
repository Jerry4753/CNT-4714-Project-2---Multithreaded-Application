import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Conveyor {
	// define attributes of a conveyor
	int conveyorNum;
	
	// define a lock on the conveyor method
	// a ReentrantLock() with no fairness policy
	// starvation is not an issue with this app
	
	private Lock theLock = new ReentrantLock();
	
	// constructor method - simply assign the conveyor its number
	public Conveyor(int conveyorNum) {
		this.conveyorNum = conveyorNum;
	}// end constructor method
	
	// method for routing stations to acquire a conveyor lock
	public boolean lockConveyor() {
		return theLock.tryLock();
	}// end method lockConveyor()
	
	// method for routing stations to release a conveyor lock
	public void unlockConveyor() {
		theLock.unlock();
	}
	
}// end class Conveyor

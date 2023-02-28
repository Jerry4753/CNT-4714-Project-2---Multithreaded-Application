import java.util.Random;

public class RoutingStation implements Runnable{
	protected Random gen = new Random();
	protected int stationNum, workload, workloadCounter;
	protected Conveyor inconveyor, outconveyor;
	protected boolean bothLocks = true;
	
	// RoutingStation constructor method
	public RoutingStation(int stationNum, int workload, Conveyor inconveyor, Conveyor outconveyor) {
		this.stationNum = stationNum;
		this.workload = workload;
		this.inconveyor = inconveyor;
		this.outconveyor = outconveyor;
		workloadCounter = workload;
	}// end constructor method
	
	//method for threads to go to sleep
	public void goToSleep() {
		try {
			Thread.sleep(gen.nextInt(500));
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// method for simulating Routing Station work - i.e., some time period during which the station is moving packages
	public void doWork() {
		System.out.println("\n * * * * * * Routing Station " + stationNum + ": * * * * CURRENTLY HARD AT WORK MOVING PACKAGES * * * * * * \n");
		System.out.println("Routing Station " + stationNum + ": successfully moves packages into station on input conveyor C" + inconveyor.conveyorNum + ".");
		System.out.println("Routing Station " + stationNum + ": successfully moves packages out of station on output conveyor C" + outconveyor.conveyorNum + ".");
		workloadCounter--;

		System.out.println("Routing Station " + stationNum + ": Station " + stationNum + " has " + workloadCounter + " package groups left to move.\n");
		
		goToSleep(); // hold the conveyors for a random period of time to simulate work flow
		
		if(workloadCounter == 0) {
			System.out.println("\n # # # # # Routing Station " + stationNum + 
					": WORKDLOAD SUCCESSFULLY COMPLETED. * * * Routing Station " + stationNum + " releasing locks and going offline");
		}
		
	}
	public void run() {
		// dump out the conveyor assignments and workload settings for the station - simulating output criteria
		System.out.println(" \n % % % % % ROUTING STATION " + stationNum + " Coming Online - Initializing Conveyors % % % % % \n");
		System.out.println("Routing Station " + stationNum + ": Input conveyor set to conveyor number C" + inconveyor.conveyorNum);
		System.out.println("Routing Station " + stationNum + ": Output conveyor set to conveyor number C" + outconveyor.conveyorNum);
		System.out.println("Routing Station " + stationNum + ": Workload set. Station " + stationNum + " has a total of " + this.workload + " package groups to move.\n");

		
		// run the simulation on the station for its entire workload
		for(int i = 0; i < workload; i++) {
			
			System.out.println("Routing Station " + stationNum + ": Entering Lock Acquisition Phase.");
			
			// begin acquiring locks - locks on both input and output conveyors are required to do work
			bothLocks = false;
			while(!bothLocks) {
				// get input conveyor
				if(inconveyor.lockConveyor()) {
					System.out.println("Routing Station " + stationNum + ": holds lock on input conveyor C" + inconveyor.conveyorNum);
					
					// get output conveyor
					if(outconveyor.lockConveyor()) {
						System.out.println("Routing Station " + stationNum + ": holds lock on output conveyor C" + outconveyor.conveyorNum);
						
						bothLocks = true;
						
						System.out.println("\n * * * * * Routing Station " + stationNum + ": holds locks on both input conveyor C" 
						+ inconveyor.conveyorNum + " and output conveyor C" +  outconveyor.conveyorNum);
						
						// start work flow - move packages
						doWork();
						
						// release both conveyors when work is done
						System.out.println("Routing Station " + stationNum + ": Entering Lock Release Phase.");						inconveyor.unlockConveyor();
						System.out.println("Routing Station " + stationNum + ": unlocks/releases input conveyor C" + inconveyor.conveyorNum);
						outconveyor.unlockConveyor();
						System.out.println("Routing Station " + stationNum + ": unlocks/releases output conveyor C" + outconveyor.conveyorNum);
					}
					else {
						// unlock the input conveyor if the output conveyor is busy
						inconveyor.unlockConveyor();
						System.out.println("Routing Station " + stationNum + ": unable to lock output conveyor C" + outconveyor.conveyorNum +
								", unlocks input conveyor C" + inconveyor.conveyorNum);
						// wait a bit before trying again
						goToSleep();
					}
				}
			}
		}
		System.out.println("\n\n @ @ @ @ @ @ ROUTING STATION " + stationNum + ": OFFLINE @ @ @ @ @ @ \n\n");
		
	}// end of run method
}

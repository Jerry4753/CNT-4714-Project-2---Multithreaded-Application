import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PackageManagementFacilitySimulator {

	static int MAX = 10;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			System.out.println("\n * * * * * * * * * * PACKAGE MANAGEMENT FACILITY SIMULATION BEGINS * * * * * * * * * *  \n");
			Scanner scnr = new Scanner(new File("config.txt"));
			
			// array list to store the integers from file
			ArrayList<Integer> config = new ArrayList<Integer>();
			
			// create thread pool of MAX size
			ExecutorService application = Executors.newFixedThreadPool(MAX);
			
			// read config.txt file into the array list config
			while(scnr.hasNext()) {
				int number = scnr.nextInt();
				config.add(number);
			}// config.txt content is now in arraylist config
			
			//close scanner
			scnr.close();
			
			// get the number of the routing stations
			int numRoutingStations = config.get(0);
			
			// assign the workloads to each station from the values in config
			for(int j = 0; j < numRoutingStations; j++){
				System.out.println("Routing Station " + j + " Has Total Workload Of " + config.get(j + 1));
			}
			System.out.println();
			System.out.println();
			
			// create an array of conveyor objects
			Conveyor [] arrayOfConveyors = new Conveyor[numRoutingStations];
			

			// fill the array with the conveyors for this simulation run
			for(int i = 0; i < numRoutingStations; i++) {
				arrayOfConveyors[i] = new Conveyor(i);
			}
			
			// create the routing stations for this simulation run
			for(int i = 0; i < numRoutingStations; i++) {
				try {
					// create new Routing stations					
					// start threads executing using the ExecutorService object
					application.execute(new RoutingStation(i, config.get(i + 1).intValue(), arrayOfConveyors[i], 
							arrayOfConveyors[i != 0 ? (i - 1) % numRoutingStations : numRoutingStations - 1]));
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			// shut down application
			application.shutdown();
			
			while(!application.isTerminated()) {
				// simulation running
				
			}
			System.out.println("\n * * * * * * * * * * ALL WORKLOADS COMPLETE * * * PACKAGE MANAGEMENT FACILITY SIMULATION ENDS * * * * * * * * * * \n");

		}catch(FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		

		
	}

}

**Title:  “Project 2:  Multi-threaded Programming in Java”**
 
**Objectives**:  To  practice  programming  an  application  with  multiple  threads  of  execution  and 
synchronizing their access to necessary shared objects. 
 
**Description**:  In this programming assignment you will simulate the package shipping management 
system for an automated package shipping operation similar to the one depicted here: 
 
  
This example package shipping operation has five routing stations (S0 – S4), each of which has an 
input and output conveyor connecting to conveyor lines (C0 – C4) that go elsewhere in the system.  
Resources were limited when the system was built so each conveyor going to the rest of the facility 
must  be  shared  between  two  routing  stations.    Since  each  routing  station  simultaneously  needs  an 
input and output connection to function, access to the shared conveyor lines must be strictly regulated.  
Flow direction in not important in our simulation. 
 
You have been hired to design a simulator for a new package management system being built with 
the same design, but possibly fewer/more stations.  You are to implement this simulator in Java and 
CNT 4714 – Project 2 – Spring 2022 
  Page 2 
have each routing station function in its own thread.  A routing station moves packages from one of 
its  connected  conveyors  to  the  other.    A  station’s  workload  is  the  number  of  times  that  a  routing 
station will move packages.  There are a varied and unspecified number of packages in a package 
group and each station will have different workloads (number of package groups).  A station must 
have exclusive access to the requested input and output conveyors during movement of packages.  A 
station will move packages for a random amount of time to simulate the random number of packages 
in  each  group.    Once  a  station  has  moved  all  of  the  packages  in  one  group,  it  will  reduce  its  total 
workload by 1 and go into an idle state (i.e., sleeping) for a random period of time before moving its 
next package group.  A routing station thread terminates when its workload reaches 0. 
 
To prevent deadlock from occurring, you must ensure that each routing station acquires the necessary 
locks in increasing numerical order (serial order). 
 
Restrictions: 
1. Your source files should begin with comments containing the following information: 
/* 
  Name: <your name goes here> 
  Course: CNT 4714 Spring 2022 
  Assignment title: Project 2 – Multi-threaded programming in Java 
  Date:  February 13, 2022 
 
  Class:  <name of class goes here> 
*/ 
2. You must use a the java.util.concurrent.locks.ReentrantLock  interface.  In 
other words, do not create your own locking system nor implement a Boolean semaphore-like 
system to control the locking. 
3. Do not use a monitor to control the synchronization in your program (i.e., do not use the Java 
synchronize statement).   
4. You  must  use  an  ExecutorService  object  to  manage  a  FixedThreadPool(MAX), 
where MAX is the upper limit on the number of stations which we’ll set to be 10 (see below 
under Input Specification). 
5. Your  station  threads  must  implement  the  Runnable  interface  and  not  extend  the  Thread 
class in order to utilize the ExecutorService object mentioned in 4 above. 
 
Input Specification: 
Your program must initially read from a text file (config.txt) to gather configuration information 
for the simulator.  The first line of the text file will be the number of routing stations to use during 
the simulation.  Afterwards, there will be one line for each station.  These lines will hold the workload 
value for each station (i.e, the number of times it needs to move packages on the conveyor system).  
Only use integers in your configuration file, decimals will not be needed.  You can assume that the 
maximum number of stations will be 10. 
 
Output Specification: 
Your simulator must output at least the following text to let the user know what the simulator is doing 
in each of these situations: 
 
 
 
  Page 3 
1. An input conveyor is assigned to a routing station: Routing Station X: Input conveyor set to conveyor number Cn. 
 
2. An output conveyor is assigned to a routing station: Routing Station X: Output conveyor set to conveyor number Cn. 
 
 
3. A routing station’s workload is set: Routing Station X: Workload set. Station X has a total of n package 
groups to move. 
 
4. A routing station is granted access to its input conveyor: Routing Station X: holds lock on input conveyor Cn. 
 
5. A routing station is granted access to its output conveyor: Routing Station X: hold lock on output conveyor Cn. 
 
6. A routing station unlocks its input conveyor:  Routing Station X: unlocks/releases input conveyor Cn. 
 
7. A routing station unlocks its output conveyor: Routing Station X: unlocks/releases output conveyor Cn. 
 
8. A routing station unable to lock its output conveyor and releases its input conveyor lock: Routing Station X: unable to lock output conveyor Cn, unlocks input 
conveyor Cm. 
 
9. A routing station has completed its workload: *  *  Station  X:  Workload  successfully  completed.  *  *  Station  X 
releasing locks and going offline * *  
 
10. A routing station successfully moves packages in and out of the routing station: Routing Station X: CURRENTLY HARD AT WORK MOVING PACKAGES. 
 
11. A routing station completes a workflow: Routing Station X: has n package groups left to move.

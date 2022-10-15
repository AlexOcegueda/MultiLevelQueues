package MultiLevelQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MultiLevelQueues {

	public static void main(String[] args) {
		MultiLevelQueues MLQ = new MultiLevelQueues();
		FileProcess[] ProcessList = new FileProcess[30];
		
		MLQ.simulate(ProcessList);
		MLQ.scheduling(ProcessList);

	}
	
	/*
	 *	FIFO
	 *
	 * BurstTime = Total time taken by the process for execution on CPU
	 * 
	 * FCFS is a non-preemptive scheduling algorithm 
	 * as a process holds the CPU until it either terminates or performs I/O. 
	 * Thus, if a longer job has been assigned to the CPU then many shorter jobs after it will have to wait
	 * 
	 * 			BT			Order
	 * ===========================
	 * P1		21			  1	
	 * P2		3			  2
	 * P3		3			  3
	 * 
	 */
	public void FCFS(Queue<FileProcess> q, Queue<FileProcess> priorityQ  ){
		System.out.println("Beginning FCFS");
		while(q.size() != 0) {
			
			simulateInterruption(priorityQ);
			
			if (priorityQ.size() != 0) { 
				System.out.println("Ending FCFS");
				System.out.println();
				return;
			}
			
			System.out.println("Finished after " + q.peek().getBurstTime() + "ms");
			q.remove();
			
		}
		System.out.println("Completed FCFS");
		System.out.println();
		
	}

	/*
		FIFO
		Quantum is a fixed time the process should be done with

			BT		Cycles to completion
		==================================
		p1	10			3
		p2	4			1
		p3	5			2
		
		
		RR is Preemptive which means it 
		temporarily interrupts an executing task, 
		with the intention of resuming it at a later time
	 
	 */
	public void roundRobin(Queue<FileProcess> q) {
		
		System.out.println("Beginning Round Robin");
		
		int quantum = 4; // fixed time the process should be done with

		// iterates through all file processes until each file is executed
		while(q.size() != 0){

			if (q.peek().getBurstTime() - quantum > 0) {
				int before = q.peek().getBurstTime();

				// subtracting quantum from burstTime
				q.peek().setBurstTime(q.peek().getBurstTime() - quantum);

				int after = q.peek().getBurstTime();

				// adding file process to end of queue
				FileProcess slowProcess = q.remove();

				q.add(slowProcess);
				System.out.println("Process too slow, on to the next one\n" + "started at " + before + "ms ended with " + after + "ms");
				System.out.println();

			} else {
				
				// file successfully finished in time
				q.remove();
				System.out.println("Successfully finished" );
			}

		}

		System.out.println("Completed Round Robin");
		System.out.println();

	}
	
	public void scheduling(FileProcess[] ProcessList) {
		
		// in a fixed priority system: lower levels wont go unless upper ones are completely empty
		Queue<FileProcess> highPriority = new LinkedList<>(); // system processes
		Queue<FileProcess> medPriority = new LinkedList<>(); // interactive processes
		Queue<FileProcess> lowPriority = new LinkedList<>(); // student processes
		
		// Sort out all files by their priority level
		for (FileProcess fp : ProcessList) {
			
			if (fp.getPriority() == 1) {
				highPriority.add(fp);
			} else if (fp.getPriority() == 2) {
				medPriority.add(fp);
			}else {
				lowPriority.add(fp);
			}
			
		}

		while (highPriority.size() != 0 &&
				medPriority.size() != 0 &&
				lowPriority.size() != 0) {


			roundRobin(highPriority); // finish all high priorities first


			// checking if there is a higher priority process before continuing with medium ones
			if (highPriority.size() == 0) {
				FCFS(medPriority, highPriority);
			} else {
				continue;
			}

			// checking if both other higher priority processes are empty
			if (highPriority.size() == 0 &&
				medPriority.size() == 0) {
				FCFS(lowPriority, medPriority);
			} 

		}
		System.out.println("All Files Processed");
		
	}

	/*
		Creates process files with random data and adds it to the process list array
	 */
	public void simulate(FileProcess[] ProcessList) {

		for (int i = 0; i < ProcessList.length; i++) {

			ProcessList[i] = createFile();
		
		}
	}

	/*
		Creates a new FileProcess with randomly generated data.
	 */
	public FileProcess createFile(){
		long start = System.currentTimeMillis();

		Random rand = new Random();

		int burstTime = rand.nextInt(10) + 1; // Total time taken by the process for execution on CPU
		int arrivalTime = rand.nextInt(5) + 1; // time when process enters into ready state and is ready for execution
		int exitTime = rand.nextInt(5) + 1; // time when process completes execution and exits from system
		int turnaroundTime = exitTime - arrivalTime; // total amount of time spent by the process from coming in the ready state for the first time to its completion.
		int completionTime = exitTime - (int) start; // time it exited - time it started
		int waitingTime = turnaroundTime - burstTime; // turnaroundTime - burstTime
		int priority = rand.nextInt(3) + 1; // determines if it's high, med, or low

		return new FileProcess(burstTime, arrivalTime, waitingTime, turnaroundTime, completionTime, priority);

	}

	public void simulateInterruption(Queue<FileProcess> priorityQ) {
		Random rand = new Random();
		int chance = rand.nextInt(11) + 1;

		// Chance to simulate an interruption of a higher priority file being added in
		if (chance == 1){
			System.out.println("New High Priority Task entered");
			priorityQ.add(createFile());
		}
	}
}

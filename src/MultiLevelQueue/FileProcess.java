package MultiLevelQueue;

public class FileProcess {
	
	int burstTime = 0;
	int waitingTime = 0;
	int turnaroundTime = 0;
	int completetionTime = 0;
	int priority = 0;
	
	public FileProcess(int burstTime, int arrivalTime, int waitingTime, int turnaroundTime, int completetionTime, int priority) {
		this.burstTime = burstTime;
		this.waitingTime = waitingTime;
		this.turnaroundTime = turnaroundTime;
		this.completetionTime = completetionTime;
		this.priority = priority;
	}

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getTurnaroundTime() {
		return this.turnaroundTime;
	}

	public int getCompletetionTime() {
		return completetionTime;
	}

	public void setCompletetionTime(int completetionTime) {
		this.completetionTime = completetionTime;
	}
	
	
}

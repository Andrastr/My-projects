package uk.ac.aber.cs21120.solution;

import uk.ac.aber.cs21120.hospital.IJob;
import uk.ac.aber.cs21120.hospital.ISimulator;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Simulator implements ISimulator {
    private int numAmbulances;
    private int tick = 0;

    private Set<IJob> activeJobs = new HashSet<>();
    private PriorityQueue<IJob> waitingJobs = new PriorityQueue<>();

    //arrays / priority, count, completion time
    private int[][] avgRunTime = new int[4][3];

    public Simulator(int numAmbulances) {
        this.numAmbulances = numAmbulances;
    }

    public int getNumAmbulances() {
        return numAmbulances;
    }

    @Override
    public void add(IJob j) {
        waitingJobs.add(j);
    }

    @Override
    public void tick() {

        activeJobs.forEach(IJob::tick);
        waitingJobs.forEach(IJob::tick);

        for (IJob job : activeJobs) {
            //counts the job as done on after the first tick
            if (job.isDone()){
                avgRunTime[job.getPriority()][0] = job.getPriority();
                avgRunTime[job.getPriority()][1] +=1;   //count
                avgRunTime[job.getPriority()][2] = job.getTimeSinceSubmit(tick);
                //activeAmbulances.remove(job); //throws ConcurrentModificationException
            }
        }
        activeJobs.removeIf(IJob::isDone);

        while(activeJobs.size() < numAmbulances && waitingJobs.size() > 0){
            IJob job = waitingJobs.poll();
            job.setSubmitTime(tick);
            activeJobs.add(job);
        }

        tick+=1;
    }

    @Override
    public int getTime() {
        return tick;
    }

    @Override
    public boolean allDone() {
        return activeJobs.isEmpty() && waitingJobs.isEmpty();
    }

    @Override
    public Set<Integer> getRunningJobs() {
        Set<Integer> activeAmbId = new HashSet<>();
        activeJobs.forEach((n) -> activeAmbId.add(n.getID()));
        return activeAmbId;
    }

    @Override
    public double getAverageJobCompletionTime(int priority) {
        return ((avgRunTime[priority][1]) / (avgRunTime[priority][2]));
    }

    public void printAverageTimes(int priority){
        double avgTime = getAverageJobCompletionTime(priority);
        switch (priority) {
            case 0:
                System.out.format("\nPriority 0 : %f", avgTime);
                break;
            case 1:
                System.out.format("\nPriority 1 : %f", avgTime);
                break;
            case 2:
                System.out.format("\nPriority 2 : %f", avgTime);
                break;
            case 3:
                System.out.format("\nPriority 3 : %f", avgTime);
                break;
                default:
                System.out.println("Invalid number");
        }


        //USED FOR DEBUGGING

        /*System.out.format("\npriority 0 count %d\n", avgRunTime[0][1]);
        System.out.format("priority 1 count %d\n", avgRunTime[1][1]);
        System.out.format("priority 2 count %d\n", avgRunTime[2][1]);
        System.out.format("priority 3 count %d\n", avgRunTime[3][1]);

        System.out.format("\npriority 0 time %d\n", avgRunTime[0][2]);
        System.out.format("priority 1 time %d\n", avgRunTime[1][2]);
        System.out.format("priority 2 time %d\n", avgRunTime[2][2]);
        System.out.format("priority 3 time %d\n", avgRunTime[3][2]);*/
    }
}

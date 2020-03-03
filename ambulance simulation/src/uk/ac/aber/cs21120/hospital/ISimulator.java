package uk.ac.aber.cs21120.hospital;

import java.util.Set;

/**
 * This interface specifies the simulator which queues the jobs and runs them
 * when ready (when a resource becomes free).
 *
 * @author Jim Finnis (jcf12@aber.ac.uk)
 *
 */
public interface ISimulator {

    /**
     * Add a job to the simulator but do not start it running. Instead, the job
     * should be waiting for resources.
     * @param j
     */
    public void add(IJob j);

    /**
     * Update the simulator: run update ticks on all the jobs which are running,
     * deal with jobs which have completed in those update ticks, and start as many jobs running
     * as resources will permit. Finally, increment the tick number.
     */
    public void tick();

    /**
     * return the current time in ticks, i.e. how many times tick() has been called.
     * @return
     */
    public int getTime();

    /**
     * Returns whether all jobs have been completed and none are waiting.
     * @return true if all jobs have been completed and none are waiting.
     */
    public boolean allDone();

    /**
     * Return the IDs of the running jobs, but not those which are waiting to run.
     * @return a Set of integers of running jobs.
     */
    public Set<Integer> getRunningJobs();

    /**
     * Return the average time from submission to the simulator to completion of all jobs so
     * far at a given priority level. Note: NOT the time between when the job starts running
     * and when it completes (which will just be the average job duration).
     *
     * @param priority	the priority level for which we wish find the average completion time.
     * @return 			the average completion time as a double precision float.
     */
    public double getAverageJobCompletionTime(int priority);
}

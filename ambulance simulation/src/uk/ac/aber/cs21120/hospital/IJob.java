package uk.ac.aber.cs21120.hospital;

/**
 * The interface for a job which must be scheduled by the Simulator.
 * A job has a priority, a duration, a unique ID and a time submitted.
 * When a job is added to the simulator it is "waiting" and does not
 * get ticked (updated) when the simulator runs. Once a resource is free
 * is can be added to the simulator's running jobs. The simulator will
 * tick running jobs, and once the job has been ticked "duration" times,
 * the job is "done" and the resource (the ambulance) can be freed for
 * other jobs.
 *
 * @author Jim Finnis (jcf12@aber.ac.uk)
 *
 */
public interface IJob {
    /**
     * Getter for ID
     * @return the job ID
     */
    public int getID();

    /**
     * Getter for priority
     * @return the priority of the job
     */
    public int getPriority();

    /**
     * Run an update tick on this job. Simply decrements the number of ticks remaining
     * in the job.
     */
    public void tick();

    /**
     * Check if a job has completed
     * @return true if job has completed
     */
    public boolean isDone();

    /**
     * Return the number of ticks since the job was added to the simulator. If called before
     * the job is submitted the result is undefined (a RuntimeException might also be thrown).
     * @param now		the current simulator tick number
     * @return			the difference between now and the time the job was submitted
     */
    public int getTimeSinceSubmit(int now);

    /**
     * Set the submit time - this will be called by the simulator
     * @param time		the time at which the job was added to the simulator
     */
    public void setSubmitTime(int time);

}

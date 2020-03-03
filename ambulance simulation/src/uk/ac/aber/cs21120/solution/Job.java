package uk.ac.aber.cs21120.solution;

import uk.ac.aber.cs21120.hospital.IJob;

public class Job implements IJob, Comparable<IJob> {
    private int id;
    private int priority;
    private int duration;
    private int submitTime;
    private int ticks = 0;


    public Job(int id, int priority, int duration) {
        this.id = id;
        this.priority = priority;
        this.duration = duration;
    }

    public int getID() {
        return this.id;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public void tick() {
        ticks+=1;
        duration--;
    }

    @Override
    public boolean isDone() {
        return duration <= 0;
    }

    @Override
    public int getTimeSinceSubmit(int now) throws RuntimeException {
        if (this.submitTime == now) throw new RuntimeException();
        return now - submitTime;
    }

    @Override
    public void setSubmitTime(int time) {
        this.submitTime = time;
    }

    @Override
    public int compareTo(IJob iJob) {
        return Integer.compare(this.priority, iJob.getPriority());
    }

    //Mainly used for debugging
    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", priority=" + priority +
                ", duration=" + duration +
                ", submitTime=" + submitTime +
                ", ticks=" + ticks +
                '}';
    }
}

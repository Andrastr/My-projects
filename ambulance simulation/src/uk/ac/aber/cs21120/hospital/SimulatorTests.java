package uk.ac.aber.cs21120.hospital;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import uk.ac.aber.cs21120.solution.Job;
import uk.ac.aber.cs21120.solution.Simulator;

/**
 * This class contains some basic JUnit tests for the Simulator class,
 * and those functions of Job which require the Simulator.
 *
 * Please extend to add more tests as part of your testing strategy.
 *
 * @author Jim Finnis (jcf12@aber.ac.uk)
 *
 */
public class SimulatorTests {

    /**
     * This tests that the tick() timekeeping works - i.e. that getTime() returns
     * the number of times the simulator has been ticked.
     */
    @Test
    public void testSimulatorTime() {
        Simulator s = new Simulator(4); // number of ambulances doesn't matter
        // run 10 ticks
        for(int i=0;i<10;i++)
            s.tick();
        // and check that the current time is 10 ticks.
        Assertions.assertEquals(10,s.getTime());
    }

    /**
     * Ensure that we can add a job, and that getTimeSinceSubmit() works.
     */
    @Test
    public void testJobAdd() {
        Simulator s = new Simulator(4);
        // run 10 ticks
        for(int i=0;i<10;i++)
            s.tick();

        // add a job
        Job j = new Job(1,2,3);
        s.add(j);

        // run 20 more ticks
        for(int i=0;i<20;i++)
            s.tick();

        // ensure that the job knows it's been submitted for 20 ticks
        Assertions.assertEquals(20,j.getTimeSinceSubmit(s.getTime()));
    }

    /**
     * Ensure that we can add multiple jobs and get the correct list of IDs
     * from getRunningJobs()
     */

    @Test
    public void testGetRunningJobs() {
        Simulator s = new Simulator(100); // huge number of ambulances so all jobs can run!
        // create 10 jobs with IDs 0-9. We don't care about priority and duration.
        for(int i=0;i<10;i++) {
            Job j = new Job(i,1,2);
            // add each job!
            s.add(j);
        }
        // run for 1 tick - this should move all jobs into the running set.
        s.tick();
        // get the set of running job IDs
        Set<Integer> set = s.getRunningJobs();
        // ensure that the set consists of 10 members
        Assertions.assertEquals(10,set.size());
        // and that all integers from 0 to 9 are in it.
        for(int i=0;i<10;i++) {
            Assertions.assertTrue(set.contains(i));
        }
    }
}

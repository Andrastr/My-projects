package uk.ac.aber.cs21120.hospital;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import uk.ac.aber.cs21120.solution.Job;

/**
 * This class contains tests for the Job class, which you should have written to
 * implement the IJob interface. Tests here should not require a simulator.
 *
 * Please extend to add more tests as part of your testing strategy.
 *
 * @author Jim Finnis (jcf12@aber.ac.uk)
 *
 */
public class JobTests {
    /**
     * Test job comparison, which should compare two jobs by priority.
     */
    @Test
    public void testPriorities() {
        // j0 has priority 0
        Job j0 = new Job(0,0,10);
        // j1 has priority 1
        Job j1 = new Job(1,1,10);
        // perform comparisons
        Assertions.assertTrue(j0.compareTo(j1)<0);
        Assertions.assertTrue(j1.compareTo(j0)>0);
        Assertions.assertEquals(0,j0.compareTo(j0));
    }

    /**
     * Test property getters. Note that there is no getter for Duration.
     */
    @Test
    public void testGetters() {
        // create a job with ID 1, priority 2, duration 3.
        Job j = new Job(1,2,3);
        Assertions.assertEquals(1,j.getID());
        Assertions.assertEquals(2,j.getPriority());
    }

    /**
     * Test that calling getTimeSinceSubmit() before the job is added to the simulator will
     * fail.
     */

    @Test
    public void testGetTimeSinceSubmitWithoutAdding() {
        boolean thrown=false;
        try {
            Job j = new Job(1,2,3);
            int t = j.getTimeSinceSubmit(0);
            System.out.println(t); // just here to stop the compiler complaining about t being unused.
            Assertions.fail("getTimeSinceSubmit was called without adding the job, and did not throw.");
        } catch(RuntimeException e) {
            thrown = true;
        }
        // thrown should be true.
        Assertions.assertTrue(thrown);
    }

    /**
     * Test that getTimeSinceSubmit() after a job is added returns the correct number of ticks.
     * Because we do not have direct access to the simulator (you may be using this test before the
     * simulator has been written) we don't actually add it, but we call setSubmitTime() directly as
     * the simulator's add() method must do.
     */
    @Test
    public void testGetTimeSinceSubmitWithAdding() {
        Job j = new Job(1,2,3);
        j.setSubmitTime(20); // pretend the job was added at tick 20.
        // make sure that at time 30, the time since submit is 10 ticks.
        Assertions.assertEquals(10,j.getTimeSinceSubmit(30));
    }

}

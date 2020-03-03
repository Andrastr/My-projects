package uk.ac.aber.cs21120.hospital;

import java.util.Random;

/**
 * Utility class for generating random priorities from 0-3, in which 0 (the most important)
 * is very rare, 1 is fairly rare, 2 is less rare and 3 is common.
 *
 * @author Jim Finnis (jcf12@aber.ac.uk)
 *
 */
public class RandomPriorityGenerator {
    /**
     * The random number generator used by this class
     */
    Random rnd = new Random();

    /**
     * Return a new random priority between 0 and 3.
     * @return random priority 0-3
     */
    public int next() {
        int r = rnd.nextInt(20);
        int pri; // pri is the priority
        if(r==0)pri=0;
        else if(r>=1 && r<5)pri=1;
        else if(r>=5 && r<10)pri=2;
        else pri=3;
        return pri;
    }

    /**
     * A list of all possible priorities
     */
    private static int[] possiblePriorities = {0,1,2,3};

    /**
     * returns the array of all possible priorities
     */
    public static int[] getPossiblePriorities() {
        return possiblePriorities;
    }
}

package uk.ac.aber.cs21120.hospital;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is a useful debugging class to show the progress of jobs through the simulator.
 * To use it, create an instance of the class. Then run the simulator for a number of 
 * ticks (with some jobs added). After each simulator tick, call add() on your JobDisplay
 * object, passing in the tick index number and the set of job IDs returned from getRunningJobs()
 * in the simulator. At the end of the run, call show() on the JobDisplay object - you will get
 * a graph of ticks against JobIDs, with an X marking when that job was running.
 * The last line will show the number of running jobs in each tick.
 * 
 * 
 * Usage example:
 * <pre>
 * MySimulator sim = new MySimulator();
 * JobDisplay jd = new JobDisplay();
 * .. // add some jobs here
 * for(int tick=0;tick<10000;tick++){
 *     sim.tick(i);
 *     jd.add(i,sim.getRunningJobs());
 * }
 * // and show the results
 * jd.show();
 * </pre>
 * 
 * Here's an example output:
 * <pre>
 *  0 -XXXXXXXXXXXXX--------
 *  1 -XXXX-----------------
 *  2 -XXXXXXXXXXXXXXXXX----
 *  3 -XXXXXXXXXXXXXXXXXXXX-
 *  4 -----XXXXX------------
 *  5 ----------XXXXX-------
 *    0444444444444432221110
 * </pre>
 * NOTE THAT the bottom line (job count) will not work correctly if the number of 
 * running jobs can be double digits (more than 9). 
 * 
 * @author Jim Finnis (jcf12@aber.ac.uk)
 *
 */
public class JobDisplay {
	/**
	 * The map used to store which jobs are running at which times. As such, it is
	 * a map from integers (tick numbers) to sets of integers (IDs of running jobs)
	 */
	private Map<Integer,Set<Integer>> map = new HashMap<Integer,Set<Integer>>();
	
	/**
	 * The maximum job ID found during add()
	 */
	private int maxid=0;
	/**
	 * The maximum tick number found during add()
	 */
	private int maxtick=0;

	/**
	 * Add the current simulator state to the system.
	 */
	public void add(ISimulator sim) {
		Set<Integer> set = sim.getRunningJobs();
		// the simulator will have incremented its time internally before doing 
		// the next tick, so we need to decrement.
		int time = sim.getTime()-1;
		map.put(time, set);
		maxtick = Math.max(time,maxtick);
		int setmax;
		if(set.size()>0)
			setmax = Collections.max(sim.getRunningJobs());
		else
			setmax = 0;
		maxid = Math.max(maxid,setmax);
	}
	
	/**
	 * At the end of a run, display the collected data on the console.
	 * See the documentation for the class for what this looks like, and note
	 * again that the last line will not handle running job counts greater than 9.
	 */
	public void show() {
		for(int i=0;i<=maxid;i++) {
			System.out.println(String.format("%2d %s", i,getRow(i)));
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<maxtick;i++) {
			Set<Integer> jobsInThisTick = map.get(i);
			if(jobsInThisTick!=null)
				sb.append(jobsInThisTick.size());
			else
				sb.append(' ');

		}
		System.out.println("   "+sb.toString());
		
	}
	
	/**
	 * Method used to construct a row of the plot used in show()
	 * @param id	a job ID
	 * @return		a string showing the ticks within which the jobs was running
	 */
	private String getRow(int id) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<maxtick;i++) {
			Set<Integer> jobsInThisTick = map.get(i);
			if(jobsInThisTick!=null && jobsInThisTick.contains(id)) {
				sb.append("X");
			} else  {
				sb.append("-");
			}
		}
		return sb.toString();
	}
}

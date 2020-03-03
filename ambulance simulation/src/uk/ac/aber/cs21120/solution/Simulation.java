package uk.ac.aber.cs21120.solution;

import uk.ac.aber.cs21120.hospital.RandomPriorityGenerator;

import java.util.Random;

public class Simulation {
    public static void task3(int ambulances){
        int jobID = 0;

        RandomPriorityGenerator rpg = new RandomPriorityGenerator();
        Simulator s = new Simulator(ambulances);
        Random rnd = new Random();

        for (int i = 0; i < 10000; i++){
            s.tick();
            int myRandom = 0;
            myRandom = rnd.nextInt(3);
            if (myRandom == 2){
                myRandom = rnd.nextInt(11);
                int no = rpg.next();
                //System.out.format("\npriority:%d\n", no);
                Job j = new Job(jobID++, no, (myRandom + 10));
                //System.out.format("duration: %d\n", myRandom+10);
                //System.out.println(j.toString());
                s.add(j);
            }
        }
        while(!s.allDone()){
            s.tick();
        }
        System.out.format("\n\nAmbulances: %d", s.getNumAmbulances());
        s.printAverageTimes(0);
        s.printAverageTimes(1);
        s.printAverageTimes(2);
        s.printAverageTimes(3);
        //s.printAverageTimes();
    }

    public static void task4(){
        for (int i = 4; i < 21; i++){
            task3(i);
        }
    }

    public static void main(String[] args){
        //Simulation.task3(4);
        Simulation.task4();
    }

}

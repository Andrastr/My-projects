package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zone {

    private String name;
    private boolean isOutside;
    private double price;
    private ArrayList<Zone> zones = new ArrayList<>();

    //To make it easier to save/load, the program only has a few of each parking space, teh real garage would have ten times the space
    private boolean[] z1Space = new boolean[10];
    private boolean[] z2Space = new boolean[5];
    private boolean[] z3Space = new boolean[2];
    private boolean[] z4Space = new boolean[20];
    private boolean[] z5Space = new boolean[8];

    Zone(){}

    private Zone(String name, boolean isOutside, double price){
        this.name = name;
        this.isOutside = isOutside;
        this.price = price;
    }

    private String getName(){
        return name;
    }

    private double getPrice(){
        return price;
    }

    void setPrice(double price){
        this.price = price;
    }

    List<Zone> getZones(){
        return zones;
    }

    String printZones(){
        return zones.toString();
    }

    boolean[] getZ1(){
        return z1Space;
    }

    boolean[] getZ2() {
        return z2Space;
    }

    boolean[] getZ3(){
        return z3Space;
    }

    boolean[] getZ4() {
        return z4Space;
    }

    boolean[] getZ5() {
        return z5Space;
    }

    //loads from file
    void load() throws IOException {
        FileReader fileInput = new FileReader("price.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        while(infile.hasNext()) {
            String n = infile.next();
            boolean o = infile.nextBoolean();
            double p = infile.nextDouble();

            Zone z = new Zone(n, o, p);
            zones.add(z);
        }
    }

        //saves to file
    void save() throws IOException {
        FileWriter fileOutput = new FileWriter("price.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);
        for (Zone z:zones) {
            outfile.println(z.getName() + ":" + z.isOutside + ":" + z.getPrice());
        }
        outfile.close();
    }

    @Override
    public String toString() {
        if(!isOutside){
            return "\n" + name + " has a hourly rate of: " + price + " Units and is inside";
        } else{
            return "\n" + name + " has a hourly rate of: " + price + " Units and is outside";
        }
    }
}

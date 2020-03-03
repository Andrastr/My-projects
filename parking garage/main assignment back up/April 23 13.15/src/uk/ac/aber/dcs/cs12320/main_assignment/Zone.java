package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zone {

    private String name;
    private boolean isOutside;
    private double price;
    private ArrayList<Zone> zones = new ArrayList<>();

        //To make it easier to save/load, the program only has a few of each parking space, the real garage would have ten times the space
    boolean[] z1Space = new boolean[10];
    boolean[] z2Space = new boolean[5];
    boolean[] z3Space = new boolean[3];
    boolean[] z4Space = new boolean[2];
    boolean[] z5Space = new boolean[8];

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

    void printZones(){
        for(Zone z:zones){
            System.out.println(z.toString());
        }
    }

        //prints the five arrays, primarily used in order to save the arrays
    String printZoneBoolean(boolean[] array){
        int i = 0;
        StringBuilder gh = new StringBuilder();
        while(i < (array.length - 1)){
            if (array[i]){
                gh.append("true:");
            }else{
                gh.append("false:");
            }
            i++;
        }
        if (array[array.length-1]){
            gh.append("true\n");
        } else{
            gh.append("false\n");
        }
        return gh.toString();
    }

        //method for loading the 5 zone space arrays
    private void loadZoneBoolean(boolean[] array) throws IOException{
        FileReader fileInput = new FileReader("zone.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        int i;
        for (i = 0; i < array.length; i++){
            array[i] = infile.nextBoolean();
        }
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

    void loadZone()throws IOException{
        FileReader fileInput = new FileReader("zone.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        loadZoneBoolean(z1Space);
        loadZoneBoolean(z2Space);
        loadZoneBoolean(z3Space);
        loadZoneBoolean(z4Space);
        loadZoneBoolean(z5Space);
    }

    void saveZone() throws IOException {
        FileWriter fileOutput = new FileWriter("zone.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);

        outfile.println(printZoneBoolean(z1Space) + printZoneBoolean(z2Space) + printZoneBoolean(z3Space) + printZoneBoolean(z4Space) + printZoneBoolean(z5Space));
        outfile.close();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" has an hourly rate of: ");
        sb.append(price);
        if(!isOutside){
            sb.append(" Units, and is inside");
        } else{
            sb.append(" Units, and is outside");
        }
        return sb.toString();
    }
}

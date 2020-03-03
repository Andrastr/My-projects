package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Zone {

    private String prices;
    private String name;
    private boolean isOutside;
    private double price;
    public ArrayList<Zone> zones = new ArrayList<>();

    public Zone(){}

    public Zone(String name, boolean isOutside) {
        this.name = name;
        this.isOutside = isOutside;
    }

    public void addZone(Zone name){
        zones.add(name);
    }

    public String getName(){
        return name;
    }

    public boolean getIsOutside() {
        return isOutside;
    }

    public void isOutside(boolean outside) {
        isOutside = outside;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    //loads from file
    public void load(String prices) throws IOException {
        FileReader fileInput = new FileReader(prices);
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        while(infile.hasNext()){

        }
    }

    //saves to file
    public void save(String prices) throws IOException {
        FileWriter fileOutput = new FileWriter(prices);
        PrintWriter outfile = new PrintWriter(fileOutput);

        for (Zone z:zones) {
            outfile.println(z.getName() + ":" + z.getIsOutside() + ":" + z.getPrice());
        }
        outfile.close();
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        return name + " has a hourly rate of: " + price + " Units";
    }
}

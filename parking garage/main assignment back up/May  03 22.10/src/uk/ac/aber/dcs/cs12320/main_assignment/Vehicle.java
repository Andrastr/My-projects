package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Vehicle extends Customer {
    private int type;
    private int zone;
    private int space;
    private ArrayList<Vehicle> vehicles = new ArrayList<>(); //ArrayList keeping track of all parked vehicles


    Vehicle(){

    }

    private Vehicle(int type, int zone, int space) {
        this.type = type;
        this.zone = zone;
        this.space = space;
    }

    int getType() {
        return type;
    }

    void setType(int type) {
        this.type = type;
    }

    int getZone() {
        return zone;
    }

    void setZone(int zone) {
        this.zone = zone;
    }

    int getSpace() {
        return space;
    }

    void setSpace(int space) {
        this.space = space;
    }

    void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    void removeVehicle(Vehicle vehicle){
        vehicles.remove(vehicle);
    }

    /**
     * Looks through ArrayList vehicles for required customer
     * If the vehicle is not found, it will return null
     *
     * @param number number on receipt recieved earlier
     * @return correct vehicle, or if not found, null
     */
    Vehicle searchForVehicle(int number) {
        Vehicle result = null;
        for(Vehicle v: vehicles){
            if(v.getReceiptNum() == number){
                result = v;
                break;
            }
        }
        return result;
    }

        //Prints ArrayList "vehicles", primarily used for debugging
    void printVehicles(){
        for (Vehicle v:vehicles){
            System.out.println(v.toString());
        }
    }

    /**
     * Loads from file "vehicles.txt"
     *
     * @throws IOException
     */
    void load() throws IOException {
        FileReader fileInput = new FileReader("vehicle.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        while(infile.hasNext()) {
            int t = infile.nextInt();
            int i  = infile.nextInt();
            int r = infile.nextInt();
            String l = infile.next();
            int z = infile.nextInt();
            int s = infile.nextInt();
            long p = infile.nextLong();

            Vehicle v = new Vehicle(t,z,s);
            v.setOwnerId(i);
            v.setReceiptNum(r);
            v.setLicensePlate(l);
            v.setStartParked(p);
            vehicles.add(v);
        }
    }

    /**
     * Saves to file "vehicles.txt"
     *
     * @throws IOException
     */
    void save() throws IOException {
        FileWriter fileOutput = new FileWriter("vehicle.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);
        for (Vehicle v:vehicles) {
            outfile.println(v.getType() + ":" + v.getOwnerId() + ":" + v.getReceiptNum() + ":" + v.getLicensePlate() + ":" + v.getZone() + ":" + v.getSpace() + ":" + v.getStartParked());
        }
        outfile.close();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String typeString;

        if(type == 1){
            typeString = "standard";
        } else if ( type == 2){
            typeString = "high vehicle";
        } else if ( type == 3){
            typeString = "long vehicle";
        } else if (type == 4){
            typeString = "coach";
        } else if (type == 5){
            typeString = "motorbike";
        } else{
            typeString = "ERROR: INVALID TYPE";
        }

        sb.append("Owner ID: ");
        sb.append(this.getOwnerId());
        sb.append(", Type: ");
        sb.append(typeString);
        sb.append(", License plate: ");
        sb.append(this.getLicensePlate());
        sb.append(", parked in zone ");
        sb.append(zone);
        sb.append(" space ");
        sb.append(space);
        sb.append(", Receipt code: ");
        sb.append(this.getReceiptNum());
        sb.append(", parked ");
        sb.append(parkedString(1));
        return sb.toString();
    }

}

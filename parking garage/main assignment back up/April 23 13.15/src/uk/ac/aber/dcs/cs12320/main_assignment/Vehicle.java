package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Vehicle {

    private int type;
    private int ownerId;
    private int receiptNum;
    private int zone;
    private int space;
    private long startTime = System.currentTimeMillis();
    private String licensePlate;
    private ArrayList<Vehicle> vehicles = new ArrayList<>(); //ArrayList keeping track of all parked vehicles


    Vehicle(){

    }

    Vehicle(int owner, int receiptNum) {
        this.ownerId = owner;
        this.receiptNum = receiptNum;
    }

    private Vehicle(int type, int ownerId, int receiptNum, String licensePlate, int zone, int space) {
        this.type = type;
        this.ownerId = ownerId;
        this.receiptNum = receiptNum;
        this.licensePlate = licensePlate;
        this.zone = zone;
        this.space = space;
    }

    int getType() {
        return type;
    }

    void setType(int type) {
        this.type = type;
    }

    int getReceiptNum(){
        return receiptNum;
    }

    void setReceiptNum(int receiptNum){
        this.receiptNum = receiptNum;
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

    private String getLicensePlate() {
        return licensePlate;
    }

    void setLicense(String licensePlate){
        this.licensePlate = licensePlate;
    }

    private int getOwner(){
        return ownerId;
    }

    void setOwner(int owner){
        this.ownerId = owner;
    }

    long getStartTime(){
        return startTime;
    }

    void setStartTime(long startTime){
        this.startTime = startTime;
    }

    void printVehicles(){
        for(Vehicle v:vehicles){
            System.out.println(v.toString());
        }
    }

    void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    void removeVehicle(Vehicle vehicle){
        vehicles.remove(vehicle);
    }

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

    //loads from file
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

            Vehicle v = new Vehicle(t, i, r, l, z, s);
            vehicles.add(v);
        }
    }

    //saves to file
    void save() throws IOException {
        FileWriter fileOutput = new FileWriter("vehicle.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);
        for (Vehicle v:vehicles) {
            outfile.println(v.getType() + ":" + v.getOwner() + ":" + v.getReceiptNum() + ":" + v.getLicensePlate() + ":" + v.getZone() + ":" + v.getSpace());
        }
        outfile.close();
    }

    @Override
    public boolean equals(Object obj) {
        Vehicle o = (Vehicle) obj;
        if (this.getReceiptNum() == o.getReceiptNum() ){
            return true;
        } else{
            return false;
        }
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
        sb.append(ownerId);
        sb.append(", Type: ");
        sb.append(typeString);
        sb.append(", License plate: ");
        sb.append(licensePlate);
        sb.append(", parked in zone ");
        sb.append(zone);
        sb.append(" space ");
        sb.append(space);
        sb.append(", Receipt code: ");
        sb.append(receiptNum);
        return sb.toString();
    }

}

package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Vehicle {

    private int type;
    private int ownerId;
    private int receiptNum;
    private int zone;
    private int space;
    private long startParked;
    private String licensePlate;
    private ArrayList<Vehicle> vehicles = new ArrayList<>(); //ArrayList keeping track of all parked vehicles


    Vehicle(){

    }

    Vehicle(int owner, int receiptNum) {
        this.ownerId = owner;
        this.receiptNum = receiptNum;
    }

    private Vehicle(int type, int ownerId, int receiptNum, String licensePlate, int zone, int space, long startParked) {
        this.type = type;
        this.ownerId = ownerId;
        this.receiptNum = receiptNum;
        this.licensePlate = licensePlate;
        this.zone = zone;
        this.space = space;
        this.startParked = startParked;
    }

    int getType() {
        return type;
    }

    void setType(int type) {
        this.type = type;
    }

    private int getReceiptNum(){
        return receiptNum;
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

    long getStartParked(){
        return startParked;
    }

    void setStartParked(long startTime){
        this.startParked = startTime;
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

    String parkedString(int num){

        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(startParked);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy | HH:mm:ss");
        SimpleDateFormat parked = new SimpleDateFormat("dd MMM yyyy | HH:MM");
        if(num == 1){
            return format.format(cal.getTime());
        } else if(num == 2){
            return parked.format(cal.getTime());
        } else{
            return "---ERROR---\nINVALID DATE FORMAT";
        }
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
            long p = infile.nextLong();

            Vehicle v = new Vehicle(t,i,r,l,z,s,p);
            vehicles.add(v);
        }
    }

    //saves to file
    void save() throws IOException {
        FileWriter fileOutput = new FileWriter("vehicle.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);
        for (Vehicle v:vehicles) {
            outfile.println(v.getType() + ":" + v.getOwner() + ":" + v.getReceiptNum() + ":" + v.getLicensePlate() + ":" + v.getZone() + ":" + v.getSpace() + ":" + v.getStartParked());
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
        sb.append(", parked ");
        sb.append(parkedString(1));
        return sb.toString();
    }

}

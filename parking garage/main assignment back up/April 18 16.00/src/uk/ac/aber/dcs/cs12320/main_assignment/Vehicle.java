package uk.ac.aber.dcs.cs12320.main_assignment;

import java.util.ArrayList;
import java.util.Objects;

public class Vehicle {

    private int type;
    private int ownerId;
    private int receiptNum;
    private int zone;
    private int space;
    private String licensePlate;
    public ArrayList<Vehicle> vehicles = new ArrayList<>();             //ArrayList keeping track of all parked vehicles


    public Vehicle(){

    }

    public Vehicle(int owner) {
        this.ownerId = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReceiptNum(){
        return receiptNum;
    }

    public void setReceiptNum(int receiptNum){
        this.receiptNum = receiptNum;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicense(String licensePlate){
        this.licensePlate = licensePlate;
    }

    public void setOwner(int owner){
        this.ownerId = owner;
    }

    public String printVehicles(){
        return vehicles.toString();
    }

    public void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle){
        vehicles.remove(vehicle);
    }

    public Vehicle searchForVehicle(int number) {
        Vehicle result = null;
        for(Vehicle v: vehicles){
            if(v.getReceiptNum() == number){
                result = v;
                break;
            }
        }
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        Vehicle o = (Vehicle) obj;
        if (this.getLicensePlate() == o.getLicensePlate() ){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String toString(){
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

        return "\nOwner ID: " + ownerId + ", Type: " + typeString + ", License plate: " + licensePlate;
    }

}

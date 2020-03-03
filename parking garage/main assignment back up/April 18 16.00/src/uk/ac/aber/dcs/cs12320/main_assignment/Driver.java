package uk.ac.aber.dcs.cs12320.main_assignment;

public abstract class Driver {
    Vehicle vehicle;
    Zone zone;
    int token;
    String name;
    int receiptNum;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setReceiptNum(int receiptNum){
        this.receiptNum = receiptNum;
    }
}

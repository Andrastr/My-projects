package uk.ac.aber.dcs.cs12320.main_assignment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Customer {
    private int ownerId;
    private int receiptNum;
    private long startParked;
    private String licensePlate;
    private boolean disabled;



    Customer(){}

    Customer(int id){
        this.ownerId = id;
    }

    Customer(int num, String licensePlate, int receiptNum, long startParked, boolean disabled){
        this.ownerId = num;
        this.licensePlate = licensePlate;
        this.receiptNum =  receiptNum;
        this.startParked = startParked;
        this.disabled = disabled;
    }

    int getOwnerId(){
        return ownerId;
    }

    void setOwnerId(int id){
        this.ownerId = id;
    }

    int getReceiptNum(){
        return receiptNum;
    }

    void setReceiptNum(int num){
        this.receiptNum = num;
    }

    long getStartParked(){
        return startParked;
    }

    void setStartParked(long startTime){
        this.startParked = startTime;
    }

    String getLicensePlate() {
        return licensePlate;
    }

    void setLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
    }

    boolean getDisabled(){
        return disabled;
    }

    void isDisabled(boolean disabled){
        this.disabled = disabled;
    }

    /**
     * Method returning a date and/or time in certain formats
     *
     * @param num The format for the date
     * @return formatted date
     */
    String parkedString(int num){
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(startParked);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy | HH:mm:ss");
        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");

        if(num == 1){
            return date.format(cal.getTime());
        } else if(num == 2){
            return time.format(cal.getTime());
        } else{
            return format.format(cal.getTime());
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Owner ID: ");
        sb.append(ownerId);
        sb.append(", Registered license plate: ");
        sb.append(licensePlate);
        sb.append(", Retrieval code: ");
        sb.append(receiptNum);
        sb.append("Parked: ");
        sb.append(parkedString(3));
        if(disabled){
            sb.append(", and is a disabled registered vehicle");
        } else{
            sb.append(", and is not a disabled registered vehicle");
        }

        return sb.toString();
    }
}

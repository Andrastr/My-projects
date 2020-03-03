package uk.ac.aber.dcs.cs12320.main_assignment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Customer {
    private int ownerId;
    private int receiptNum;
    private long startParked;
    private String licensePlate;
    private boolean isDisabled;


    /**
     * Constructor for class Customer
     */
    Customer(){}

    /**
     * Another constructor for class Customer
     *
     * @param id The customer id number
     */
    Customer(int id){
        this.ownerId = id;
    }

    /**
     * A third constructor for the Customer class
     *
     * @param num The customer id number
     * @param licensePlate The registered license plate of the vehicle
     * @param receiptNum The receipt number used to retrieve the vehicle
     * @param startParked The time when the customer parked
     * @param disabled Whether the customer is disabled (true) or not (false)
     */
    Customer(int num, String licensePlate, int receiptNum, long startParked, boolean disabled){
        this.ownerId = num;
        this.licensePlate = licensePlate;
        this.receiptNum =  receiptNum;
        this.startParked = startParked;
        this.isDisabled = disabled;
    }

    /**
     * @return Owner id number of customer
     */
    int getOwnerId(){ return ownerId; }

    /**
     * @param id Owner id number of customer
     */
    void setOwnerId(int id){ this.ownerId = id; }

    /**
     * @return Customers retrival code
     */
    int getReceiptNum(){ return receiptNum; }

    /**
     * @param num Customers retrieval code
     */
    void setReceiptNum(int num){ this.receiptNum = num; }

    /**
     * @return When the customer parked
     */
    long getStartParked(){ return startParked; }

    /**
     * @param startTime When the customer parked
     */
    void setStartParked(long startTime){ this.startParked = startTime; }

    /**
     * @return License plate of the customer
     */
    String getLicensePlate() { return licensePlate; }

    /**
     * @param licensePlate License plate of the customer
     */
    void setLicensePlate(String licensePlate){ this.licensePlate = licensePlate; }

    /**
     * @return If the customer or a passenger is disabled (true) or not) false)
     */
    boolean getDisabled(){ return isDisabled; }

    /**
     * @param disabled If the customer or a passenger is disabled (true) or not) false)
     */
    void isDisabled(boolean disabled){ this.isDisabled = disabled; }

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
        if(isDisabled){
            sb.append(", and is a disabled registered vehicle");
        } else{
            sb.append(", and is not a disabled registered vehicle");
        }

        return sb.toString();
    }
}

package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Customer {
    private int ownerId;
    private int receiptNum;
    private long startParked;
    private String licensePlate;
    private boolean disabled;
    private int token;
    private ArrayList<Customer> customers = new ArrayList<>();  //ArrayList keeping track of all current customers

    Customer(){}

    Customer(int id){
        this.ownerId = id;
    }

    private Customer(int num, String licensePlate, int receiptNum, long startParked, boolean disabled){
        this.ownerId = num;
        this.licensePlate = licensePlate;
        this.receiptNum =  receiptNum;
        this.startParked = startParked;
        this.disabled = disabled;
    }

    boolean getDisabled(){
        return disabled;
    }

    void isDisabled(boolean disabled){
        this.disabled = disabled;
    }

    void setToken(int token) {
        this.token = token;
    }int getReceiptNum(){
        return receiptNum;
    }

    void setReceiptNum(int num){
        this.receiptNum = num;
    }

    String getLicensePlate() {
        return licensePlate;
    }

    void setLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
    }

    int getOwnerId(){
        return ownerId;
    }

    void setOwnerId(int id){
        this.ownerId = id;
    }

    long getStartParked(){
        return startParked;
    }

    void setStartParked(long startTime){
        this.startParked = startTime;
    }

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

    int generateOwnerId(){
        int i = 1;

        for (Customer c:customers){
            if (i != c.getOwnerId()){
                ownerId = i;
            } else{
                i++;
            }
        }
        return i;
    }

    void printReceipt(Customer tempC, Vehicle tempV){
        String format = "|\t%1$-30s|\n";

        System.out.println("\n\n\n+ ------------------------------- +");
        System.out.format(format, "User receipt");
        System.out.format(format, "Please keep this receipt");
        System.out.format(format, "Date         " + tempC.parkedString(1));
        System.out.format(format, "Time               " + tempC.parkedString(2));
        System.out.println("+ ------------------------------- +");
        System.out.format(format, "-User ID       |   " + tempC.getOwnerId());
        System.out.format(format, "-License plate     |   " + tempC.getLicensePlate());
        System.out.format(format, "-Disabled          |   " + tempC.getDisabled());
        System.out.format(format, "-Zone ID           |   " + tempV.getZone());
        System.out.format(format, "-Space ID          |   " + tempV.getSpace());
        System.out.println("+ ------------------------------- +");
        System.out.format(format, "RETRIEVAL CODE     |   " + tempC.getReceiptNum());
        System.out.println("+ ------------------------------- +");
    }

    void addCustomer(Customer newUser){
        customers.add(newUser);
    }

    void removeCustomer(Customer User){
        customers.remove(User);
    }

    Customer searchForCustomer(int receiptNum){
        Customer result = null;
        for(Customer c: customers){
            if(c.getReceiptNum() == receiptNum){
                result = c;
                break;
            }
        }
        return result;
    }

        //Used for debugging
    void printCustomers(){
        for (Customer c:customers){
            System.out.println(c.toString());
        }
    }

    //loads from file
    void load() throws IOException {
        FileReader fileInput = new FileReader("customer.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        while(infile.hasNext()) {
            int i = infile.nextInt();
            String l = infile.next();
            int r = infile.nextInt();
            long p = infile.nextLong();
            boolean d = infile.nextBoolean();

            Customer c = new Customer(i,l,r,p,d);
            customers.add(c);
        }
    }

    //saves to file
    void save() throws IOException {
        FileWriter fileOutput = new FileWriter("customer.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);
        for (Customer c:customers) {
            outfile.println( c.getOwnerId() + ":" + c.getLicensePlate() + ":" + c.getReceiptNum() + ":" + c.getStartParked() + ":" + c.getDisabled());
        }
        outfile.close();
    }

    @Override
    public boolean equals(Object obj) {
        Customer o = (Customer) obj;
        if (this.getReceiptNum() == o.getReceiptNum() ){
            return true;
        } else{
            return false;
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

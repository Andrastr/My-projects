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
    private int customerNum;    //Starts counting from
    private String licensePlate;
    private int token;
    private int receiptNum;
    private long startParked;
    private boolean disabled;
    private ArrayList<Customer> customers = new ArrayList<>();  //ArrayList keeping track of all current customers

    Customer(){

    }

    Customer(int num) {
        this.customerNum = num;
    }

    private Customer(int num, String licensePlate, int receiptNum, long startParked, boolean disabled){
        this.customerNum = num;
        this.licensePlate = licensePlate;
        this.receiptNum =  receiptNum;
        this.startParked = startParked;
        this.disabled = disabled;
    }

    int generateCustomerNumber(){
        int i = 1;

        for (Customer c:customers){
            if (i != c.getCustomerNum()){
                customerNum = i;
            } else{
                i++;
            }
        }
        return i;
    }

    private String getLicensePlate(){
        return licensePlate;
    }

    void setLicensePlate(String license){
        this.licensePlate = license;
    }

    int getCustomerNum() {
        return customerNum;
    }

    long getStartParked(){
        return startParked;
    }

    void setStartParked(long startParked){
        this.startParked = startParked;
    }

    boolean getDisabled(){
        return disabled;
    }

    void isDisabled(boolean disabled){
        this.disabled = disabled;
    }

    private int getReceiptNum(){
        return receiptNum;
    }

    void setReceiptNum(int receiptNum){
        this.receiptNum = receiptNum;
    }

    void setToken(int token) {
        this.token = token;
    }

    void printReceipt(Customer tempC, Vehicle tempV){
        String format = "|\t%1$-30s|\n";

        System.out.println("\n\n\n+ ------------------------------- +");
        System.out.format(format, "Customer receipt");
        System.out.format(format, "Please keep this receipt");
        System.out.format(format, "Date         " + tempC.parkedString(1));
        System.out.format(format, "Time               " + tempC.parkedString(2));
        System.out.println("+ ------------------------------- +");
        System.out.format(format, "-Customer ID       |   " + tempC.getCustomerNum());
        System.out.format(format, "-License plate     |   " + tempC.getLicensePlate());
        System.out.format(format, "-Disabled          |   " + tempC.getDisabled());
        System.out.format(format, "-Zone ID           |   " + tempV.getZone());
        System.out.format(format, "-Space ID          |   " + tempV.getSpace());
        System.out.println("+ ------------------------------- +");
        System.out.format(format, "RETRIEVAL CODE     |   " + tempC.getReceiptNum());
        System.out.println("+ ------------------------------- +");
    }

    void addCustomer(Customer newCustomer){
        customers.add(newCustomer);
    }

    void removeCustomer(Customer customer){
        customers.remove(customer);
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

    private String parkedString(int num){

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
            outfile.println( c.getCustomerNum() + ":" + c.getLicensePlate() + ":" + c.getReceiptNum() + ":" + c.getStartParked() + ":" + c.getDisabled());
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
        return"ID: " + customerNum + ", Registered license plate: " + licensePlate + ", Retrieval code: " + receiptNum + ", Parked: " + parkedString(3);
    }
}

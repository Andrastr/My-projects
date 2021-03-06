package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Customer extends Driver {
    private int customerNum;    //Starts counting from
    private String licensePlate;
    private long startParked;
    private ArrayList<Customer> customers = new ArrayList<>();  //ArrayList keeping track of all current customers

    Customer(){

    }

    Customer(int num) {
        this.customerNum = num;
    }

    private Customer(int num, String licensePlate, int receiptNum, long startParked){
        this.customerNum = num;
        this.licensePlate = licensePlate;
        this.receiptNum =  receiptNum;
        this.startParked = startParked;
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

    void printCustomers(){
        for(Customer c:customers){
            System.out.println(c.toString());
        }
    }

    void printReceipt(Customer tempC, Vehicle tempV){
        //Customer tempCustomer = temp;
        System.out.println("\n\n\n+ ----------------------------------- +");
        System.out.println("|  Please keep this receipt on hand   |");
        System.out.println("|  Date                 " + tempC.parkedString(1) + "   |");
        System.out.println("|  Time                       " + tempC.parkedString(2) + "   |");
        System.out.println("+ ----------------------------------- +");
        System.out.println("-  Customer ID               | " + tempC.getCustomerNum());
        System.out.println("-  Registered license plate  | " + tempC.getLicensePlate());
        System.out.println("-  Zone ID                   | " + tempV.getZone());
        System.out.println("-  Space ID                  | " + tempV.getSpace());
        System.out.println("+ ----------------------------------- +");
        System.out.println("|  CODE                      | " + tempC.getReceiptNum() + "  |");
        System.out.println("+ ----------------------------------- +");
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

            Customer c = new Customer(i,l,r,p);
            customers.add(c);
        }
    }

        //saves to file
    void save() throws IOException {
        FileWriter fileOutput = new FileWriter("customer.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);
        for (Customer c:customers) {
            outfile.println( c.getCustomerNum() + ":" + c.getLicensePlate() + ":" + c.getReceiptNum() + ":" + c.getStartParked());
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
        sb.append("ID: ");
        sb.append(customerNum);
        sb.append(", Registered license plate: ");
        sb.append(licensePlate);
        sb.append(", Receipt code: ");
        sb.append(receiptNum);
        sb.append(", parked: ");
        sb.append(parkedString(3));
        return sb.toString();
    }
}

package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer extends Driver {
    private int customerNum;    //Starts counting from
    private String licensePlate;
    private ArrayList<Customer> customers = new ArrayList<>();  //ArrayList keeping track of all current customers

    Customer(){

    }

    private Customer(int num) {
        this.customerNum = num;
    }

    private Customer(int num, String licensePlate, int receiptNum){
        this.customerNum = num;
        this.licensePlate = licensePlate;
        this.receiptNum =  receiptNum;
    }

    private int generateCustomerNumber(){
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

    void printCustomers(){
        for(Customer c:customers){
            System.out.println(c.toString());
        }
    }

    Customer newCustomer(){
        int i = generateCustomerNumber();
        Customer tempCustomer = new Customer(i);
        //System.out.println(customers.toString());    //for debugging purposes
        return tempCustomer;
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

        //loads from file
    void load() throws IOException {
        FileReader fileInput = new FileReader("customer.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        while(infile.hasNext()) {
            int i = infile.nextInt();
            String l = infile.next();
            int r = infile.nextInt();

            Customer c = new Customer(i,l,r);
            customers.add(c);
        }
    }

        //saves to file
    void save() throws IOException {
        FileWriter fileOutput = new FileWriter("customer.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);
        for (Customer c:customers) {
            outfile.println( c.getCustomerNum() + ":" + c.getLicensePlate() + ":" + c.getReceiptNum());
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
        return sb.toString();
    }
}

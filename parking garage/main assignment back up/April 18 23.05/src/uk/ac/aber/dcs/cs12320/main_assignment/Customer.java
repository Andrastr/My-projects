package uk.ac.aber.dcs.cs12320.main_assignment;

import java.util.ArrayList;

public class Customer extends Driver {
    private int customerNum;    //Starts counting from
    private ArrayList<Customer> customers = new ArrayList<>();  //ArrayList keeping track of all current customers

    public Customer(){

    }

    public Customer(int num) {
        this.customerNum = num;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
    }

    public String printCustomers(){
        return customers.toString();
    }

    public Customer newCustomer(){
        customerNum++;
        Customer tempCustomer = new Customer(customerNum);
        //System.out.println(customers.toString());    //for debugging purposes
        return tempCustomer;
    }

    public void addCustomer(Customer newCustomer){
        customers.add(newCustomer);
    }

    public void removeCustomer(Customer customer){
        customers.remove(customer);
    }

    public Customer searchForCustomer(int customerNum){
        Customer result = null;
        for(Customer c: customers){
            if(c.getCustomerNum() == customerNum){
                result = c;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "ID: " + customerNum;
    }
}

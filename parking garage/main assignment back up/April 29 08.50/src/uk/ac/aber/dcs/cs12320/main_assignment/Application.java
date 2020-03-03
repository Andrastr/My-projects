package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Application {
    private boolean quit = false;
    private Scanner scan;
    private Attendant attendant = new Attendant();
    private Customer customer = new Customer();
    private Vehicle vehicle = new Vehicle();
    private Zone zone = new Zone();
    private String format = "|\t%1$-33s|\n";

    /**
     * Loads data from the files
     * @throws IOException
     */
    private void setUp() throws IOException{
        zone.load();
        zone.loadZone();
        attendant.load();
        customer.load();
        vehicle.load();

    }

    /**
     * Saves data to the files
     * @throws IOException
     */
    private void shutDown()throws IOException{
        System.out.println("\n\n---| Saving data |---");
        zone.save();
        zone.saveZone();
        attendant.save();
        customer.save();
        vehicle.save();
        System.out.println("\n-----| Goodbye |-----");
    }

    public static void main(String[] args) throws IOException{
        Application app = new Application();
        app.setUp();
        app.customerMenu();
        app.shutDown();
    }

    /**
     * Prints the main menu for customers
     */
    private void printMenu(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "Welcome to Strand parking house");
        System.out.format(format, "");
        System.out.format(format, "Please note that attendants can");
        System.out.format(format, "only park Standard, Higher and");
        System.out.format(format, "Longer vehicles");
        System.out.format(format, "If you drive another type,");
        System.out.format(format, "please park it yourself");
        System.out.format(format, "");
        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "");
        System.out.format(format, "Please select an option below");
        System.out.format(format, "");
              //The customer would have a touch screen with options
             //for option 1, the customer would in actually drop off their vehicle by the front door as specified in the problem statement
        System.out.format(format, "1. Call an attendant");
        System.out.format(format, "2. Park a vehicle");
        System.out.format(format, "3. Retrieve a parked vehicle");
        System.out.format(format, "4. Display a garage zone");
            //In reality, this would not be an option on the terminal/app that the customers has access to
        System.out.format(format, "5. Open the employee menu");
        System.out.format(format, "6. Break the system");
        System.out.format(format, "");
        System.out.println("+ ---------------------------------- +");
    }

    /**
     * Interactions from the menu for customers
     */
    private void customerMenu() {
        String response;

        do {
            printMenu();
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    callAttendant();
                    break;
                case "2":
                    registerVehicle(false);
                    break;
                case "3":
                    retrieveVehicle();
                    break;
                case "4":
                    displayZone();
                    break;
                case"5":
                    System.out.println("\n\n\n\n\n\n\n\n\n\n");
                    employeeMenu();
                    break;
                case"6":
                    systemBreak();
                    //zone.printZones();
                    //customer.printCustomers();
                    //vehicle.printVehicles();
                    //System.out.println(zone.printZoneBoolean(zone.z1Space));
                    //System.out.println(zone.printZoneBoolean(zone.z2Space));
                    //System.out.println(zone.printZoneBoolean(zone.z3Space));
                    //System.out.println(zone.printZoneBoolean(zone.z4Space));
                    //System.out.println(zone.printZoneBoolean(zone.z5Space));
                    pressEnterToContinue();
                    break;
                // there is no mention in the menu about "Q", as the user is not supposed to close the program
                case "Q":
                    quit = true;
                    break;
                default:
                    System.out.println("\n\n\n\n\nInvalid input, try again\n");
                    systemSleep(2000);
            }
        } while (!quit);
        quit = false;
    }

    /**
     * Menu for atendants parking a vehicle
     */
    private void attendantMenu(){
        String response;

        do{
            System.out.println("\n+ ---------------------------------- +");
            System.out.format(format, "Please select an option below");
            System.out.format(format, "");
            System.out.format(format, "1. Park a new vehicle");
            System.out.format(format, "2. Retrieve a parked vehicle");
            System.out.format(format, "3. Display the garage zones");
            System.out.format(format, "");
            System.out.println("+ ---------------------------------- +\n");

            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch(response){
                case "1":
                    registerVehicle(true);
                    quit = true;
                    break;
                case "2":
                    retrieveVehicle();
                    quit = true;
                    break;
                case"3":
                    zone.displayZone(1);
                    zone.displayZone(2);
                    zone.displayZone(3);
                    zone.displayZone(4);
                    zone.displayZone(5);
                    pressEnterToContinue();
                default:
                    System.out.println("\n\n\n\n\nInvalid input, try again\n");
            }
        } while(!quit);
        quit = false;
    }

    /**
     * Menu for people working at the garage
     */
    private void employeeMenu(){
        String response;

        do{
            System.out.println("\n\n\n+ ---------------------------------- +");
            System.out.format(format, "Please select a option below");
            System.out.format(format, "");
            System.out.format(format, "1. Register a new attendant");
            System.out.format(format, "2. Get a list of attendants");
            System.out.format(format, "3. Change the rate of a zone");
            System.out.format(format, "4. Print info about the zones");
            System.out.format(format, "5. Display the garage zones");
            System.out.format(format, "6. Go back to the main menu");
            System.out.format(format, "");
            System.out.println("+ ---------------------------------- +");

            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch(response){
                case "1":
                    newAttendant();
                    break;
                case "2":
                    System.out.println("\n\n---Free Attendants---");
                    attendant.printFreeAttendants();
                    System.out.println("\n---Occupied Attendants---");
                    attendant.printOccupiedAttendants();
                    pressEnterToContinue();
                    break;
                case "3":
                    zone.changePrices();
                    break;
                case "4":
                    zone.printZones();
                    pressEnterToContinue();
                    break;
                case "5":
                    zone.displayZone(1);
                    zone.displayZone(2);
                    zone.displayZone(3);
                    zone.displayZone(4);
                    zone.displayZone(5);
                    pressEnterToContinue();
                    break;
                case "6":
                    quit = true;
                    break;
                    //only reason I have the "Q" below is because I press q out of habit to exit
                case "Q":
                    quit = true;
                default:
                    System.out.println("\n\n\n\n\nInvalid input, try again\n");
            }
        } while(!quit);
        quit = false;
    }

    /**
     * Creates new attednant
     * Called in employeeMenu()
     */
    private void newAttendant(){
        attendant.newAttendant();
    }

    /**
     * Method for calling an attendant
     */
    private void callAttendant(){
        if(!attendant.isFreeEmpty()){
            Attendant randAttendant;
            randAttendant = attendant.callAttendant();
            attendant.setIsFree(false);
            System.out.println("+ ---------------------------------- +");
            System.out.format(format, "Your new attendant is " + randAttendant.getName());
            System.out.println("+ ---------------------------------- +");
            attendantMenu();
        } else{
            System.out.println("+ ---------------------------------- +");
            System.out.format(format, "There are no free attendants");
            System.out.format(format, "Please park the car yourself");
            System.out.format(format, "We are sorry for this");
            System.out.format(format, "inconvenience");
            System.out.println("+ ---------------------------------- +");
            pressEnterToContinue();
        }
    }

    /**
     * Creates a new Customer
     *
     * @return customer
     */
    private Customer newCustomer(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        Customer newCustomer = new Customer(customer.generateCustomerNumber());
        customer.addCustomer(newCustomer);
        return newCustomer;
    }

    /**
     * Allows the user to register their vehicle at the garage
     * Adds vehicle and customer to respective ArrayLists
     *
     * @param isAttendant
     */
    private void registerVehicle(boolean isAttendant){
        String response;
        String license;
        int num = generateReceipt();

        Customer tempCustomer = newCustomer();
        tempCustomer.setReceiptNum(num);

        Vehicle tempVehicle = new Vehicle(tempCustomer.getCustomerNum(), num);
        vehicle.addVehicle(tempVehicle);

        if(!isAttendant){
            do{
                System.out.println("+ ---------------------------------- +");
                System.out.format(format, "Welcome, what kind of vehicle");
                System.out.format(format, "will you be parking today?");
                System.out.println("+ ---------------------------------- +");
                System.out.format(format, "1. Standard");
                System.out.format(format, "2. Higher");
                System.out.format(format, "3. Longer");
                System.out.format(format, "4. Coach");
                System.out.format(format, "5. Motorbike");
                System.out.format(format, "");
                System.out.format(format, "6. Help");
                System.out.println("+ ---------------------------------- +");

                scan = new Scanner(System.in);
                response = scan.nextLine().toUpperCase();
                switch (response) {
                    case "1":
                        tempVehicle.setType(1);
                        quit = true;
                        break;
                    case "2":
                        tempVehicle.setType(2);
                        quit = true;
                        break;
                    case "3":
                        tempVehicle.setType(3);
                        quit = true;
                        break;
                    case "4":
                        tempVehicle.setType(4);
                        quit = true;
                        break;
                    case "5":
                        tempVehicle.setType(5);
                        quit = true;
                        break;
                    case "6":
                        System.out.println("\n+ ---------------------------------- +");
                        System.out.format(format, "Help menu");
                        System.out.println("+ ---------------------------------- +");
                        System.out.format(format, "Standard:");
                        System.out.format(format, "Up to 2m high");
                        System.out.format(format, "Up to 5m long");
                        System.out.format(format, "");
                        System.out.format(format, "Higher:");
                        System.out.format(format, "Between 2m and 3m in height");
                        System.out.format(format, "");
                        System.out.format(format, "Long:");
                        System.out.format(format, "Up to 3m high");
                        System.out.format(format, "Between 5.1m and 6m long");
                        System.out.format(format, "");
                        System.out.format(format, "Coach:");
                        System.out.format(format, "Any height");
                        System.out.format(format, "Up to 15m long");
                        System.out.format(format, "");
                        System.out.format(format, "Motorbike:");
                        System.out.format(format, "Two wheels");
                        System.out.println("+ ---------------------------------- +");
                        pressEnterToContinue();
                        break;
                    default:
                        System.out.println("\n\n\n\n\nInvalid input\n");
                        systemSleep(2000);
                }
            } while (!quit);
            quit = false;
        } else{
            do{
                System.out.println("+ ---------------------------------- +");
                System.out.format(format, "What kind of vehicle");
                System.out.format(format, "will you be parking?");
                System.out.println("+ ---------------------------------- +");
                System.out.format(format, "1. Standard");
                System.out.format(format, "2. Longer");
                System.out.format(format, "3. Higher");
                System.out.println("+ ---------------------------------- +");

                scan = new Scanner(System.in);
                response = scan.nextLine().toUpperCase();
                switch (response) {
                    case "1":
                        tempVehicle.setType(1);
                        quit = true;
                        break;
                    case "2":
                        tempVehicle.setType(3);
                        quit = true;
                        break;
                    case"3":
                        tempVehicle.setType(2);
                        quit = true;
                        break;
                    default:
                        System.out.println("\n\n\n\n\nInvalid input\n");
                        systemSleep(2000);
                }
            } while (!quit);
            quit = false;
        }

        System.out.println("+ ---------------------------------- +");
        System.out.println("| Please register your license plate |");
        scan = new Scanner(System.in);
        license = scan.nextLine();
        tempVehicle.setLicense(license);
        tempCustomer.setLicensePlate(license);
        //tempVehicle.toString();  //for debugging purposes
        do{
                //Here the user would be given two boxes to click, yes and no
            System.out.format(format, "Your license plate is: " + license);
            System.out.format(format, "Is this correct?   y/n");

            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "Y":
                    System.out.format(format, "");
                    System.out.format(format, "Thank you for confirming");
                    quit = true;
                    break;
                case "N":
                    System.out.format(format, "");
                    System.out.format(format, "Please re-enter your");
                    System.out.format(format, "license plate number");
                    scan = new Scanner(System.in);
                    license = scan.nextLine();
                    tempVehicle.setLicense(license);
                    break;
                default:
                    System.out.println("Invalid input\n");
                    systemSleep(2000);
            }
        } while (!quit);
        quit = false;

        System.out.format(format, "");
            //The user would again recieve two boxes, yes and no
        System.out.format(format, "Is you vehicle");
        System.out.format(format, "disabled registered?  y/n");
        scan = new Scanner(System.in);
        response = scan.nextLine().toUpperCase();
        do{
            switch (response){
                case "Y":
                    tempCustomer.isDisabled(true);
                    quit = true;
                    break;
                case "N":
                    tempCustomer.isDisabled(false);
                    quit = true;
                    break;
                default: System.out.println("\n\n\n\n\nInvalid input\n");
                    systemSleep(2000);
            }
        } while(!quit);
        quit = false;

        tempVehicle = findSpace(tempVehicle);

        tempVehicle.setStartParked(System.currentTimeMillis());
        tempCustomer.setStartParked(tempVehicle.getStartParked());
        if (tempVehicle.getZone() == 0){
            System.out.format(format, "There is unfortunately");
            System.out.format(format, "no room for your car");
            System.out.format(format, "We apologise for this");
            System.out.format(format, "inconvenience");
            customer.removeCustomer(tempCustomer);
            vehicle.removeVehicle(tempVehicle);
        } else{
            System.out.println("\nPrinting receipt");
            customer.printReceipt(tempCustomer, tempVehicle);
        }
        pressEnterToContinue();
    }

    /**
     * Generates a receipt for the user to keep
     *
     * @return
     */
    private int generateReceipt(){
        Random random = new Random();
        //There is no system to check if the receipt number is in use, so this could be a problem i the future,
        //but as the receipt number is 5 digits, the chance of getting a duplicate number almost 0 (38/90000 ~ 0.0004)
        int num = random.nextInt(89999) + 10000;
        return num;
    }

    /**
     * Find a free space for the user to park
     * If no free space is found, user is turned away
     *
     * @param secVehicle
     * @return
     */
    private Vehicle findSpace(Vehicle secVehicle){
        int i = 0;

        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "Please follow the given");
        System.out.format(format, "instructions in order to");
        System.out.format(format, "find an empty space:");
        System.out.format(format, "");
        if(secVehicle.getType() == 1){
            for (boolean z: zone.z4Space) {
                if(!z){
                    System.out.format(format, "'instructions'");
                    zone.z4Space[i] = true;
                    secVehicle.setZone(4);
                    secVehicle.setSpace(++i);
                    break;
                }
                i++;
            }
            if(i >= zone.z1Space.length){
                i = 0;
                for (boolean z: zone.z1Space) {
                    if(!z){
                        System.out.format(format, "'instructions'");
                        zone.z1Space[i] = true;
                        secVehicle.setZone(1);
                        secVehicle.setSpace(++i);
                        break;
                    }
                    i++;
                }
            }
        } else if (secVehicle.getType() == 2){
            for (boolean z: zone.z1Space) {
                if(!z){
                    System.out.format(format, "*instructions*");
                    zone.z1Space[i] = true;
                    secVehicle.setZone(1);
                    secVehicle.setSpace(++i);
                    break;
                }
                i++;

            }
        } else if (secVehicle.getType() == 3){
            for (boolean z: zone.z2Space) {
                if (!z){
                    System.out.format(format, "*instructions*");
                    zone.z2Space[i] = true;
                    secVehicle.setZone(2);
                    secVehicle.setSpace(++i);
                    break;
                }
                i++;
            }
        } else if (secVehicle.getType() == 4){
            for (boolean z: zone.z3Space) {
                if(!z){
                    System.out.format(format, "*instructions*");
                    zone.z3Space[i] = true;
                    secVehicle.setZone(3);
                    secVehicle.setSpace(++i);
                    break;
                }
                i++;
            }
        } else if (secVehicle.getType() == 5){
            for (boolean z: zone.z5Space) {
                if(!z){
                    System.out.format(format, "*instructions*");
                    zone.z5Space[i] = true;
                    secVehicle.setZone(5);
                    secVehicle.setSpace(++i);
                    break;
                }
                i++;

            }
        } else{
            System.out.println("------ERROR------\n---Invalid type---");
        }
        System.out.println("+ ---------------------------------- +");
        pressEnterToContinue();
        return secVehicle;
    }

    /**
     * Allows the user to type in the given code i order to find and retrieve their vehicle, then calls payment()
     */
    private void retrieveVehicle(){
        do{

            System.out.println("\n\n+ ---------------------------------- +");
            System.out.format(format, "Please type in the number");
            System.out.format(format, "on your receipt");
            System.out.format(format, "If you have lost your receipt");
            System.out.format(format, "type in '0' to call for help");
            System.out.println("+ ---------------------------------- +");

            scan = new Scanner(System.in);
            int num = scan.nextInt();
            if(num == 0){
                System.out.println("\n\n+ ---------------------------------- +");
                System.out.format(format, "An attendant has been alerted");
                System.out.format(format, "and is on their way");
                System.out.println("+ ---------------------------------- +");
                pressEnterToContinue();
            } else {
                Customer tempCustomer = customer.searchForCustomer(num);
                Vehicle tempVehicle = vehicle.searchForVehicle(num);

                if (tempVehicle != null && tempCustomer != null) {
                    paymentAndToken(tempCustomer, tempVehicle);
                    quit = true;
                } else {
                    System.out.println("\n\n+ ---------------------------------- +");
                    System.out.format(format, "We are sorry, but we are");
                    System.out.format(format, "unable to locate your vehicle");
                    System.out.format(format, "");
                    System.out.format(format, "You might have mistyped");
                    System.out.format(format, "press '3' to return to the");
                    System.out.format(format, "menu and re-enter your code");
                    System.out.println("+ ---------------------------------- +");
                    pressEnterToContinue();
                }
            }
        } while(!quit);
    }

    /**
     * Obtains a random number between 100 and 500 to be used as a token for the exit barrier
     *
     * @return token
     */
    private int generateToken(){
        Random rand = new Random();

            // Obtain a number between 0 - 500
        int token = rand.nextInt(400);
        token += 100;

        return token;
    }

    /**
     * Method used for payment, in order to "exit the garage"
     * Work in progress
     *
     * @param tempCustomer
     * @param tempVehicle
     */
    private void paymentAndToken(Customer tempCustomer, Vehicle tempVehicle){
        long start = tempCustomer.getStartParked();
        long end = System.currentTimeMillis();
        int parkedZone = tempVehicle.getZone();
        double rate;
        Zone tempZone;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat current = new SimpleDateFormat("dd MMM yyyy | HH:MM");

        if(parkedZone == 1){
            tempZone = zone.zones.get(0);
        } else if(parkedZone == 2){
            tempZone = zone.zones.get(1);
        } else if(parkedZone == 3){
            tempZone = zone.zones.get(2);
        } else if (parkedZone == 4) {
            tempZone = zone.zones.get(3);
        } else{
            tempZone = zone.zones.get(4);
        }
        rate = tempZone.getPrice();

        float sec = ((end - start) % 1000F) % 60;
        float min = ((end - start) / 1000F) / 60 % 60;
        float hrs = ((end - start) / 1000F) / 3600;
        sec = Math.round(sec);
        min = Math.round(min);
        hrs = Math.round(hrs);

        if (sec != 0){
            if(sec/sec != 0){
                min++;
            }
        }

        System.out.println("\n\n+ ---------------------------------- +");
        System.out.format(format, "Parked:  " + tempVehicle.parkedString(2));
        System.out.format(format, "Current: " + current.format(cal.getTime()));
        System.out.format(format, "");
        System.out.format(format,("You have been parked for"));
        System.out.format(format, String.format("%d hours and %d minutes", (long)hrs, (long)min));

        if (min != 0){
            if(min/min != 0){
                hrs++;
            }
        }
        if(tempVehicle.getType() != 4){
            if(tempCustomer.getDisabled()){
                rate =rate/2;
            }
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                rate = 0;
            }
        }
        double cost = hrs * rate;

        System.out.format(format, "You will have to pay");
        System.out.format(format, String.format("%d Units as you leave", (long)cost));
        System.out.format(format, "");
        System.out.format(format, "Car information");
        System.out.format(format, "Zone:            " + tempVehicle.getZone());
        System.out.format(format, "Parking Space:   " + tempVehicle.getSpace());
        System.out.println("+ ---------------------------------- +");
        pressEnterToContinue();


        System.out.println("\n\n\n\n\n+ ---------------------------------- +");
        System.out.format(format, "Please Insert coins");
        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "");
        System.out.println("+ ---------------------------------- +");

        do{
            System.out.format(format, "You still need to pay");
            System.out.format(format, String.format("%d Units", (long)cost));
            scan = new Scanner(System.in);
            double paid = scan.nextInt();
            System.out.format(format, String.format("You have paid %d Units", (long)paid));

            if(paid == cost){
                quit = true;
            }else if(paid < cost){
                cost = cost - paid;
            }else if(paid > cost){
                cost = cost - paid;
                cost = Math.abs(cost);

                System.out.format(format, String.format("and will recieve %d Units back", (long)cost));
                quit = true;
            }
        } while(!quit);
        quit = false;

        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "You have now paid the full amount");
        System.out.println("+ ---------------------------------- +");
        pressEnterToContinue();

            //Gives the user the token and makes them type it in, if time used is over 15 minutes, an attendant is called
        int token = generateToken();
        tempCustomer.setToken(token);
        System.out.println("\n+ ---------------------------------- +");
        System.out.format(format, "You will no recieve a token");
        System.out.format(format, "Please type it into the screen");
        System.out.format(format, "by the exit barrier");
        System.out.format(format, "");
        System.out.format(format, String.format("Your token is: %d", token));
        System.out.println("+ ---------------------------------- +");
        pressEnterToContinue();

        System.out.println("\n\n\n----| Driving to gate |----\n\n\n");

        long time = System.currentTimeMillis();
        float timeMin = (time - end) / 1000F / 60;
        timeMin = Math.round(timeMin);
        do{
            System.out.println("\n+ ---------------------------------- +");
            System.out.format(format, "Please enter your token");
            System.out.println("+ ---------------------------------- +");

            scan = new Scanner(System.in);
            int num = scan.nextInt();
            if(timeMin < 15){
                if(num == token){
                    System.out.println("\nGoodBye");
                    quit = true;
                } else if(num != token){
                    System.out.println("\nInvalid token, please enter the one on your receipt");
                } else{
                    System.out.println("\n\n-------| ERROR |-------\n---| INVALID TOKEN |---");
                }
            } else{
                    //notifies an attendant
                attendant.callAttendant();
                System.out.println("\n\n+ ---------------------------------- +");
                System.out.format(format, "You have spent over 15 minutes");
                System.out.format(format, "and will no longer be able to");
                System.out.format(format, "exit the building by yourself");
                System.out.format(format, "An attendant has been notified,");
                System.out.format(format, "and will help you shortly");
                System.out.println("\n\n+ ---------------------------------- +");
                quit = true;
            }
        } while (!quit);
        quit = false;
        pressEnterToContinue();

            //customer and Vehicle is removed if the time limit is reached or not, as the attendant would help the user exit the garage
        customer.removeCustomer(tempCustomer);
        vehicle.removeVehicle(tempVehicle);
    }

    /**
     * Allows users to display all zones parking spaces
     * Calls Zone.displayZone(param)
     */
    private void displayZone() {

        System.out.println("\n\n");
        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "Display Zone");
        System.out.format(format, "Please type in you vehicle type");
        System.out.format(format, "");
        System.out.format(format, "Standard:    '1'");
        System.out.format(format, "High:        '2'");
        System.out.format(format, "Long:        '3'");
        System.out.format(format, "Coach:       '4'");
        System.out.format(format, "Motorbike:   '5'");
        System.out.format(format, "");
        System.out.format(format, "Display all: '6'");
        System.out.format(format, "");
        System.out.println("+ ---------------------------------- +");

        scan = new Scanner(System.in);
        String i = scan.nextLine();
        switch (i) {
            case "1":
                zone.displayZone(1);
                zone.displayZone(4);
                break;
            case "2":
                zone.displayZone(1);
                break;
            case "3":
                zone.displayZone(2);
                break;
            case "4":
                zone.displayZone(3);
                break;
            case "5":
                zone.displayZone(5);
                break;
            case "6":
                zone.displayZone(1);
                zone.displayZone(2);
                zone.displayZone(3);
                zone.displayZone(4);
                zone.displayZone(5);
                break;
            default:
                System.out.println("\n---ERROR---\nINVALID ZONE");
        }
        pressEnterToContinue();
    }

    /**
     * Stops the program until enter button is hit
     * Has a problem with sometimes freezing, causing invalid input to appear
     */
    private void pressEnterToContinue(){
        System.out.println("\nPress Enter to continue...");
        try {
            System.in.read();
        } catch(Exception e){}
    }

    /**
     * Puts the system to sleep for x milliseconds
     * @param millis milliseconds program will wait for
     */
    private void systemSleep(int millis){
        try {
            Thread.sleep(millis);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void systemBreak(){
        List<String> l = new ArrayList<String>(Integer.MAX_VALUE);
    }

}

package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Application {
    private boolean quit = false;
    private Scanner scan;
    private Attendant attendant = new Attendant();
    private Customer customer = new Customer();
    private Vehicle vehicle = new Vehicle();
    //private Vehicle secVehicle = new Vehicle();
    private Zone zone = new Zone();

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
        //System.out.println("\nFree attendants: ");
        //attendant.printFreeAttendants();
        //System.out.println("\nOccupied attendants: ");
        //attendant.printOccupiedAttendants();
        //System.out.println("\nRegisterd customers: ");
        //customer.printCustomers();
        //System.out.println("\nRegisterd vehicles: ");
        //vehicle.printVehicles();
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
        //System.out.println("+ ---------------| Hello and welcome to Strand inc. parking house |--------------- +");
        System.out.println("+ ------------------------------- +");
        System.out.println("| Welcome to Strand parking house |");
        System.out.println("|                                 |");
        System.out.println("| Please note that attendants can |");
        System.out.println("| only park Standard, Higher and  |");
        System.out.println("| Longer vehicles                 |");
        System.out.println("| If you own another type         |");
        System.out.println("| please park it yourself         |");
        System.out.println("|                                 |");
        System.out.println("+ ------------------------------- +");
        System.out.println("|                                 |");
             //The customer would here be given a numpad to enter a number
            //for option 1, the customer would in actually drop off their vehicle by the front door as specified in the problem statement1
        System.out.println("|  Please select a option below   |");
        System.out.println("|                                 |");
        System.out.println("|  1. Call an attendant           |");
        System.out.println("|  2. Park a new vehicle          |");
        System.out.println("|  3. Retrieve a parked vehicle   |");
        System.out.println("|  4. Display the parking zones   |");
        //In reality, this would not be an option on the terminal/app that the customers has access to     |
        System.out.println("|  5. Open the employee menu      |");
        System.out.println("|  6. Break the system            |");
        System.out.println("+ ------------------------------- +");
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
                    //systemBreak();
                    //zone.printZones();
                    //customer.printCustomers();
                    //vehicle.printVehicles();
                    System.out.println(zone.printZoneBoolean(zone.z1Space));
                    System.out.println(zone.printZoneBoolean(zone.z2Space));
                    System.out.println(zone.printZoneBoolean(zone.z3Space));
                    System.out.println(zone.printZoneBoolean(zone.z4Space));
                    System.out.println(zone.printZoneBoolean(zone.z5Space));
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
            System.out.println("\n+ ------------------------------- +");
            System.out.println("|  Please select an option below  |");
            System.out.println("|                                 |");
            System.out.println("|  1. Park a new vehicle,         |");
            System.out.println("|  2. Retrieve a parked vehicle   |");
            System.out.println("|  3. Display the zones,          |");
            System.out.println("+ ------------------------------- +\n");

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
            System.out.println("\n+ ------------------------------- +");
            System.out.println("|  Please select a option below   |");
            System.out.println("|                                 |");
            System.out.println("|  1. Register a new attendant    |");
            System.out.println("|  2. Get a list of attendants    |");
            System.out.println("|  3. Change the price of a zone  |");
            System.out.println("|  4. Print info about the zones  |");
            System.out.println("|  5. Display the zones           |");
            System.out.println("|  6. Go back to the main menu    |");
            System.out.println("+ ------------------------------- +");

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
                    setPrice();
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
        Attendant randAttendant;
        randAttendant = attendant.callAttendant();

        if(randAttendant != null){
            attendant.setIsFree(false);
            attendantMenu();
        } else{
            System.out.println("\nThere is no free attendants at the moment, please register and park the car yourself.");
            System.out.println("\nWe are truly sorry for the inconvenience this may cause.");
            systemSleep(5000);
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
                System.out.println("Welcome, what kind of vehicle will you be parking today?");
                System.out.println("1. Standard (up to 2m high and 5m long");
                System.out.println("2. Higher (between 2m and 3m in height");
                System.out.println("3. Longer (up to 3m high, between 5.1m and 6m long");
                System.out.println("4. Coach (any height, up to 15m long");
                System.out.println("5. Motorbike");

                scan = new Scanner(System.in);
                response = scan.nextLine().toUpperCase();
                switch (response) {
                    case "1":
                        tempVehicle.setType(1);
                        System.out.println("You have a standard vehicle");
                        quit = true;
                        break;
                    case "2":
                        tempVehicle.setType(2);
                        System.out.println("You have a high vehicle");
                        quit = true;
                        break;
                    case"3":
                        tempVehicle.setType(3);
                        System.out.println("You have a long vehicle");
                        quit = true;
                        break;
                    case"4":
                        tempVehicle.setType(4);
                        System.out.println("You have a coach");
                        quit = true;
                        break;
                    case"5":
                        tempVehicle.setType(5);
                        System.out.println("You have a motorbike");
                        quit = true;
                        break;
                    default:
                        System.out.println("\n\n\n\n\nInvalid input\n");
                        systemSleep(2000);
                        quit = false;
                }
            } while (!quit);
            quit = false;
        } else{
            do{
                System.out.println("Hello, which vehicle will you be parking today?");
                System.out.println("1. Standard (up to 2m high and 5m long");
                System.out.println("2. Higher (between 2m and 3m in height");
                System.out.println("3. Longer (up to 3m high, between 5.1m and 6m long");

                scan = new Scanner(System.in);
                response = scan.nextLine().toUpperCase();
                switch (response) {
                    case "1":
                        tempVehicle.setType(1);
                        System.out.println("You have a standard vehicle");
                        quit = true;
                        break;
                    case "2":
                        tempVehicle.setType(2);
                        System.out.println("You have a high vehicle");
                        quit = true;
                        break;
                    case"3":
                        tempVehicle.setType(3);
                        System.out.println("You have a long vehicle");
                        quit = true;
                        break;
                    default:
                        System.out.println("\n\n\n\n\nInvalid input\n");
                        systemSleep(2000);
                        quit = false;
                }
            } while (!quit);
            quit = false;
        }


        System.out.println("\nPlease register your license plate ");
        scan = new Scanner(System.in);
        license = scan.nextLine();
        tempVehicle.setLicense(license);
        tempCustomer.setLicensePlate(license);
        //tempVehicle.toString();  //for debugging purposes
        do{
            System.out.println("You license plate number is: " + license + "\nIs this correct? y/n");
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "Y":
                    System.out.println("\nThank you for confirming");
                    quit = true;
                    break;
                case "N":
                    System.out.println("Please re-enter your license plate number");
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

        System.out.println("\nIs your vehicle disabled registered? y/n");
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
                    quit = false;
            }
        } while(!quit);
        quit = false;

        tempVehicle = findSpace(tempVehicle);

        tempVehicle.setStartParked(System.currentTimeMillis());
        tempCustomer.setStartParked(tempVehicle.getStartParked());
        //System.out.println(tempVehicle);
        //System.out.println(tempCustomer);
        if (tempVehicle.getZone() == 0){
            System.out.println("\nThere is unfortunately no room for your car, we are terribly sorry for this inconvenience");
            customer.removeCustomer(tempCustomer);
            vehicle.removeVehicle(tempVehicle);
        } else{
            System.out.println("Here is your receipt");
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

        if(secVehicle.getType() == 1){
            for (boolean z: zone.z4Space) {
                if(!z){
                    System.out.println("Please follow the given instruction in order to find an empty space");
                    System.out.println("*instructions");
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
                        System.out.println("Please follow the given instruction in order to find an empty space");
                        System.out.println("*insert instructions here*");
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
                    System.out.println("Please follow the given instruction in order to find an empty space");
                    System.out.println("*instructions*");
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
                    System.out.println("Please follow the given instructions to find an empty space");
                    System.out.println("*instructions*");
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
                    System.out.println("Please follow the given instructions in order to find an empty space");
                    System.out.println("*instructions*");
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
                    System.out.println("\nPlease follow the given instructions in order to find an empty space");
                    System.out.println("*Drive to the top floor, turn right, and there is your new spot*");
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
        pressEnterToContinue();
        return secVehicle;
    }

    /**
     * Allows the user to type in the given code i order to find and retrieve their vehicle, then calls payment()
     */
    private void retrieveVehicle(){
        System.out.println("\n\n+ ------------------------------- +");
        System.out.println("|  Please type in the number      |");
        System.out.println("|  on your receipt                |");
        System.out.println("|  If you have lost your receipt  |");
        System.out.println("|  type in '0' to call for help   |");
        System.out.println("+ ------------------------------- +");

        scan = new Scanner(System.in);
        int num = scan.nextInt();
        if(num == 0){
            System.out.println("\n\n+ ------------------------------- +");
            System.out.println("|  An attendant has been alerted  |");
            System.out.println("|  and is on their way            |");
            System.out.println("+ ------------------------------- +");
            pressEnterToContinue();
        } else {
            Customer tempCustomer = customer.searchForCustomer(num);
            Vehicle tempVehicle = vehicle.searchForVehicle(num);

            if (tempVehicle != null && tempCustomer != null) {
                System.out.println(tempCustomer.toString());
                int token = generateToken();
                tempCustomer.setToken(token);

                System.out.println("\n\n+ ------------------------------- +");
                System.out.println("|  Parked: " + tempVehicle .parkedString(2) + "    |");
                System.out.println("|                                 |");
                System.out.println("|  You will now recieve a token   |");
                System.out.println("|  Please type it into the screen |");
                System.out.println("|  at the barrier by the exit     |");
                System.out.println("|                                 |");
                System.out.println("|  Your token is: " + token + "             |");
                System.out.println("|                                 |");
                System.out.println("|         Car information         |");
                System.out.println("|  Zone:          " + tempVehicle.getZone() + "               |");
                System.out.println("|  Parking space: " + tempVehicle.getSpace() + "               |");
                System.out.println("+ ------------------------------- +");
                pressEnterToContinue();
                payment(tempCustomer, tempVehicle);

                customer.removeCustomer(tempCustomer);
                vehicle.removeVehicle(tempVehicle);
                pressEnterToContinue();
            } else {
                System.out.println("\n\n+ ------------------------------- +");
                System.out.println("|  We are truly sorry, but we are |");
                System.out.println("|  unable to locate your vehicle  |");
                System.out.println("|                                 |");
                System.out.println("|  You might have mistyped,       |");
                System.out.println("|  Press '3' to return to the     |");
                System.out.println("|  menu and re-enter your code    |");
                System.out.println("+ ------------------------------- +");
                pressEnterToContinue();
                }
            }

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
    private void payment(Customer tempCustomer, Vehicle tempVehicle){
        long start = tempCustomer.getStartParked();
        long end = System.currentTimeMillis();
        int parkedZone = tempVehicle.getZone();
        double rate;
        double paid;
        Zone tempZone;
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
        DecimalFormat format = new DecimalFormat("0");
        
        System.out.println("\n+ ------------------------------- +");
        System.out.println("|  You have been parked for       |" );

        if (sec != 0){
            if(sec/sec != 0){
                min++;
            }
        }
        if (min != 0){
            if(min/min != 0){
                hrs++;
            }
        }

            //If setting is for formatting the menu, the | at the end would otherwise move with changing hours
        if(hrs == 1 && min == 1){
            System.out.println("|  " + format.format(hrs) + " hour and " + format.format(min) + " minute            |" );   // If hours == 1 and minutes == 1
        } else if(hrs == 1 && min < 10) {
            System.out.println("|  " + format.format(hrs) + " hour and " + format.format(min) + " minutes           |"  );  // If hours == 1 and minutes < 10
        } else if(hrs == 1 && min < 60){
            System.out.println("|  " + format.format(hrs) + " hour and " + format.format(min) + " minutes          |" );    // If hours == 1 and minutes < 60
        } else if(hrs < 10 && min == 1){
            System.out.println("|  " + format.format(hrs) + " hours and " + format.format(min) + " minute           |"  );  // If hours < 10 and minutes == 1
        } else if(hrs < 100 && min == 1){
            System.out.println("|  " + format.format(hrs) + " hours and " + format.format(min) + " minute          |" );    // If hours < 100 and minutes == 1
        } else if(hrs < 1000 && min == 1){
            System.out.println("|  " + format.format(hrs) + " hours and " + format.format(min) + " minute         |" );     // If hours < 1000 and minutes == 1
        } else if(hrs < 10 && min < 10) {
            System.out.println("|  " + format.format(hrs) + " hours and " + format.format(min) + " minutes          |" );   // If hours < 10 and minutes < 10
        } else if(hrs < 100 && min < 10){
            System.out.println("|  " + format.format(hrs) + " hours and " + format.format(min) + " minutes         |" );    // If hours < 100 and minutes < 10
        } else if(hrs < 10 && min < 60){
            System.out.println("|  " + format.format(hrs) + " hours and " + format.format(min) + " minutes         |" );    // If hours < 10 and minutes < 60
        } else if(hrs < 100 && min < 60){
            System.out.println("|  " + format.format(hrs) + " hours and " + format.format(min) + " minutes        |" );     // If hours < 100 and minutes < 60
        } else if(hrs < 1000 && min < 10){
            System.out.println("|  " + format.format(hrs) + " hours and " + format.format(min) + " minutes        |"   );   // If hours < 1000 and minutes < 10
        } else if(hrs < 1000 && min < 60){
            System.out.println("|  " + format.format(hrs) + " hours and " + format.format(min) + " minutes       |"  );     // If hours < 1000 and minutes < 60
        } else{
            System.out.println("|  NOT SUPPOSED TO HAPPEN         |"   );
        }

        Calendar cal=Calendar.getInstance();
        if(tempVehicle.getType() != 4){
            if(tempCustomer.getDisabled()){
                rate =rate/2;
            }
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                rate = 0;
            }
        }
        double cost = hrs * rate;

        System.out.println("|  You will have to pay           |");
        if(cost < 10){
            System.out.println("|  " + cost + " Units as your leave        |");
        } else if(cost < 100){
            System.out.println("|  " + cost + " Units as your leave       |");
        } else if(cost < 1000){
            System.out.println("|  " + cost + " Units as your leave      |");
        }else{
            System.out.println("|  NOT SUPPOSED TO HAPPEN         |"   );
        }
        System.out.println("+ ------------------------------- +");
        pressEnterToContinue();

          //NEXT TO DO:
         //FINISH PAYMENT()
        //MAKE THE USER ABLE TO ACTUALLY PAY, INSERT TOKEN ETC.

        System.out.println("\n\n\n----| Time spent driving to gate |----\n\n\n");

        System.out.println("+ ------------------------------- +");
        System.out.println("|     Please enter your token     |");
        System.out.println("+ ------------------------------- +");


    }

    /**
     * Allows employees to change the hourly rates for different zones
     */
    private void setPrice(){
        List<Zone> list = zone.getZones();
        double price;

        do{
            System.out.println("\\n\\n\\n\\n\\n\\n+ ------------------------------- +");
            System.out.println("|    Select the zone to change    |");
            System.out.println("|                                 |");
            System.out.println("|            1. Zone 1            |");
            System.out.println("|            2. Zone 2            |");
            System.out.println("|            3. Zone 3            |");
            System.out.println("|            4. Zone 4            |");
            System.out.println("|            5. Zone 5            |");
            System.out.println("+ ------------------------------- +\n");
            scan = new Scanner(System.in);
            int zone = scan.nextInt();

            switch (zone){
                case 1:
                    System.out.println("\n\nEnter the hourly rate for zone 1\n");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    list.get(0).setPrice(price);
                    quit = true;
                    break;
                case 2:
                    System.out.println("\n\nEnter the hourly rate for zone 2\n");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    list.get(1).setPrice(price);
                    quit = true;
                    break;
                case 3:
                    System.out.println("\n\nEnter the hourly rate for zone 3\n");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    list.get(2).setPrice(price);
                    quit = true;
                    break;
                case 4:
                    System.out.println("\n\nEnter the hourly rate for zone 4\n");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    list.get(3).setPrice(price);
                    quit = true;
                    break;
                case 5:
                    System.out.println("\n\nEnter the hourly rate for zone 5\n");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    list.get(4).setPrice(price);
                    quit = true;
                    break;
                default:
                    System.out.println("\n\n\n\n\nInvalid input, try again\n");
                    break;
            }
        } while (!quit);
        quit = false;
    }

    /**
     * Allows users to display all zones parking spaces
     * Calls Zone.displayZone(param)
     */
    private void displayZone() {
        System.out.println("\n\n+ --------| DISPLAY ZONE |-------- +");
        System.out.println("|  Please enter your vehicle type  |");
        System.out.println("|                                  |");
        System.out.println("|     Standard:    '1'             |");
        System.out.println("|     High:        '2'             |");
        System.out.println("|     Long:        '3'             |");
        System.out.println("|     Coach:       '4'             |");
        System.out.println("|     Motorbike:   '5'             |");
        System.out.println("|                                  |");
        System.out.println("|     Display all: '6'             |");
        System.out.println("+ -------------------------------- +");

        scan = new Scanner(System.in);
        int i = scan.nextInt();
        switch (i) {
            case 1:
                zone.displayZone(1);
                zone.displayZone(4);
                break;
            case 2:
                zone.displayZone(1);
                break;
            case 3:
                zone.displayZone(2);
                break;
            case 4:
                zone.displayZone(3);
                break;
            case 5:
                zone.displayZone(5);
                break;
            case 6:
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
     * @param millis
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

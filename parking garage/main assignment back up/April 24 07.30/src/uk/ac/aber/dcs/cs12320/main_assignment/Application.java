package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.IOException;
import java.util.*;

public class Application {
    private boolean quit = false;
    private Scanner scan;
    private Attendant attendant = new Attendant();
    private Customer customer = new Customer();
    private Vehicle vehicle = new Vehicle();
    //private Vehicle secVehicle = new Vehicle();
    private Zone zone = new Zone();

    private void setUp() throws IOException{
        zone.load();
        zone.loadZone();
        attendant.load();
        customer.load();
        vehicle.load();

    }

    private void shutDown()throws IOException{
        System.out.println("\n\nSaving data");
        zone.save();
        zone.saveZone();
        System.out.println("\nFree attendants: ");
        attendant.printFreeAttendants();
        System.out.println("\nOccupied attendants: ");
        attendant.printOccupiedAttendants();
        attendant.save();
        System.out.println("\nRegisterd customers: ");
        customer.printCustomers();
        customer.save();
        System.out.println("\nRegisterd vehicles: ");
        vehicle.printVehicles();
        vehicle.save();
        System.out.println("\n---Goodbye---");
    }

    public static void main(String[] args) throws IOException{
        Application app = new Application();
        app.setUp();
        app.customerMenu();
        app.shutDown();
    }

    private void printMenu(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("+ ---------------| Hello and welcome to Strand inc. parking house |--------------- +");
        System.out.println("|                                                                                  |");
        System.out.println("|  Please note that attendants can only park Standard, Higher and Longer vehicles  |");
        System.out.println("|  If you own another type, please park it yourself                                |");
        System.out.println("|                                                                                  |");
        System.out.println("+ -------------------------------------------------------------------------------- +");
            //in reality, the customer would drop off their vehicle by the front door as specified in the problem statement
        System.out.println("|  If you want to call an attendant, please type in '1'                            |");
        System.out.println("|  If you want to park a new vehicle, please type in '2'                           |");
        System.out.println("|  If you want to retrieve a parked vehicle, please type in '3'                    |");
            //In reality, this would not be an option on the terminal/app that the customers has access to     |
        System.out.println("|  If you are an employee, please type in in '4'                                   |");
        System.out.println("|  If you want to display the parking spaces, please type in '5'                   |");
        System.out.println("|  If you want to break the system, please type in '6'                             |");
        System.out.println("+ -------------------------------------------------------------------------------- +");
    }

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
                    System.out.println("\n\n\n\n\n\n\n\n\n\n");
                    employeeMenu();
                    break;
                case"5":
                    displayZone();
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

    private void attendantMenu(){
        String response;

        do{
            System.out.println("\n+ ----------------------------------------------------------------- +");
            System.out.println("|  1. If you want to park a new vehicle, please type in '1'         |");
            System.out.println("|  2. If you want to retrieve a parked vehicle, please type in '2'  |");
            System.out.println("+ ----------------------------------------------------------------- +\n");

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
                default:
                    System.out.println("\n\n\n\n\nInvalid input, try again\n");
            }
        } while(!quit);
        quit = false;
    }

    private void employeeMenu(){
        String response;

        System.out.println("\nWelcome");

        do{
            System.out.println("\n");
            System.out.println("+ ---------------------------------------------------------------- +");
            System.out.println("|  1. If you want to register a new attendant, type in '1'         |");
            System.out.println("|  2. If you want to get a list of all attendants, type in '2'     |");
            System.out.println("|  3. If you want to change the price of a zone, type in '3'       |");
            System.out.println("|  4. If you want to print out all the zones, type in '4'          |");
            System.out.println("|  5. If you want to go back to the main menu, please type in '5'  |");
            System.out.println("+ ---------------------------------------------------------------- +");

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

    private void newAttendant(){
        attendant.newAttendant();
    }

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

    private Customer newCustomer(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        Customer newCustomer = new Customer(customer.generateCustomerNumber());
        customer.addCustomer(newCustomer);
        return newCustomer;
    }

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

        System.out.println("\nPlease enter your license plate number");
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

        tempVehicle = findSpace(tempVehicle);

        tempVehicle.setStartParked(System.currentTimeMillis());
        tempCustomer.setStartParked(tempVehicle.getStartParked());
        System.out.println(tempVehicle);
        System.out.println(tempCustomer);
        if (tempVehicle.getZone() == 0){
            System.out.println("\nThere is unfortunately no room for your car, we are terribly sorry for this inconvenience");
            customer.removeCustomer(tempCustomer);
            vehicle.removeVehicle(tempVehicle);
        } else{
            customer.printReceipt(tempCustomer, tempVehicle);
        }
        pressEnterToContinue();
    }

    private Vehicle findSpace(Vehicle secVehicle){
        Customer tempCustomer = customer.searchForCustomer(secVehicle.getReceiptNum());
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
        return secVehicle;
    }

    private void retrieveVehicle(){

            System.out.println("Please type in the number on your receipt");
            scan = new Scanner(System.in);
            int num = scan.nextInt();
            Customer tempCustomer = customer.searchForCustomer(num);
            Vehicle tempVehicle = vehicle.searchForVehicle(num);

            if(tempVehicle != null && tempCustomer != null){
                System.out.println(tempCustomer.toString());

                //The token is here represented as an int, but would in reality be an actual token
                System.out.println("You will now recieve a token, please enter it in the exit barrier in order to open the barrier");
                int token = generateToken();
                tempCustomer.setToken(token);
                System.out.println("Your token is: " + token);
                int zone = tempVehicle.getZone();
                int space = tempVehicle.getSpace();
                System.out.println("Your car is located in zone " + zone + " parking space " + space);
                customer.removeCustomer(tempCustomer);
                vehicle.removeVehicle(tempVehicle);
                pressEnterToContinue();
            } else{
                System.out.println(tempCustomer);
                System.out.println(tempVehicle);
                System.out.println("We are truly sorry, but we are unable to locate your vehicle");
                System.out.println("You might have mistyped, if so, press '3' to return re-enter your code");
                pressEnterToContinue();
            }

    }

    private int generateReceipt(){
        int num;

        Random random = new Random();
        //There is no system to check if the receipt number is in use, so this could be a problem i the future,
        //but as the receipt number is 5 digits, the chance of getting a duplicate number is a mere 0.45% ( (450/100`000)*100 = 0.45 )
        num = random.nextInt(89999) + 10000;
        System.out.println(num);
        return num;
    }

    private int generateToken(){
        int token;
        Random rand = new Random();

        // Obtain a number between 0 - 500
        token = rand.nextInt(500);
        token += 1;

        return token;
    }

    private void setPrice(){
        List<Zone> list = zone.getZones();
        double price;

        do{
            System.out.println("\n\n\n\n\n\n+ ------------------------------------------------ +");
            System.out.println("|  Enter '1' to change the hourly rate for zone 1  |");
            System.out.println("|  Enter '2' to change the hourly rate for zone 2  |");
            System.out.println("|  Enter '3' to change the hourly rate for zone 3  |");
            System.out.println("|  Enter '4' to change the hourly rate for zone 4  |");
            System.out.println("|  Enter '5' to change the hourly rate for zone 5  |");
            System.out.println("+ ------------------------------------------------ +\n");
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

    private void displayZone() {
        System.out.println("\n\n+ --------| DISPLAY ZONE |-------- +");
        System.out.println("|  Please enter your vehicle type  |");
        System.out.println("|     Standard:   '1'              |");
        System.out.println("|     High:       '2'              |");
        System.out.println("|     Long:       '3'              |");
        System.out.println("|     Coach:      '4'              |");
        System.out.println("|     Motorbike:  '5'              |");
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
            default:
                System.out.println("\n---ERROR---\nINVALID ZONE");
        }
        pressEnterToContinue();
    }

    private void pressEnterToContinue(){
        System.out.println("\nPress Enter to continue...");
        try {
            System.in.read();
        } catch(Exception e){}
    }

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

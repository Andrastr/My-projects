package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.IOException;
import java.util.*;

public class Application {
    private boolean quit = false;
    private Scanner scan;
    private Attendant attendant = new Attendant();
    private Customer customer = new Customer();
    private Vehicle vehicle = new Vehicle();
    private Vehicle secVehicle = new Vehicle();
    private Zone zone = new Zone();

    private void setUp() throws IOException{
        zone.load();
    }

    public static void main(String[] args) throws IOException{
        Application app = new Application();
        app.setUp();
        app.customerMenu();
        System.out.println("Free attendants: ");
        System.out.println(app.attendant.printFreeAttendants());
        System.out.println("Occupied attendants: ");
        System.out.println(app.attendant.printOccupiedAttendants());
        System.out.println("Registerd customers: ");
        System.out.println(app.customer.printCustomers());
        System.out.println("Registerd vehicles: ");
        System.out.println(app.vehicle.printVehicles());
        System.out.println("\n---Goodbye---");
        /*System.out.println(app.zone1.toString());
        System.out.println(app.zone2.toString());
        System.out.println(app.zone3.toString());
        System.out.println(app.zone4.toString());
        System.out.println(app.zone5.toString());*/
        app.zone.save();

    }

    private void printMenu(){
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("---Hello and welcome to Strand inc. parking house---");

        System.out.println("\nIf you want to call an attendant, please type in '1'");
        System.out.println("Please note that attendants can only park Standard, Higher and Longer vehicles,\nIf you own another type, please park it yourself");
        System.out.println("\nIf you want to park a new vehicle, please type in '2'");
        System.out.println("If you want to retrieve a parked vehicle, please type in '3'");
                            //In reality, this would not be an option on the terminal/app that the customers has access to
        System.out.println("If you are an employee, please type in in '4'");
        System.out.println("If you want to break the system, please type in '5'");
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
                    findSpace();
                    break;
                case "3":
                    retrieveVehicle(false);
                    break;
                case "4":
                    System.out.println("\n\n\n\n\n\n\n\n\n\n");
                    employeeMenu();
                    break;
                case"5":
                    //systemBreak();
                    System.out.println(zone.printZones());
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
            System.out.println("\n1. If you want to park a new vehicle, please type in '1'");
            System.out.println("2. If you want to retrieve a parked vehicle, please type in '2'");

            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch(response){
                case "1":
                    registerVehicle(true);
                    quit = true;
                    break;
                case "2":
                    retrieveVehicle(true);
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
            System.out.println("\n1. If you would like to register a new attendant, type in '1'");
            System.out.println("2. If you would like to get a list of all attendants, type in '2'");
            System.out.println("3. If you would like to change the price of a zone, type in '3'");
            System.out.println("4. If you would like to go back to the main menu, please type in '4'");

            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch(response){
                case "1":
                    newAttendant();
                    break;
                case "2":
                    System.out.println(attendant.printAttendants());
                    break;
                case "3":
                    setPrice();
                case "4":
                    quit = true;
                    break;
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
            attendantMenu();
        } else{
            System.out.println("\nThere is no free attendants at the moment, please register and park the car yourself.");
            System.out.println("\nWe are truly sorry for the inconvenience this may cause.");
            systemSleep(5000);
        }
    }

    private Customer newCustomer(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        Customer newCustomer = customer.newCustomer();
        customer.addCustomer(newCustomer);
        return newCustomer;
    }

    private Vehicle registerVehicle(boolean isAttendant){
        String response;
        String license;
        int num = generateReceipt();

        Customer tempCustomer = newCustomer();
        tempCustomer.setReceiptNum(num);

        Vehicle tempVehicle = new Vehicle(tempCustomer.getCustomerNum());
        vehicle.addVehicle(tempVehicle);
        tempVehicle.setReceiptNum(num);


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
        } else if(isAttendant){
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
        } else{
            System.out.println("What? How did you do this?");
        }

        System.out.println("\nPlease enter your license plate number");
        scan = new Scanner(System.in);
        license = scan.nextLine();
        tempVehicle.setLicense(license);
        vehicle.printVehicles();  //for debugging purposes
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

        secVehicle = tempVehicle;
        return secVehicle;
    }

    private void findSpace(){
        boolean[] z1Space = zone.getZ1();
        boolean[] z2Space = zone.getZ2();
        boolean[] z3Space = zone.getZ3();
        boolean[] z4Space = zone.getZ4();
        boolean[] z5Space = zone.getZ5();

        int i = 0;
        if(secVehicle.getType() == 1){
            for (boolean z: z4Space) {
                if(z != true){
                    System.out.println("Please follow the given instruction in order to find an empty space");
                    System.out.println("*instructions");
                    z4Space[i] = true;
                    vehicle.setZone(4);
                    vehicle.setSpace(i);
                    break;
                }
                i++;
            }
            if(i >= z1Space.length){
                i = 0;
                for (boolean z: z1Space) {
                    if(z != true){
                        System.out.println("Please follow the given instruction in order to find an empty space");
                        System.out.println("*insert instructions here*");
                        z1Space[i] = true;
                        vehicle.setZone(1);
                        vehicle.setSpace(++i);
                        break;
                    }
                    i++;
                }
                if(i >= z1Space.length){
                    System.out.println("There is unfortunately no room for your car, we are terribly sorry for this inconvenience");
                    customer.removeCustomer(customer);
                    vehicle.removeVehicle(vehicle);
                }
            }
        } else if (secVehicle.getType() == 2){
            for (boolean z: z1Space) {
                if(z != true){
                    System.out.println("Please follow the given instruction in order to find an empty space");
                    System.out.println("*instructions*");
                    z1Space[i] = true;
                    vehicle.setZone(1);
                    vehicle.setSpace(++i);
                    break;
                }
                i++;

            }
            if (i >= z1Space.length){
                System.out.println("There is unfortunately no room for your vehicle, we are terribly sorry for this inconvenience");
                customer.removeCustomer(customer);
                vehicle.removeVehicle(vehicle);
            }
        } else if (secVehicle.getType() == 3){
            for (boolean z: z2Space) {
                if (z != true){
                    System.out.println("Please follow the given instructions to find an empty space");
                    System.out.println("*instructions*");
                    z2Space[i] = true;
                    vehicle.setZone(2);
                    vehicle.setSpace(++i);
                    break;
                }
                i++;
            }
            if (i >= z2Space.length){
                System.out.println("There is unfortunately no room for your vehicle, we are terribly sorry for this inconvenience");
                customer.removeCustomer(customer);
                vehicle.removeVehicle(vehicle);
            }
        } else if (secVehicle.getType() == 4){
            for (boolean z: z3Space) {
                if(z != true){
                    System.out.println("Please follow the given instructions in order to find an empty space");
                    System.out.println("*instructions*");
                    z3Space[i] = true;
                    vehicle.setZone(3);
                    vehicle.setSpace(++i);
                    break;
                }
                i++;
            }
            if (i >= z3Space.length){
                System.out.println("There is unfortunately no room for your coach, we are terribly sorry for this inconvenience");
                customer.removeCustomer(customer);
                vehicle.removeVehicle(vehicle);
            }
        } else if (secVehicle.getType() == 5){
            for (boolean z: z5Space) {
                if(z != true){
                    System.out.println("\nPlease follow the given instructions in order to find an empty space");
                    System.out.println("*Drive to the top floor, turn right, and there is your new spot*");
                    z5Space[i] = true;
                    vehicle.setZone(5);
                    vehicle.setSpace(++i);
                    break;
                }
                i++;

            }
            if ( i >= z5Space.length){
                System.out.println("There is unfortunately no room for your motorbike, we are terribly sorry for this inconvenience");
                customer.removeCustomer(customer);
                vehicle.removeVehicle(vehicle);
            }
        } else{
            System.out.println("---ERROR---" +
                    "\n---Invalid type---");
        }
    }

    private void retrieveVehicle(boolean isAttendant){

            System.out.println("Please type in the number on your receipt");
            scan = new Scanner(System.in);
            int num = scan.nextInt();
            Customer tempCustomer = new Customer();
            tempCustomer = customer.searchForCustomer(num);
            Vehicle tempVehicle = vehicle.searchForVehicle(num);

            if(tempVehicle != null && tempCustomer != null){
                System.out.println(tempCustomer.toString());

                //The token is here represented as an int, but would in reality be an actual token
                System.out.println("You will now recieve a token, pleas enter it in the exit barrier in order to open the barrier");
                int token = generateToken();
                tempCustomer.setToken(token);
                System.out.println("Your token is: " + token);
                int zone = tempVehicle.getZone();
                int space = tempVehicle.getSpace();
                System.out.println("Your car is located in zone " + zone + " parking space " + space);
                customer.removeCustomer(tempCustomer);
                vehicle.removeVehicle(tempVehicle);

                if(isAttendant){
                    attendant.setToken(token);
                }

            } else{
                System.out.println("We are truly sorry, but we are unable to locate your vehicle");
            }

    }

    private void printVehicles(){
        for (Vehicle v:vehicle.vehicles) {
            System.out.println(v.toString());
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
            System.out.println("\n --------------------------------------------------");
            System.out.println("|  Enter '1' to change the hourly rate for zone 1  |");
            System.out.println("|  Enter '2' to change the hourly rate for zone 2  |");
            System.out.println("|  Enter '3' to change the hourly rate for zone 3  |");
            System.out.println("|  Enter '4' to change the hourly rate for zone 4  |");
            System.out.println("|  Enter '5' to change the hourly rate for zone 5  |");
            System.out.println(" --------------------------------------------------");
            scan = new Scanner(System.in);
            int zone = scan.nextInt();

            switch (zone){
                case 1:
                    System.out.println("Enter the hourly rate for zone 1");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    list.get(0).setPrice(price);
                    quit = true;
                    break;
                case 2:
                    System.out.println("Enter the hourly rate for zone 2");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    list.get(1).setPrice(price);
                    quit = true;
                    break;
                case 3:
                    System.out.println("Enter the hourly rate for zone 3");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    list.get(2).setPrice(price);
                    quit = true;
                    break;
                case 4:
                    System.out.println("Enter the hourly rate for zone 4");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    list.get(3).setPrice(price);
                    quit = true;
                    break;
                case 5:
                    System.out.println("Enter the hourly rate for zone 5");
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

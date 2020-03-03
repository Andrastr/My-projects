

package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

class Garage {
    private int attendantId;
    private Scanner scan;
    private boolean quit = true;
    private String format = "|   %1$-33s|\n";
    private Zone zone = new Zone();

    private ArrayList<Vehicle> vehicles = new ArrayList<>();                   //ArrayList keeping track of all parked vehicles
    private ArrayList<Customer> customers = new ArrayList<>();                //ArrayList keeping track of all current customers
    private ArrayList<Attendant> attendants = new ArrayList<>();             //ArrayList keeping track of every attendant
    private ArrayList<Attendant> freeAttendants = new ArrayList<>();        //ArrayList keeping track of every available attendants
    private ArrayList<Attendant> occupiedAttendants = new ArrayList<>();   //ArrayList keeping track of every occupied attendants
    private ArrayList<Zone> zones = new ArrayList<>();


    /**
     * Constructor for the class Garage
     */
    Garage(){}



    //--CUSTOMER METHODS BELOW--\\

    /**
     * Adds a new customer to the ArrayList 'customers'
     *
     * @param user The customer that will be added
     */
    private void addCustomer(Customer user) {
        customers.add(user);
    }

    /**
     * Removes a customer from the ArrayList 'customers'
     *
     * @param user The customer that will be removed
     */
    private void removeCustomer(Customer user){
        customers.remove(user);
    }

    /**
     * Generates an ID number for the customer
     * Starts counting from 1
     * The number 1 is never revisited,
     * easy to find customer 1 million
     * As 13 is an unlucky number, it will be skipped over
     *
     * @return Customer Id
     */
    private int generateOwnerId(){
        int i;
        i = customers.get(customers.size()-1).getOwnerId();
        i++;
        return i;
    }

    /**
     * Creates a new customer
     *
     * @return customer
     */
    private Customer newCustomer(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        Customer newCustomer = new Customer(generateOwnerId());
        addCustomer(newCustomer);
        return newCustomer;
    }

    /**
     * Generated the code used to locate the customers vehicle
     * A random number between 10000 and 99999 is picked,
     * and returned.
     * There is a chance of getting a duplicate number,
     * but it is very low
     *
     * @return receipt number
     */
    private int generateReceipt(){
        Random random = new Random();
        return random.nextInt(89999) + 10000;
    }

    /**
     * Prints a receipt for the customer,
     * Includes info about the vehicle
     * And teh receipt code, parking space
     *
     * @param tempC Current Customer
     * @param tempV Current Vehicle
     */
    private void printReceipt(Customer tempC, Vehicle tempV){
        String format = "|   %1$-30s|\n";

        System.out.println("\n\n\n+ ------------------------------- +");
        System.out.format(format, "User receipt");
        System.out.format(format, "Please keep this receipt");
        System.out.format(format, "Date         " + tempC.parkedString(1));
        System.out.format(format, "Time               " + tempC.parkedString(2));
        System.out.println("+ ------------------------------- +");
        System.out.format(format, "-User ID           |  " + tempC.getOwnerId());
        System.out.format(format, "-License plate     |  " + tempC.getLicensePlate());
        System.out.format(format, "-Disabled          |  " + tempC.getDisabled());
        System.out.format(format, "-Zone ID           |  " + tempV.getZone());
        System.out.format(format, "-Space ID          |  " + tempV.getSpace());
        System.out.println("+ ------------------------------- +");
        System.out.format(format, "RETRIEVAL CODE     |   " + tempC.getReceiptNum());
        System.out.println("+ ------------------------------- +");
    }

    /**
     * Sub-method for paymentAndToken()
     * Called upon when the customer is supposed to pay,
     *
     * @param token the token for the exit barrier, used in a print statement
     * @param cost the price the customer is supposed to pay
     */
    private void payment(int token, double cost){
        System.out.println("\n\n+ ---------------------------------- +");
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

            if (paid < 0) {
                System.out.format(format, "How did you pay a negative amount?");
                System.out.format(format, "Please pay in positive numbers");
            } else if(paid == cost){
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
        System.out.println("\n+ ---------------------------------- +");
        System.out.format(format, "You will no recieve a token");
        System.out.format(format, "Please type it into the screen");
        System.out.format(format, "by the exit barrier");
        System.out.format(format, "");
        System.out.format(format, String.format("Your token is: %d", token));
        System.out.println("+ ---------------------------------- +");
        pressEnterToContinue();
    }

    /**
     * Sub-method for paymentAndToken(),
     * User is told to type in the given token, which is compared to the one they got
     * If it is the same, they are let out
     * Unless they have spent over 15 minutes, in which case an attendant is called
     *
     * @param token the token for the exit barrier, typed in by ser, then compared
     * @param end the time the customer or attendant retrieved the vehicle
     */
    private void token(int token, long end){
        //Gives the customer the token and makes them type it in, if time used is over 15 minutes, an attendant is called


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
                    System.out.println("\n\n\n\n\n+ ---------------------------------- +");
                    System.out.format(format, "Thank you for using this garage,");
                    System.out.format(format, "Please come again");
                    System.out.println("+ ---------------------------------- +");
                    quit = true;
                } else{
                    System.out.println("\nInvalid token, please enter the one on your receipt");
                }
            } else{
                //notifies an attendant
                boolean help = callAttendant();
                if (help){
                    System.out.println("\n\n+ ---------------------------------- +");
                    System.out.format(format, "");
                    System.out.format(format, "You have spent over 15 minutes");
                    System.out.format(format, "and will no longer be able to");
                    System.out.format(format, "exit the building by yourself");
                    System.out.format(format, "An attendant has been notified,");
                    System.out.format(format, "and will help you shortly");
                    System.out.format(format, "");
                    System.out.println("\n\n+ ---------------------------------- +");
                } else{
                    System.out.println("\n\n+ ---------------------------------- +");
                    System.out.format(format, "");
                    System.out.format(format, "There is currently no available");
                    System.out.format(format, "attendants, but one will come");
                    System.out.format(format, "as soon as they are free, i order");
                    System.out.format(format, "to help you out");
                    System.out.format(format, "");
                    System.out.println("+ ---------------------------------- +");
                }
                quit = true;
            }
        } while (!quit);
        quit = false;
        pressEnterToContinue();
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
     * Prints the ArrayList 'customers'
     * primarily used for debugging
     */
    void printCustomers(){
        for (Customer c:customers){
            System.out.println(c.toString());
        }
    }



    //--VEHICLE METHODS BELOW--\\

    /**
     * Adds a vehicle to the ArrayList 'vehicles'
     *
     * @param vehicle The vehicle that will be added
     */
    private void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    /**
     * Removes a vehicle
     * @param vehicle The vehicle that will be removed
     */
    private void removeVehicle(Vehicle vehicle){
        vehicles.remove(vehicle);
    }

    /**
     * Method used for payment, in order to "exit the garage"
     *
     * Prints when customer started parking, and current time
     * Customer gets told how much to pay, then told to pay
     * After payment, customer gets back money if more is paid than needed,
     * then recieves a token for the exit barrier
     *
     * @param tempCustomer Current customer
     * @param tempVehicle  Current vehicle
     */
    private void paymentAndToken(Customer tempCustomer, Vehicle tempVehicle){
        long start = tempCustomer.getStartParked();
        long end = System.currentTimeMillis();
        int parkedZone = tempVehicle.getZone();
        double rate;
        Zone tempZone;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat current = new SimpleDateFormat("dd MMM yyyy | HH:mm");

        switch (parkedZone){
            case 1:
                tempZone = zones.get(0);
                break;
            case 2:
                tempZone = zones.get(1);
                break;
            case 3:
                tempZone = zones.get(2);
                break;
            case 4:
                tempZone = zones.get(3);
                break;
            default:
                tempZone = zones.get(4);
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
        System.out.format(format, "Parked:  " + current.format(tempCustomer.getStartParked()));
        System.out.format(format, "Current: " + current.format(cal.getTime()));
        System.out.format(format, "");
        System.out.format(format,("You have been parked for"));
        System.out.format(format, String.format("%d hours and %d minutes", (long)hrs, (long)min));

        if (min != 0){
            if(min/min != 0){
                hrs++;
            }
        }

        if (tempVehicle.getType() != 4){
            if(tempCustomer.getDisabled()){
                rate =rate/2;
                if(cal  .get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                    rate = 0;
                }
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

        int token = generateToken();
        payment(token, cost);
        token(token, end);

        //Customer and Vehicle is removed whether the time limit is reached or not, as an attendant would help the customer exit the garage
        removeCustomer(tempCustomer);
        removeVehicle(tempVehicle);

        emptySpace(tempVehicle);
    }

    private void vehicleHelpMenu(){
        System.out.println("\n+ ---------------------------------- +");
        System.out.format(format, "Help menu");
        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "Standard:");
        System.out.format(format, "Height: up to 2m");
        System.out.format(format, "Length: Up to 5m");
        System.out.format(format, "");
        System.out.format(format, "Higher:");
        System.out.format(format, "Height: Between 2 and 3m");
        System.out.format(format, "Length: Up to 5m");
        System.out.format(format, "");
        System.out.format(format, "Long:");
        System.out.format(format, "Height: Up to 3m");
        System.out.format(format, "Length: Between 5.1m and 6m");
        System.out.format(format, "");
        System.out.format(format, "Coach:");
        System.out.format(format, "Any height");
        System.out.format(format, "Length: Up to 15m");
        System.out.format(format, "");
        System.out.format(format, "Motorbike:");
        System.out.format(format, "Two wheels");
        System.out.println("+ ---------------------------------- +");
    }

    /**
     * Prints all  vehicle types, allowing the user to choose theirs
     * Also prints a help option if the customer is not sure which type they drive
     *
     * @param isAttendant if true, the user is an attendant, and it will print the attednant options only
     */
    private void vehicleMenu(boolean isAttendant){
        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "Welcome, what kind of vehicle");
        System.out.format(format, "will you be parking today?");
        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "1. Standard");
        System.out.format(format, "2. Higher");
        System.out.format(format, "3. Longer");
        if (!isAttendant){
            System.out.format(format, "4. Coach");
            System.out.format(format, "5. Motorbike");
            System.out.format(format, "");
            System.out.format(format, "6. Help");
        }
        System.out.println("+ ---------------------------------- +");
    }

    /**
     * Prints ArrayList 'vehicles'
     * Used for debugging
     */
    void printVehicles(){
        for (Vehicle v:vehicles){
            System.out.println(v.toString());
        }
    }

    /**
     * Allows the customer to register their vehicle at the garage
     * Adds vehicle and customer to respective ArrayLists
     *
     * Registers vehicles
     * Generates a Owner Id, and has the customer or attendant type in info
     * about the vehicle (type, license plate, disabled driver/passenger)
     *
     * Adds vehicle and customer to corresponding ArrayLists
     * Then calls method findSpace() to find a free parking space
     *
     * @param isAttendant If there is a free attendant, isAttendant == true
     */
    void registerVehicle(boolean isAttendant) throws InterruptedException{
        String response;
        int num = generateReceipt();

        Customer tempCustomer = newCustomer();
        tempCustomer.setReceiptNum(num);

        Vehicle tempVehicle = new Vehicle();
        tempVehicle.setOwnerId(tempCustomer.getOwnerId());
        tempVehicle.setReceiptNum(num);
        addVehicle(tempVehicle);

        tempVehicle.setStartParked(System.currentTimeMillis());
        tempCustomer.setStartParked(tempVehicle.getStartParked());

        if(!isAttendant){
            do{
                vehicleMenu(isAttendant);

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
                        vehicleHelpMenu();
                        pressEnterToContinue();
                        break;
                    default:
                        System.out.println("\n\n\n\n\nInvalid input\n");
                        Thread.sleep(2000);
                        quit = false;
                }
            } while (!quit);
            quit = false;
        } else{
            do{
                vehicleMenu(isAttendant);

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
                        Thread.sleep(2000);
                }
            } while (!quit);
            quit = false;
        }

        registerPlate(tempVehicle, tempCustomer);
        System.out.format(format, "");
        isDisabled(tempCustomer);
        findSpace(tempVehicle);

        if (tempVehicle.getZone() == 0){
            System.out.format(format, "There is unfortunately");
            System.out.format(format, "no room for your car");
            System.out.format(format, "We apologise for this");
            System.out.format(format, "inconvenience");
            removeCustomer(tempCustomer);
            removeVehicle(tempVehicle);
        } else{
            System.out.println("\nPrinting receipt");
            printReceipt(tempCustomer, tempVehicle);
        }
         //Debugging
        //System.out.println("\n\n" + tempVehicle.toString() + "\n" + tempCustomer.toString());
        pressEnterToContinue();
    }

    /**
     * SubMethod for registerVehicle(),
     * Is called on in order to register the license plate of the new vehicle
     *
     * @param tempVehicle New vehicle
     * @param tempCustomer New Customer
     * @throws InterruptedException For the thread to sleep 2000ms
     */
    private void registerPlate(Vehicle tempVehicle, Customer tempCustomer) throws InterruptedException{
        String license;

        System.out.println("+ ---------------------------------- +");
        System.out.println("| Please register your license plate |");
        scan = new Scanner(System.in);
        license = scan.nextLine();
        tempVehicle.setLicensePlate(license);
        tempCustomer.setLicensePlate(license);
        //tempVehicle.toString();  //for debugging purposes
        do{
            //Here the customer would be given two boxes to click, yes and no
            System.out.format(format, "Your license plate is: " + license);
            System.out.format(format, "Is this correct?   y/n");

            scan = new Scanner(System.in);
            String response = scan.nextLine().toUpperCase();
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
                    tempVehicle.setLicensePlate(license);
                    break;
                default:
                    System.out.println("Invalid input\n");
                    Thread.sleep(2000);
            }
        } while (!quit);
        quit = false;
    }

    /**
     * Sub-method for registerVehicle(),
     * Is called on to set whether the customer is disabled or not
     *
     * @param tempCustomer New customer
     * @throws InterruptedException for the thread to sleep
     */
    private void isDisabled(Customer tempCustomer) throws InterruptedException{
        do{
            System.out.format(format, "Are you driving a handicap");
            System.out.format(format, "approved vehicle?  y/n");
            scan = new Scanner(System.in);
            String response = scan.nextLine().toUpperCase();
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
                    Thread.sleep(2000);
            }
        } while(!quit);
        quit = false;
    }

    /**
     * Has users type in the ID code on their receipt,
     * then looks through Arraylists customers and vehicles to find both
     *
     * Then calls paymentAndToken()
     *
     * If the customer has lost their code, or has forgotten it,
     * an attendant is called and the customer is taken back to the main menu
     */
    void retrieveVehicle() throws InterruptedException {
        try{
            do {

                System.out.println("\n\n+ ---------------------------------- +");
                System.out.format(format, "Please type in the number");
                System.out.format(format, "on your receipt");
                System.out.format(format, "If you have lost your receipt");
                System.out.format(format, "type in '0' to call for help");
                System.out.println("+ ---------------------------------- +");

                scan = new Scanner(System.in);
                int num = scan.nextInt();
                if (num == 0) {
                    System.out.println("\n\n+ ---------------------------------- +");
                    System.out.format(format, "An attendant has been alerted");
                    System.out.format(format, "and is on their way");
                    System.out.println("+ ---------------------------------- +");
                    pressEnterToContinue();
                    quit = true;
                } else {
                    Customer tempCustomer = searchForCustomer(num);
                    Vehicle tempVehicle = searchForVehicle(num);

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
            } while (!quit);
            quit = false;
        } catch (InputMismatchException e){
            System.err.println("InputMismatchError: Input must be Integer");
            System.out.println("\n Returning to main menu");
            Thread.sleep(2000);
        }
    }



    //--ATTENDANT METHODS BELOW--\\

    /**
     * Changes all attendants to either free or occupied
     * Removes from and adds all attendants to corresponding ArrayLists
     *
     * @param num Either '1' or '2', if '1', all attendants are set to 'free', else they are set to 'occupied'
     */
    void changeAttendants(int num){
        freeAttendants.clear();
        occupiedAttendants.clear();

        if (num == 1){
            for (Attendant a:attendants){
                a.setIsFree(true);
                freeAttendants = attendants;
            }
        } else {
            for (Attendant a:attendants){
                a.setIsFree(false);
                occupiedAttendants = attendants;
            }
        }
        System.out.println("+ ---- +");
        System.out.println("| DONE |");
        System.out.println("+ ---- +");
    }

    /**
     * Changes the salary of an attendant
     *
     * User writes in the full name of the attendant,
     * and the ArrayList is looked through to find the attendant
     *
     * If attendant is not found, an error message will appear,
     * and user wil be asked to input name again
     *
     * The user then writes in a double in order to set the salary for the attendant
     */
    void editAttendant(int num){
        boolean menu = false;
        Attendant tempAttendant = new Attendant();
        scan = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n");

        System.out.println("\n+ ---------------------------------- +");
        System.out.format(format, "Please enter the full");
        System.out.format(format, "name of the attendant");

        do{
            //System.out.println("test");
            String name = scan.nextLine().toUpperCase();

            if(name.equals("EXIT")){
                quit = true;
                menu = true;
            } else {
                tempAttendant = searchForAttendant(name);

                if (tempAttendant != null){
                    System.out.format(format, "Is this the correct attendant?");
                    System.out.format(format, "y/n");
                    System.out.format(format, tempAttendant.toString());
                    String response = scan.nextLine().toUpperCase();

                    switch (response){
                        case "Y":
                            quit = true;
                            break;
                        case "N":
                            System.out.format(format, "Please re-enter the attendants ");
                            System.out.format(format, "name, or to exit to main menu,");
                            System.out.format(format, "please type in exit");
                            quit = false;
                            break;
                        case "EXIT":
                            quit = true;
                            menu = true;
                            break;
                        default:
                            System.out.println("\n\n-------ERROR------\n---INVALID INPUT---");
                    }
                } else {
                    System.out.println("\n\n+ ---------------------------------- +");
                    System.out.format(format, "Sorry, the attendant");
                    System.out.format(format, "was not found");
                    System.out.format(format, "It could be a typo, or the ");
                    System.out.format(format, "attendant could no longer");
                    System.out.format(format, "be in the system");
                    System.out.format(format, "");
                    System.out.format(format, "Please re-enter the attendants ");
                    System.out.format(format, "name, or 'exit' to return to");
                    System.out.format(format, "the main menu");
                    System.out.println("+ ---------------------------------- +");
                    quit = false;
                }
            }
        } while (!quit);
        quit = false;

        if (tempAttendant!= null && !menu){
            if (num == 1) removeAttendant(tempAttendant);
            if (num == 2) changeSalary(tempAttendant);
        }
    }


    /**
     * Removes an attendant from all ArrayLists
     * @param tempAttendant The attendant that will be removed
     */
    private void removeAttendant(Attendant tempAttendant){
        String name = tempAttendant.getName();
        boolean occupied = tempAttendant.getIsFree();
        System.out.println(name);

        for (Attendant a:attendants){
            if (a.getName().equals(name)) {
                attendants.remove(a);
                break;
            }
        }

        if (!occupied){
            for (Attendant o:occupiedAttendants){
                if (o.getName().equals(name)){
                    occupiedAttendants.remove(o);
                    break;
                }
            }
        } else {
            for (Attendant f:freeAttendants){
                if (f.getName().equals(name)){
                    freeAttendants.remove(f);
                    break;
                }
            }
        }
    }

    /**
     * Changes the salary of an attendant
     *
     * @param tempAttendant the affected attendant
     */
    private void changeSalary(Attendant tempAttendant){
        System.out.format(format, "Please type in the attendants");
        System.out.format(format, "new salary");
        System.out.println("+ ---------------------------------- +");
        double newSalary = scan.nextDouble();
        tempAttendant.setSalary(newSalary);
    }

    /**
     * Gets a random number spanning the length of the given ArrayList
     * Returns the attendant in that position
     *
     * @param list ArrayList
     * @return attendant
     */
    private Attendant getRandomAttendant(ArrayList<Attendant> list) {
        Attendant attendant;
        Random rand = new Random();
        attendant = attendants.get(rand.nextInt(list.size()));
        return attendant;
    }

    /**
     * Checks if freeAttendants is empty
     * If it is empty, it returns true
     *
     * @return boolean
     */
    private boolean isFreeEmpty(){
        return freeAttendants.isEmpty();
    }

    void printAttendants(int num){
        if (num == 1){
               //Prints ArrayList "freeAttendants"
            for (Attendant a:freeAttendants){
                System.out.println(a.toString());
            }
        } else if (num == 2){
                //Prints ArrayList "occupiedAttendants"
            for (Attendant a:occupiedAttendants){
                System.out.println(a.toString());
            }
        } else {
                //Prints ArrayList "attendants"
            for (Attendant a:attendants){
                System.out.println(a.toString());
            }
        }
    }

    /**
     * Has the user type in a name,
     * Increases attendantId by 1
     * creates a new attendant with that name
     */
    void newAttendant(){
        System.out.println("Please type in the attendants name below");
        scan = new Scanner(System.in);
        String name = scan.nextLine().toUpperCase();
        System.out.println("Please type in the attendants salary below (eg. 1.5)");
        double salary = scan.nextDouble();
        attendantId++;

        Attendant attendant = new Attendant(attendantId, name, true, salary);
        attendants.add(attendant);
        freeAttendants.add(attendant);
    }

    /**
     * Method for calling an attendant
     *
     * Checks if there is any free attendants, and returns their name,
     * then adds the attendant to occupied Attendants ArrayList
     *
     */
    boolean callAttendant() {
        if(!isFreeEmpty()){
            Attendant randAttendant;
            randAttendant = getRandomAttendant(freeAttendants);
            randAttendant.setIsFree(false);
            System.out.println("\n\n+ ---------------------------------- +");
            System.out.format(format, "Your new attendant is " + randAttendant.getName());
            System.out.println("+ ---------------------------------- +");
            return true;
        } else{
            System.out.println("\n\n+ ---------------------------------- +");
            System.out.format(format, "There are no free attendants");
            System.out.format(format, "Please park the car yourself");
            System.out.format(format, "We are sorry for this");
            System.out.format(format, "inconvenience");
            System.out.println("+ ---------------------------------- +");
            pressEnterToContinue();
            return false;
        }
    }



    //--ZONE METHODS BELOW--\\

    /**
     * As a vehicle leaves the garage, the parking space is set to free (false) so a new car can park there
     *
     * @param tempVehicle the given vehicle
     */
    private void emptySpace(Vehicle tempVehicle){
        int zoneNum = tempVehicle.getZone();
        int i = tempVehicle.getSpace();

        switch (zoneNum){
            case 1:
                zone.z1Space[i] = false;
                break;
            case 2:
                zone.z2Space[i] = false;
                break;
            case 3:
                zone.z3Space[i] = false;
                break;
            case 4:
                zone.z4Space[i] = false;
                break;
            case 5:
                zone.z5Space[i] = false;
                break;
            default:
                System.out.println("---ERROR----\nINVALID ZONE");
        }
    }

    /**
     * Depending on vehicle type, it will look through the corresponding
     * zone array, looking for a free parking space, if one is found,
     * the parking space is added to the vehicle, and vehicle returned
     * Otherwise, vehicle is turned away, and customer/vehicle is
     * removed from their ArrayLists
     *
     * @param secVehicle Vehicle wiythout parking spaces
     */
    private void findSpace(Vehicle secVehicle){
        int i = 0;

        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "Please follow the given");
        System.out.format(format, "instructions in order to");
        System.out.format(format, "find an empty space:");
        System.out.format(format, "");

        switch  (secVehicle.getType()){                  //Looks through the corresponding array list,
            case 1:                                     //if a space is empty, it shows a 'false' in the corresponding array
                for (boolean z: zone.z4Space) {
                    if(!z){
                        System.out.format(format, "'instructions'");
                        zone.z4Space[i] = true;       //If the vehicle is of type 1 (Standard vehicle)
                        secVehicle.setZone(4);       //It will look for an empty space in Zone 4 (array: z4Space)
                        secVehicle.setSpace(++i);   //if Zone 4 is full, it will look through Zone 1, which it shares with Higher vehicles
                        break;
                    }
                    i++;
                }
                if(i >= zone.z1Space.length){   //If zone 4 is full, it start looking through zone 1 (array: z1Space
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
                break;
            case 2:                                   //If the vehicle is of type 2 (Higher vehicle),
                for (boolean z: zone.z1Space) {      //It will look for an empty space in Zone 1 (array: z2Space)
                    if(!z){                         //The customer is turned away
                        System.out.format(format, "*instructions*");
                        zone.z1Space[i] = true;
                        secVehicle.setZone(1);
                        secVehicle.setSpace(++i);
                        break;
                    }
                    i++;

                }
                break;
            case 3:                                   //If the vehicle is of type 3 (Longer vehicle),
                for (boolean z: zone.z2Space) {      //It will look for an empty space in Zone 2 (array: z2Space)
                    if (!z){                        //If none is found, the customer is turned away
                        System.out.format(format, "*instructions*");
                        zone.z2Space[i] = true;
                        secVehicle.setZone(2);
                        secVehicle.setSpace(++i);
                        break;
                    }
                    i++;
                }
                break;
            case 4:                                   //If the vehicle is of type 4 (Coach),
                for (boolean z: zone.z3Space) {      //It will look for an empty space in Zone 3 (array: z3Space)
                    if(!z){                         //If none is found, the customer is turned away
                        System.out.format(format, "*instructions*");
                        zone.z3Space[i] = true;
                        secVehicle.setZone(3);
                        secVehicle.setSpace(++i);
                        break;
                    }
                    i++;
                }
                break;
            case 5:                                   //If teh vehicle is of type 5 (Motorbike),
                for (boolean z: zone.z5Space) {      //It will look for an empty space in zone 5 (array: z5Space)
                    if(!z){                         //If none is found, the customer is turned away
                        System.out.format(format, "*instructions*");
                        zone.z5Space[i] = true;
                        secVehicle.setZone(5);
                        secVehicle.setSpace(++i);
                        break;
                    }
                    i++;

                }
                break;
            default:
                System.out.println("------ERROR------\n---Invalid type---");
        }

        System.out.println("+ ---------------------------------- +");
        pressEnterToContinue();
    }

    /**
     * Allows the manager to change the hourly rates for different zones
     */
    void changePrices(){
        Scanner scan;
        double price;
        boolean quit = false;

        do{
            System.out.println("\n\n\n\n\n\n+ ---------------------------------- +");
            System.out.format(format, "   Select the zone to change");
            System.out.format(format, "");
            System.out.format(format, "         1. Zone 1");
            System.out.format(format, "         2. Zone 2");
            System.out.format(format, "         3. Zone 3");
            System.out.format(format, "         4. Zone 4");
            System.out.format(format, "         5. Zone 5");
            System.out.format(format, "");
            System.out.println("+ ---------------------------------- +");

            scan = new Scanner(System.in);
            int zone = scan.nextInt();
            System.out.println("\n\n+ ---------------------------------- +");
            switch (zone){
                case 1:
                    System.out.format(format, "Enter the hourly rate for zone 1");
                    System.out.format(format, "");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    zones.get(0).setPrice(price);
                    quit = true;
                    break;
                case 2:
                    System.out.format(format, "Enter the hourly rate for zone 2");
                    System.out.format(format, "");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    zones.get(1).setPrice(price);
                    quit = true;
                    break;
                case 3:
                    System.out.format(format, "Enter the hourly rate for zone 3");
                    System.out.format(format, "");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    zones.get(2).setPrice(price);
                    quit = true;
                    break;
                case 4:
                    System.out.format(format, "Enter the hourly rate for zone 4");
                    System.out.format(format, "");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    zones.get(3).setPrice(price);
                    quit = true;
                    break;
                case 5:
                    System.out.format(format, "Enter the hourly rate for zone 5");
                    System.out.format(format, "");
                    scan = new Scanner(System.in);
                    price = scan.nextDouble();
                    zones.get(4).setPrice(price);
                    quit = true;
                    break;
                default:
                    System.out.println("\n\n\n\n\nInvalid input, try again\n");
                    break;
            }
            System.out.println("+ ---------------------------------- +");
        } while(!quit);
    }

    /**
     * Allows customer to display all zones parking spaces
     * Calls Zone.displayZone(param)
     */
    void displayZoneMenu() {
        boolean quit = false;
        String format = "|   %1$-33s|\n";

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

        do{
            Scanner scan;
            scan = new Scanner(System.in);
            String i = scan.nextLine();
            switch (i) {
                case "1":
                    displayZone(1);
                    displayZone(4);
                    quit = true;
                    break;
                case "2":
                    displayZone(1);
                    quit = true;
                    break;
                case "3":
                    displayZone(2);
                    quit = true;
                    break;
                case "4":
                    displayZone(3);
                    quit = true;
                    break;
                case "5":
                    displayZone(5);
                    quit = true;
                    break;
                case "6":
                    displayZone(1);
                    displayZone(2);
                    displayZone(3);
                    displayZone(4);
                    displayZone(5);
                    quit = true;
                    break;
                default:
                    System.out.println("\n---ERROR---\nINVALID ZONE");
            }
        } while (!quit);
    }

    /**
     * Self explanatory,
     * prints the zones ArrayList
     */
    void printZones(){
        for(Zone z:zones){
            System.out.println(z.toString());
        }
    }

    /**
     * Prints the parking spaces as a String of connected booleans
     * Mainly used for debugging
     *
     */
    void printZoneBooleans(){
        System.out.println(zone.printZoneBoolean(zone.z1Space));
        System.out.println(zone.printZoneBoolean(zone.z2Space));
        System.out.println(zone.printZoneBoolean(zone.z3Space));
        System.out.println(zone.printZoneBoolean(zone.z4Space));
        System.out.println(zone.printZoneBoolean(zone.z5Space));
    }

    /**
     * Formats and prints the zone parking spaces in a nice and compact way
     *
     * eg.
     * + ---------| Zone  3 |--------- +
     * | taken |  free | taken |  free |
     * + ----------------------------- +
     * There is 2 free spaces in Zone 3
     *
     *
     * @param zoneNum The zone that is to be displayed
     */
    void displayZone(int zoneNum){

        StringBuilder sb = new StringBuilder("\n");
        int i;
        int freeSpace=0;
        boolean[] list;

        switch (zoneNum){
            case 1:
                sb.append("\n+ -------------| Zone  1 |------------- +\n");
                list = zone.z1Space.clone();
                break;
            case 2:
                sb.append("\n+ -----| Zone  2 |----- +\n");
                list = zone.z2Space.clone();
                break;
            case 3:
                sb.append("\n+ ---------| Zone  3 |--------- +\n");
                list = zone.z3Space.clone();
                break;
            case 4:
                sb.append("\n+ -------------| Zone  4 |------------- +\n");
                list = zone.z4Space.clone();
                break;
            case 5:
                sb.append("\n+ ---------| Zone  5 |--------- +\n");
                list = zone.z5Space.clone();
                break;
            default:
                list = zone.z1Space.clone();
        }

        for (i=0;i<(list.length);i++){
            sb.append("|");
            if (!list[i]){
                sb.append("  free ");
                freeSpace++;
            } else{
                sb.append(" taken ");
            }
            if (zoneNum != 3) {
                if(i==(list.length/2-1)){
                    sb.append("|\n");
                }
            }
        }
        sb.append("|");
        if(zoneNum == 1 || zoneNum == 4){
            sb.append("\n+ ------------------------------------- +");
        } else if(zoneNum == 2){
            sb.append("\n+ --------------------- +");
        } else if(zoneNum == 3 || zoneNum == 5){
            sb.append("\n+ ----------------------------- +");
        }
        System.out.println(sb.toString());
        System.out.println("\nThere is " + freeSpace + " free spaces in Zone " + zoneNum);




        //Previous attempt at printing free zones, before I realised a StringBuilder would be more efficient
        /*if (i==1){
            System.out.println("| " + z1Space[0] + " | " + z1Space[1] + " | " + z1Space[2] + " | " + z1Space[3] + " | " + z1Space[4] + " |");
            System.out.println("| ------------------------------------- |");
            System.out.println("| " + z1Space[5] + " | " + z1Space[6] + " | " + z1Space[7] + " | " + z1Space[8] + " | " + z1Space[9] + " |");
        }*/
    }



    //COMMON METHODS BELOW--\\

    /**
     * Stops the program until the enter key is pressed
     *
     */
    private void pressEnterToContinue(){
        System.out.println("\nPress enter to continue");
        String enter;
        do{
            enter = scan.nextLine();
        } while (enter == null);
    }

    /**
     * Looks through ArrayList customers for required customer
     * If the customer is not found, it will return null
     *
     * @param receiptNum number on receipt recieved earlier
     * @return correct customer, or if not found, null
     */
    private Customer searchForCustomer(int receiptNum){
        Customer result = null;
        for(Customer c: customers){
            if(c.getReceiptNum() == receiptNum){
                result = c;
                break;
            }
        }
        return result;
    }

    /**
     * Looks through ArrayList vehicles for required vhicle
     * If the vehicle is not found, it will return null
     *
     * @param number number on receipt recieved earlier
     * @return correct vehicle, or if not found, null
     */
    private Vehicle searchForVehicle(int number) {
        Vehicle result = null;
        for(Vehicle v: vehicles){
            if(v.getReceiptNum() == number){
                result = v;
                break;
            }
        }
        return result;
    }

    /**
     * Look through ArrayList 'attendants' for the required attendant
     * If the attendant is not found, it will return null
     *
     * @param name name of the attendant
     * @return the attendant with teh corresponding name
     */
    private Attendant searchForAttendant(String name){
        Attendant result = null;
        for(Attendant a:attendants){
            if(a.getName().equals(name)){
                result = a;
                break;
            }
        }
        return result;
    }

    /**
     * Loads from file "customers.txt"
     *
     * @throws IOException if there is a mismatch in input
     */
    private void loadCustomers() throws IOException {
        FileReader fileInput = new FileReader("customer.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        try{
            while(infile.hasNext()) {
                int id = infile.nextInt();
                String license = infile.next();
                int receipt = infile.nextInt();
                long parked = infile.nextLong();
                boolean disabled = infile.nextBoolean();

                Customer c = new Customer(id,license,receipt,parked,disabled);
                customers.add(c);
            }
        } catch (InputMismatchException e){
            System.err.println("An unexpected error has occurred while loading file: customer.txt");
        }
    }

    /**
     * Loads from file "vehicles.txt"
     *
     * @throws IOException if there is a mismatch in input
     */
    private void loadVehicles() throws IOException {
        FileReader fileInput = new FileReader("vehicle.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        try {
            while(infile.hasNext()) {
                int type = infile.nextInt();
                int id  = infile.nextInt();
                int receipt = infile.nextInt();
                String license = infile.next();
                int zone = infile.nextInt();
                int space = infile.nextInt();
                long parked = infile.nextLong();

                Vehicle v = new Vehicle(type,zone,space);
                v.setOwnerId(id);
                v.setReceiptNum(receipt);
                v.setLicensePlate(license);
                v.setStartParked(parked);
                vehicles.add(v);
            }
        } catch (InputMismatchException e){
            System.err.println("An unexpected error has occurred while loading file: vehicle.txt");
        }
    }

    /**
     * Loads from file "attendant.txt"
     *
     * @throws IOException if there is a mismatch in input
     */
    private void loadAttendants() throws IOException {
        FileReader fileInput = new FileReader("attendant.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        try {
            while(infile.hasNext()) {
                int id = infile.nextInt();
                String name = infile.next();
                boolean free = infile.nextBoolean();
                double salary = infile.nextDouble();

                Attendant a = new Attendant(id,name,free,salary);
                attendants.add(a);
                if(free){
                    freeAttendants.add(a);
                } else{
                    occupiedAttendants.add(a);
                }
                attendantId = id;
            }
        } catch (InputMismatchException e){
            System.err.println("An unexpected error has occurred while loading file: attendant.txt");
        }
    }

    /**
     * Loads from file "price.txt"
     *
     * @throws IOException if there is a mismatch in input
     */
    private void loadZone() throws IOException {
        FileReader fileInput = new FileReader("price.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        try {
            while(infile.hasNext()) {
                String name = infile.next();
                boolean outside = infile.nextBoolean();
                double price = infile.nextDouble();

                Zone z = new Zone(name,outside,price);
                zones.add(z);
            }
        } catch (InputMismatchException e){
            System.err.println("An unexpected error has occurred while loading file: price.txt");
        }
    }

    /**
     * Writes data from mamory to given file
     *
     * @param fileName Name of the file it writes to
     * @throws IOException  if it is unable to find the file
     */
    private void save(String fileName) throws IOException {
        try (FileWriter fileOutPut = new FileWriter(fileName);
        PrintWriter outfile = new PrintWriter(fileOutPut)){

            switch (fileName){
                case "customer.txt":
                    for (Customer c:customers) {
                        outfile.println( c.getOwnerId() + ":" + c.getLicensePlate() + ":" + c.getReceiptNum() + ":" + c.getStartParked() + ":" + c.getDisabled());
                    }
                    break;
                case "vehicle.txt":
                    for (Vehicle v : vehicles) {
                        outfile.println(v.getType() + ":" + v.getOwnerId() + ":" + v.getReceiptNum() + ":" + v.getLicensePlate() + ":" + v.getZone() + ":" + v.getSpace() + ":" + v.getStartParked());
                    }
                    break;
                case "attendant.txt":
                    for (Attendant a:attendants) {
                        outfile.println( a.getAttendantId() + ":" + a.getName() + ":" + a.getIsFree() + ":" + a.getSalary());
                    }
                    break;
                case "price.txt":
                    for (Zone z:zones) {
                        outfile.println(z.getName() + ":" + z.getOutside() + ":" + z.getPrice());
                    }
                    break;
                case "parkingSpaces.txt":
                    zone.saveParkingSpaces();
                    break;
                default:
                    System.out.println("Unable to find file");
            }
        }
    }

    /**
     * Method for calling the load method for all files
     *
     * @throws IOException if there is a mismatch in input
     */
    void setUp() throws IOException {
            loadZone();
            zone.loadParkingSpaces();
            loadAttendants();
            loadCustomers();
            loadVehicles();

    }

    /**
     * Method calling save() for all files
     */
    void shutDown() {
        try{
            save("price.txt");
            save("parkingSpaces.txt");
            save("attendant.txt");
            save("customer.txt");
            save("vehicle.txt");
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: ");
        }


    }

}

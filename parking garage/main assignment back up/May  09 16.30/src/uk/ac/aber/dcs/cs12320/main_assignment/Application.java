package uk.ac.aber.dcs.cs12320.main_assignment;
  //Running with command line, from project folder:
 //cd "Dokumenter\Skole\Uni\CS12320\Main Assignment\Main-Assignment\
//java -cp out/production/Main-Assignment uk.ac.aber.dcs.cs12320.main_assignment.Application

import java.io.IOException;
import java.util.*;

public class Application {
    private boolean quit = false;
    private Scanner scan;
    private Garage garage = new Garage();
    private String format = "|   %1$-33s|\n";

    /**
     * Loads data from the files
     * Loads from:
     * attendant.txt
     * vehicle.txt
     * customer.txt
     * zone.txt
     * price.txt
     * @throws IOException
     */
    private void setUp() throws IOException{
        garage.setUp();
    }

    /**
     * Saves data to the files
     * Saves from:
     * attendant.txt
     * vehicle.txt
     * customer.txt
     * zone.txt
     * price.txt
     * @throws IOException
     */
    private void shutDown(){
        System.out.println("\n\n---| Saving data |---");
        garage.shutDown();
        System.out.println("\n-----| Goodbye |-----");
    }

    public static void main(String[] args) throws IOException, InterruptedException{
        Application app = new Application();
        app.setUp();
        app.userMenu();
        app.shutDown();
    }

    /**
     * Prints the main menu for Users
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
             //for option 1, the customer would in reality drop off their vehicle by the front door as specified in the problem statement
        System.out.format(format, "1. Call an attendant");
        System.out.format(format, "2. Park a vehicle");
        System.out.format(format, "3. Retrieve a parked vehicle");
        System.out.format(format, "4. Display a garage zone");
            //In reality, this would not be an option on the terminal/app that the customers has access to
        System.out.format(format, "5. Open the admin menu");
             //Prints all the ArrayLists
            //Used for debugging
        System.out.format(format, "6. Print uncommented prints");
        System.out.format(format, "");
        System.out.println("+ ---------------------------------- +");
    }

    /**
     * Prints the admin menu
     */
    private void printAdminMenu(){
        System.out.println("\n\n\n+ ---------------------------------- +");
        System.out.format(format, "Please select a option below");
        System.out.format(format, "");
        System.out.format(format, "0. Register a new attendant");
        System.out.format(format, "1. Remove an attendant");
        System.out.format(format, "2. Change an attendants salary");
        System.out.format(format, "3. Set all attendants to free");
        System.out.format(format, "4. Set all attendants to occupied");
        System.out.format(format, "5. Get a list of attendants");
        System.out.println("+ ---------------------------------- +");
        System.out.format(format, "6. Change the rate of a zone");
        System.out.format(format, "7. Print info about the zones");
        System.out.format(format, "8. Display the garage zones");
        System.out.format(format, "9. Go back to the main menu");
        System.out.println("+ ---------------------------------- +");
    }

    /**
     * Menu for customers
     */
    private void userMenu() throws InterruptedException {
        String response;

        do {
            printMenu();
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    boolean help = garage.callAttendant();
                    if (help) attendantMenu();
                    break;
                case "2":
                    garage.registerVehicle(false);
                    break;
                case "3":
                    garage.retrieveVehicle();
                    break;
                case "4":
                    garage.displayZoneMenu();
                    pressEnterToContinue();
                    break;
                case"5":
                    System.out.println("\n\n\n\n\n\n\n\n\n\n");
                    adminMenu();
                    break;
                case"6":
                    /*System.out.println("Zones:");
                    garage.printZones();
                    System.out.println("\nAttendants");
                    garage.printAttendants(1);
                    garage.printAttendants(2);
                    System.out.println("\nCustomers: ");
                    garage.printCustomers();
                    System.out.println("\nVehicles: ");
                    garage.printVehicles();
                    System.out.println("\nZones: ");
                    garage.printZoneBooleans();
                    pressEnterToContinue();*/
                    break;
                // there is no mention in the menu about "Q", as the customer is not supposed to close the program
                case "Q":
                    quit = true;
                    break;
                default:
                    System.out.println("\n\n\n\n\nInvalid input, try again\n");
                    Thread.sleep(2000);
            }
        } while (!quit);
        quit = false;
    }

    /**
     * Menu for attendants parking a vehicle
     */
    private void attendantMenu() throws InterruptedException {
        String response;

        do{
            System.out.println("\n+ ---------------------------------- +");
            System.out.format(format, "");
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
                    garage.registerVehicle(true);
                    quit = true;
                    break;
                case "2":
                    garage.retrieveVehicle();
                    quit = true;
                    break;
                case"3":
                    garage.displayZone(1);
                    garage.displayZone(2);
                    garage.displayZone(3);
                    garage.displayZone(4);
                    garage.displayZone(5);
                    pressEnterToContinue();
                default:
                    System.out.println("\n\n\n\n\nInvalid input, try again\n");
            }
        } while(!quit);
        quit = false;
    }

    /**
     * Menu for the manager of the garage
     *
     */
    private void adminMenu(){
        String response;

        do{
            printAdminMenu();
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch(response){
                case "0":
                    garage.newAttendant();
                    break;
                case "1":
                    garage.editAttendant(1);
                    break;
                case "2":
                    garage.editAttendant(2);
                    break;
                case "3":
                    garage.changeAttendants(1);
                    break;
                case "4":
                    garage.changeAttendants(2);
                    break;
                case "5":
                    System.out.println("\n\n---Free Attendants---");
                    garage.printAttendants(1);
                    System.out.println("\n---Occupied Attendants---");
                    garage.printAttendants(2);
                    pressEnterToContinue();
                    break;
                case "6":
                    garage.changePrices();
                    break;
                case "7":
                    garage.printZones();
                    pressEnterToContinue();
                    break;
                case "8":
                    for (int i = 1; i<6; i++){
                        garage.displayZone(i);
                    }
                    pressEnterToContinue();
                    break;
                case "9":
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
     * Stops the program until the enter key is hit
     */
    private void pressEnterToContinue(){
        System.out.println("\nPress enter to continue");
        String enter;
        do{
            enter = scan.nextLine();
        } while (enter == null);
    }

}

package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.*;

public class Attendant {
    private String name;
    private int attendantId;    //Starts counting from 1
    private boolean isFree;
    private double salary;

    private ArrayList<Attendant> attendants = new ArrayList<>();             //ArrayList keeping track of every attendant
    private ArrayList<Attendant> freeAttendants = new ArrayList<>();        //ArrayList keeping track of every available attendants
    private ArrayList<Attendant> occupiedAttendants = new ArrayList<>();   //ArrayList keeping track of every occupied attendants

    Attendant(){

    }

    private Attendant(int idNumber, String name, boolean isFree, double salary) {
        this.attendantId = idNumber;
        this.name = name;
        this.isFree = isFree;
        this.salary = salary;
    }

    private int getAttendantId() {
        return attendantId;
    }

    private boolean getIsFree(){
        return isFree;
    }

    private double getSalary(){
        return salary;
    }

    void setSalary(double salary){
        this.salary = salary;
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
     * Checks if an ArrayList is empty or not
     *
     * @param list Arraylist (freeAttendants or occupiedAttendants)
     * @return boolean
     */
    private boolean isAttendant(ArrayList<Attendant> list){
        return list.isEmpty();
    }

    String getName() {
        return name;
    }

    void setIsFree(boolean isFree){
        this.isFree = isFree;
    }

    /**
     * Checks if freeAttendants is empty
     * If it is empty, it returns true
     *
     * @return boolean
     */
    boolean isFreeEmpty(){
        return freeAttendants.isEmpty();
    }
    //Prints ArrayList "freeAttendants"

    void printFreeAttendants(){
        for (Attendant a:freeAttendants){
            System.out.println(a.toString());
        }
    }
    //Prints ArrayList "occupiedAttendants"

    void printOccupiedAttendants(){
        for (Attendant a:occupiedAttendants){
            System.out.println(a.toString());
        }
    }

    /**
     * Has the user type in a name,
     * Increases attendantId by 1
     * creates a new attendant with that name
     */
    void newAttendant(){
        System.out.println("Please type in the attendants name below");
        Scanner scan = new Scanner(System.in);
        name = scan.nextLine().toUpperCase();
        System.out.println("Please type in the attendants salary below (eg. 1.5)");
        salary = scan.nextDouble();
        attendantId++;

        Attendant attendant = new Attendant(attendantId, name, true, salary);
        attendants.add(attendant);
        freeAttendants.add(attendant);
    }

    /**
     * Checks if "freeAttendants" is empty,
     * If not, calls getRandomAttendant()
     * Moves attendant to "occupiedAttendants"
     * Removes attendant from "freeAttendants"
     *
     * @return Random attendant
     */
    Attendant callAttendant(){
        Attendant randAttendant;
        if(isAttendant(freeAttendants)){
            return null;
        } else{
            randAttendant = getRandomAttendant(freeAttendants);
            randAttendant.setIsFree(false);
            occupiedAttendants.add(randAttendant);
            freeAttendants.remove(randAttendant);
            return randAttendant;
        }
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
     * The user then writes in a double in order to set the salary for teh attendant
     */
    void changeSalary(){
        boolean quit = false;
        boolean menu = false;
        String format = "|   %1$-36s|\n";
        Attendant tempAttendant = null;
        Scanner scan = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n");

        System.out.println("\n+ ------------------------------------- +");
        System.out.format(format, "Please enter the full");
        System.out.format(format, "name of the attendant");

        do{
            String name = scan.nextLine().toUpperCase();

            if(name.equals("EXIT")){
                quit = true;
                menu = true;
            } else {
                tempAttendant = searchForAttendant(name);

                if (tempAttendant != null){
                    System.out.format(format, "Is this the correct attendant?  y/n");
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
                            break;
                        case "EXIT":
                            quit = true;
                            menu = true;
                            break;
                        default:
                            System.out.println("\n\n-------ERROR------\n---InVALID INPUT---");
                    }
                } else {
                    System.out.println("\n\n+ ------------------------------------- +");
                    System.out.format(format, "Sorry, the attendant was not found,");
                    System.out.format(format, "It could be a typo, or the ");
                    System.out.format(format, "attendant could no longer");
                    System.out.format(format, "be in the system");
                    System.out.format(format, "");
                    System.out.format(format, "Please re-enter the attendants ");
                    System.out.format(format, "name, or 'exit' to return to");
                    System.out.format(format, "the main menu");
                    System.out.println("+ ------------------------------------- +");
                }
            }
        } while (!quit);

        if (!menu){
            System.out.format(format, "Please type in the attendants");
            System.out.format(format, "new salary");
            System.out.println("+ ------------------------------------- +");
            double newSalary = scan.nextDouble();
            tempAttendant.setSalary(newSalary);
        }

    }

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
     * Loads from file "attendant.txt"
     *
     * @throws IOException
     */
    void load() throws IOException {
            FileReader fileInput = new FileReader("attendant.txt");
            Scanner infile = new Scanner(fileInput);
            infile.useDelimiter(":|\r?\n|\r");
            while(infile.hasNext()) {
                int i = infile.nextInt();
                String n = infile.next();
                boolean f = infile.nextBoolean();
                double s = infile.nextDouble();

                Attendant a = new Attendant(i,n,f,s);
                attendants.add(a);
                if(f){
                    freeAttendants.add(a);
                } else{
                    occupiedAttendants.add(a);
                }
                attendantId = i;
            }
        }

    /**
     * Saves to file "attendant.txt"
     * @throws IOException
     */
    void save() throws IOException {
        FileWriter fileOutput = new FileWriter("attendant.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);
        for (Attendant a:attendants) {
            outfile.println( a.getAttendantId() + ":" + a.getName() + ":" + a.getIsFree() + ":" + a.getSalary());
        }
        outfile.close();
    }

    @Override
    public boolean equals(Object obj) {
        Attendant o = (Attendant) obj;
        if(this.getName().equals(o.getName())){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: ");
        sb.append(attendantId);
        sb.append(", name: ");
        sb.append(name);
        if(isFree){
            sb.append(", not occupied");
        } else{
            sb.append(", occupied");
        }
        return sb.toString();
    }
}

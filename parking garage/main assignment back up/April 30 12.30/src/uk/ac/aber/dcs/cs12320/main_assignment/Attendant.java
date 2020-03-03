package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Attendant {
    private String name;
    private int attendantId;    //Starts counting from 1
    private boolean isFree;

    private ArrayList<Attendant> attendants = new ArrayList<>();             //ArrayList keeping track of every attendant
    private ArrayList<Attendant> freeAttendants = new ArrayList<>();        //ArrayList keeping track of every available attendants
    private ArrayList<Attendant> occupiedAttendants = new ArrayList<>();   //ArrayList keeping track of every occupied attendants

    Attendant(){

    }

    private Attendant(int idNumber, String name, boolean isFree) {
        this.attendantId = idNumber;
        this.name = name;
        this.isFree = isFree;
    }

    private int getAttendantId() {
        return attendantId;
    }

    private boolean getIsFree(){
        return isFree;
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
        System.out.println("Please type in the attendants name");
        Scanner scan = new Scanner(System.in);
        name = scan.nextLine();
        attendantId++;

        Attendant attendant = new Attendant(attendantId, name, true);
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

        //loads from file "attendant.txt"

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

                Attendant a = new Attendant(i,n,f);
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
            outfile.println( a.getAttendantId() + ":" + a.getName() + ":" + a.getIsFree());
        }
        outfile.close();
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

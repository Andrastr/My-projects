package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Attendant extends Driver {
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

    void setIsFree(boolean isFree){
        this.isFree = isFree;
    }

    private Attendant getRandomAttendant(ArrayList<Attendant> list) {
        Attendant attendant;
        Random rand = new Random();
        attendant = attendants.get(rand.nextInt(list.size()));
        return attendant;
    }

    void printAttendants(){
        for (Attendant a:attendants){
            System.out.println(a.toString());
        }
    }

    void printFreeAttendants(){
        for (Attendant a:freeAttendants){
            System.out.println(a.toString());
        }
    }

    void printOccupiedAttendants(){
        for (Attendant a:occupiedAttendants){
            System.out.println(a.toString());
        }
    }

    private boolean isAttendant(ArrayList<Attendant> list){
        return list.isEmpty();
    }

    void newAttendant(){
        System.out.println("Please type in the attendants name");
        Scanner scan = new Scanner(System.in);
        name = scan.nextLine();
        attendantId++;

        Attendant attendant = new Attendant(attendantId, name, true);
        attendants.add(attendant);
        freeAttendants.add(attendant);
    }

    Attendant callAttendant(){
        Attendant randAttendant;
        if(isAttendant(freeAttendants)){
            return null;
        } else{
            randAttendant = getRandomAttendant(freeAttendants);
            randAttendant.setIsFree(false);
            occupiedAttendants.add(randAttendant);
            freeAttendants.remove(randAttendant);
            System.out.println("\n\nYour new attendant is " + randAttendant.getName());
            return randAttendant;
        }
    }

    private void systemSleep(int millis){
        try {
            Thread.sleep(millis);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

        //loads from file
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

        //saves to file
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

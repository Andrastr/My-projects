package uk.ac.aber.dcs.cs12320.main_assignment;

import java.util.*;

public class Attendant extends Driver {
    private int attendantId;    //Starts counting from 1
    private boolean isFreeEmpty = true;
    private Scanner scan;

    private ArrayList<Attendant> attendants = new ArrayList<>();             //ArrayList keeping track of every attendant
    private ArrayList<Attendant> freeAttendants = new ArrayList<>();        //ArrayList keeping track of every available attendants
    private ArrayList<Attendant> occupiedAttendants = new ArrayList<>();   //ArrayList keeping track of every occupied attendants

    public Attendant(){

    }

    public Attendant(int idNumber, String name) {
        this.attendantId = idNumber;
        this.name = name;
    }

    public int getId() {
        return attendantId;
    }

    public Attendant getRandomAttendant(ArrayList<Attendant> list) {
        Attendant attendant;
        Random rand = new Random();
        attendant = attendants.get(rand.nextInt(list.size()));
        return attendant;
    }

    public String printAttendants(){
        return attendants.toString();
    }

    public String printFreeAttendants(){
        return freeAttendants.toString();
    }

    public String printOccupiedAttendants(){
        return occupiedAttendants.toString();
    }

    public void addAttendant(Attendant newAttendant){
        attendants.add(newAttendant);
    }

    public void addFreeAttendant(Attendant newFree){
        freeAttendants.add(newFree);
    }

    public void addOccupiedAttendant(Attendant newOccupied){
        occupiedAttendants.add(newOccupied);
    }

    private boolean isAttendant(ArrayList<Attendant> list){
        if (list.isEmpty()){
            return isFreeEmpty = true;
        } else{
            return isFreeEmpty = false;
        }
    }

    public Attendant newAttendant(){

        System.out.println("Please type in the attendants name");
        scan = new Scanner(System.in);
        name = scan.nextLine();
        attendantId++;

        Attendant attendant = new Attendant(attendantId, name);
        attendants.add(attendant);
        freeAttendants.add(attendant);
        return attendant;
    }

    public Attendant callAttendant(){
        Attendant randAttendant;
        isAttendant(freeAttendants);
        if(isFreeEmpty == true){
            return null;
        } else{
            randAttendant = getRandomAttendant(freeAttendants);
            occupiedAttendants.add(randAttendant);
            freeAttendants.remove(randAttendant);
            System.out.println(freeAttendants.toString());
            System.out.println(occupiedAttendants.toString());
            systemSleep(2000);
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

    @Override
    public String toString() {
        return "\nID: " + attendantId + ", Name: " + name;
    }
}

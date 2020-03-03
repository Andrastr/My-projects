package uk.ac.aber.dcs.cs12320.main_assignment;

public class Attendant {
    private String name;
    private int attendantId;    //Starts counting from 1
    private boolean isFree;
    private double salary;


    Attendant(){

    }

    Attendant(int idNumber, String name, boolean isFree, double salary) {
        this.attendantId = idNumber;
        this.name = name;
        this.isFree = isFree;
        this.salary = salary;
    }

    String getName() {
        return name;
    }

    int getAttendantId() {
        return attendantId;
    }

    boolean getIsFree(){
        return this.isFree;
    }

    void setIsFree(boolean isFree){
        this.isFree = isFree;
    }

    double getSalary(){
        return salary;
    }

    void setSalary(double salary){
        this.salary = salary;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Attendant){
            Attendant o = (Attendant) obj;
            if(this.getName().equals(o.getName())){
                return true;
            } else{
                return false;
            }
        } else {
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

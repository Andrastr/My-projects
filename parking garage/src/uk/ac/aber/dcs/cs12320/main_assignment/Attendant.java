package uk.ac.aber.dcs.cs12320.main_assignment;

public class Attendant {
    private String name;
    private int attendantId;    //Starts counting from 1
    private boolean isFree;
    private double salary;

    /**
 * Constructor for the class Attendant
 */
    Attendant(){

    }

    /**
     * Another constructor for the class Attendant, with params
     *
     * @param idNumber The identification number of the attendant
     * @param name The name if the attendant
     * @param isFree If the attendant is currently occupied (false) or free (true)
     * @param salary Hourly salary of the attendant (in Units)
     */
    Attendant(int idNumber, String name, boolean isFree, double salary) {
        this.attendantId = idNumber;
        this.name = name;
        this.isFree = isFree;
        this.salary = salary;
    }

    /**
     * @return The name of the attendant
     */
    String getName() { return name; }

    /**
     * @return The id number of the attendant
     */
    int getAttendantId() { return attendantId; }

    /**
     * @return If the attendant is free (true) or not (false)
     */
    boolean getIsFree(){ return this.isFree; }

    /**
     * @param isFree If the attendant is free (true) or not (false)
     */
    void setIsFree(boolean isFree){ this.isFree = isFree; }

    /**
     * @return salary of the attendant (in Units)
     */
    double getSalary(){ return salary; }

    /**
     * @param salary salary of the attendant (in Units)
     */
    void setSalary(double salary){ this.salary = salary; }


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
            sb.append(", free");
        } else{
            sb.append(", occupied");
        }
        return sb.toString();
    }
}

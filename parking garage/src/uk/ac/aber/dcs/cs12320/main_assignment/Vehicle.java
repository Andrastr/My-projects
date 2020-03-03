package uk.ac.aber.dcs.cs12320.main_assignment;

public class Vehicle extends Customer {
    private int type;
    private int zone;
    private int space;

    /**
     * Constructor for the class Vehicle
     */
    Vehicle(){

    }

    /**
     * Another constructor for the class vehicle
     *
     * @param type Type of vehicle
     * @param zone Which zone the vehicle is parked in
     * @param space Which parking space the vehicle is parked on
     */
    Vehicle(int type, int zone, int space) {
        this.type = type;
        this.zone = zone;
        this.space = space;

    }

    /**
     * @return The type of vehicle
     */
    int getType() { return type; }

    /**
     * @param type The type of vehicle
     */
    void setType(int type) { this.type = type; }

    /**
     * @return The zone the vehicle is parked in
     */
    int getZone() { return zone; }

    /**
     * @param zone The zone the vehicle is parked in
     */
    void setZone(int zone) { this.zone = zone; }

    /**
     * @return The parking space the vehicle is parked on
     */
    int getSpace() { return space; }

    /**
     * @param space The sapce teh vehicle is parked on
     */
    void setSpace(int space) { this.space = space; }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String typeString;

        if(type == 1){
            typeString = "standard";
        } else if ( type == 2){
            typeString = "high vehicle";
        } else if ( type == 3){
            typeString = "long vehicle";
        } else if (type == 4){
            typeString = "coach";
        } else if (type == 5){
            typeString = "motorbike";
        } else{
            typeString = "ERROR: INVALID TYPE";
        }

        sb.append("Owner ID: ");
        sb.append(this.getOwnerId());
        sb.append(", Type: ");
        sb.append(typeString);
        sb.append(", License plate: ");
        sb.append(this.getLicensePlate());
        sb.append(", parked in zone ");
        sb.append(zone);
        sb.append(" space ");
        sb.append(space);
        sb.append(", Receipt code: ");
        sb.append(this.getReceiptNum());
        sb.append(", parked ");
        sb.append(parkedString(1));
        return sb.toString();
    }

}

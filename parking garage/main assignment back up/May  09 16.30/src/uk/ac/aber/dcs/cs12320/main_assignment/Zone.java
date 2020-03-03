package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Zone {

    private String name;
    private boolean isOutside;
    private double price;


        //To make it easier to read/double check, the program only has a few of each parking space, the real garage would have much more space
    boolean[] z1Space = new boolean[10];
    boolean[] z2Space = new boolean[6];
    boolean[] z3Space = new boolean[4];
    boolean[] z4Space = new boolean[10];
    boolean[] z5Space = new boolean[8];

    Zone(){}

    Zone(String name, boolean isOutside, double price){
        this.name = name;
        this.isOutside = isOutside;
        this.price = price;
    }

    String getName(){
        return name;
    }

    boolean getOutside(){
        return this.isOutside;
    }

    void setOutside(boolean isOutside){
        this.isOutside = isOutside;
    }

    double getPrice(){
        return price;
    }

    void setPrice(double price){
        this.price = price;
    }

    /**
     * Prints the array in a way the load() can read it from file
     * eg. false:false:true:false
     *
     * @param array boolean array
     * @return array as String
     */
    String printZoneBoolean(boolean[] array){
        int i = 0;
        StringBuilder gh = new StringBuilder();
        while(i < (array.length - 1)){
            if (array[i]){
                gh.append("true:");
            }else{
                gh.append("false:");
            }
            i++;
        }
        if (array[array.length-1]){
            gh.append("true");
        } else{
            gh.append("false");
        }
        if(array != z5Space){
            gh.append("\n");
        }
        return gh.toString();
    }

    /**
     * Loads data from file "zone.txt"
     *
     * @throws IOException
     */
    void loadParkingSpaces()throws IOException{
        FileReader fileInput = new FileReader("parkingSpaces.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        int i;
        int j=1;
        boolean[] list;
        while(infile.hasNext()){
            switch (j){
                case 1:
                    list=z1Space;
                    break;
                case 2:
                    list=z2Space;
                    break;
                case 3:
                    list = z3Space;
                    break;
                case 4:
                    list = z4Space;
                    break;
                case 5:
                    list = z5Space;
                    break;
                default:
                    list = z1Space;
                    System.err.println("An error has occurred loading Zone " + j + "parking spaces");
            }

            for (i=0;i<list.length;i++){
               list[i] = infile.nextBoolean();
           }
            j++;
        }
    }

    /**
     * Saves data to file "zone.txt
     *
     * @throws IOException
     */
    void saveParkingSpaces() throws IOException {
        FileWriter fileOutput = new FileWriter("parkingSpaces.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);

        outfile.println(printZoneBoolean(z1Space) + printZoneBoolean(z2Space) + printZoneBoolean(z3Space) + printZoneBoolean(z4Space) + printZoneBoolean(z5Space));
        outfile.close();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" has an hourly rate of: ");
        sb.append(price);
        if(!isOutside){
            sb.append(" Units, and is inside");
        } else{
            sb.append(" Units, and is outside");
        }
        return sb.toString();
    }
}

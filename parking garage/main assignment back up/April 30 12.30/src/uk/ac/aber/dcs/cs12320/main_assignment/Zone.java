package uk.ac.aber.dcs.cs12320.main_assignment;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zone {

    private String name;
    private boolean isOutside;
    private double price;
    ArrayList<Zone> zones = new ArrayList<>();

        //To make it easier to save/load, the program only has a few of each parking space, the real garage would have ten times the space
    boolean[] z1Space = new boolean[10];
    boolean[] z2Space = new boolean[6];
    boolean[] z3Space = new boolean[4];
    boolean[] z4Space = new boolean[10];
    boolean[] z5Space = new boolean[8];

    Zone(){}

    private Zone(String name, boolean isOutside, double price){
        this.name = name;
        this.isOutside = isOutside;
        this.price = price;
    }

    private String getName(){
        return name;
    }

    double getPrice(){
        return price;
    }

    private void setPrice(double price){
        this.price = price;
    }

    void printZones(){
        for(Zone z:zones){
            System.out.println(z.toString());
        }
    }

    void displayZone(int j){

        StringBuilder sb = new StringBuilder("\n");
        int i;
        int f=0;
        boolean[] list;

        if(j==1){
            list = z1Space.clone();
        } else if(j==2){
            list = z2Space.clone();
        } else if (j==3){
            list = z3Space.clone();
        } else if(j==4){
            list = z4Space.clone();
        } else if(j==5){
            list = z5Space.clone();
        } else{
            list = z1Space.clone();
        }

        if(j==1){
            sb.append("\n+ -------------| Zone  1 |------------- +\n");
        } else if(j==2){
            sb.append("\n+ -----| Zone  2 |----- +\n");
        } else if(j==3){
            sb.append("\n+ ---------| Zone  3 |--------- +\n");
        } else if(j==4){
            sb.append("\n+ -------------| Zone  4 |------------- +\n");
        }else if(j==5){
            sb.append("\n+ ---------| Zone  5 |--------- +\n");
        }

        for (i=0;i<(list.length);i++){
            sb.append("|");
            if (!list[i]){
                sb.append("  free ");
                f++;
            } else{
                sb.append(" taken ");
            }
            if (j != 3) {
                if(i==(list.length/2-1)){
                    sb.append("|\n");
                }
            }
        }
        sb.append("|");
        if(j==1 || j==4){
            sb.append("\n+ ------------------------------------- +");
        } else if(j==2){
            sb.append("\n+ --------------------- +");
        } else if(j==3 || j==5){
            sb.append("\n+ ----------------------------- +");
        }
        System.out.println(sb.toString());
        System.out.println("\nThere is " + f + " free spaces in Zone " + j);




            //Previous attempt at printing free zones, before I realised a StringBuilder would be more efficient
        /*if (i==1){
            System.out.println("| " + z1Space[0] + " | " + z1Space[1] + " | " + z1Space[2] + " | " + z1Space[3] + " | " + z1Space[4] + " |");
            System.out.println("| ------------------------------------- |");
            System.out.println("| " + z1Space[5] + " | " + z1Space[6] + " | " + z1Space[7] + " | " + z1Space[8] + " | " + z1Space[9] + " |");
        }*/
    }

    /**
     * Allows employees to change the hourly rates for different zones
     */
    void changePrices(){
        String format = "|\t%1$-33s|\n";
        Scanner scan;
        double price;
        boolean quit = false;

        do{
            System.out.println("\n\n\n\n\n\n+ ---------------------------------- +");
            System.out.format(format, "   Select the zone to change");
            System.out.format(format, "");
            System.out.format(format, "           1. Zone 1");
            System.out.format(format, "           2. Zone 2");
            System.out.format(format, "           3. Zone 3");
            System.out.format(format, "           4. Zone 4");
            System.out.format(format, "           5. Zone 5");
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
        } while(!quit);
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

      //DELETE ME
     //method for loading the 5 zone space arrays
    /*private void loadZoneBoolean(boolean[] array) throws IOException{
        FileReader fileInput = new FileReader("zone.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        int i;
        for (i = 0; i < array.length;i++){
            array[i] = infile.nextBoolean();
            System.out.print(i + ":" + array[i] + " | ");
        }
        System.out.println("done");
    }*/

    /**
     * Loads from file "price.txt"
     *
     * @throws IOException
     */
    void load() throws IOException {
        FileReader fileInput = new FileReader("price.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        while(infile.hasNext()) {
            String n = infile.next();
            boolean o = infile.nextBoolean();
            double p = infile.nextDouble();

            Zone z = new Zone(n,o,p);
            zones.add(z);
        }
    }

    /**
     * Saves to file "price.txt
     *
     * @throws IOException
     */
    void save() throws IOException {
        FileWriter fileOutput = new FileWriter("price.txt");
        PrintWriter outfile = new PrintWriter(fileOutput);
        for (Zone z:zones) {
            outfile.println(z.getName() + ":" + z.isOutside + ":" + z.getPrice());
        }
        outfile.close();
    }

    /**
     * Loads data from file "zone.txt"
     *
     * @throws IOException
     */
    void loadZone()throws IOException{
        FileReader fileInput = new FileReader("zone.txt");
        Scanner infile = new Scanner(fileInput);
        infile.useDelimiter(":|\r?\n|\r");
        int i;
        int j=1;
        boolean[] list;
        while(infile.hasNext()){
           if(j==1){
               list=z1Space;
           } else if (j==2){
               list=z2Space;
           } else if(j==3){
               list = z3Space;
           } else if(j==4){
               list = z4Space;
           } else{
               list = z5Space;
           }

            for (i=0;i<list.length;i++){
               list[i] = infile.nextBoolean();
                //System.out.print(i + ":" + list[i] + "|");
           }
            //System.out.println("DONE");
            j++;
        }



        /*loadZoneBoolean(z1Space);
        loadZoneBoolean(z2Space);
        loadZoneBoolean(z3Space);
        loadZoneBoolean(z4Space);
        loadZoneBoolean(z5Space);*/
    }

    /**
     * Saves data to file "zone.txt
     *
     * @throws IOException
     */
    void saveZone() throws IOException {
        FileWriter fileOutput = new FileWriter("zone.txt");
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

package hotel;
import Dash.Booking;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

class RoomsUnavailableException extends Exception{
    public RoomsUnavailableException(String message){
        super(message);
    }
}

class InvalidDateException extends Exception{
    public InvalidDateException(String message){
        super(message);
    }
}

public class Hotels {
    String hotelType;
    String hotelName;
    String hotelPlace;
    String hotelContact;
    int hotelRooms;
    double hotelPrice;
    double hotelRating;

    public Hotels(String hotelType, String hotelName, String hotelPlace, String hotelContact, int hotelRooms, double hotelPrice, double hotelRating) {
        this.hotelType = hotelType;
        this.hotelName = hotelName;
        this.hotelPlace = hotelPlace;
        this.hotelContact = hotelContact;
        this.hotelRooms = hotelRooms;
        this.hotelPrice = hotelPrice;
        this.hotelRating = hotelRating;
    }

    public static void CheckHotels(String destination){
        try(Scanner z = new Scanner(System.in)){
            System.out.println("\n                                                                      HOTELS IN "+destination.toUpperCase()+"                                                  ");
            String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] hotelDetails = line.split(",");
                    String destinationfromcsv = hotelDetails[2].trim();
                    if(destination.equalsIgnoreCase(destinationfromcsv)){
                        System.out.println("TYPE:" + hotelDetails[0]);
                        System.out.println("NAME:" + hotelDetails[1]);
                        System.out.println("PRICE:" + hotelDetails[5]);
                        System.out.println("RATING:" + hotelDetails[6]);
                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    }
                }

                hotelOptions(destination);
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void hotelOptions(String destination){
        Scanner z=new Scanner(System.in); 
        System.out.println("\n                                               1. SEARCH BY PRICE RANGE   2. SEARCH BY HOTEL TYPE   3. CHOOSE A HOTEL                                                   ");
        System.out.println("");
        System.out.println("Your Choice:");
        int choice=z.nextInt();

        if(choice==1){
            System.out.println("Enter the minimum price:");
            double minPrice=z.nextDouble();
            System.out.println("Enter the maximum price:");
            double maxPrice=z.nextDouble();
            search_hotel(destination,minPrice,maxPrice);

            z.nextLine();
            System.out.println("\nDo you want to book a hotel?(Yes/No)");
            String ans=z.nextLine();

            if(ans.equalsIgnoreCase("yes")){
                Choose_hotel(destination);
                return;
            }
            else{
                hotelOptions(destination);
            }
        }
        else if(choice==2){
            z.nextLine();
            System.out.println("Enter the type(OneStar/TwoStar/ThreeStar/FourStar/FiveStar):");
            String type = z.nextLine();

            search_hotel(destination, type);

            System.out.println("\nDo you want to book a hotel?(Yes/No)");
            String ans=z.nextLine();

            if(ans.equalsIgnoreCase("yes")){
                Choose_hotel(destination);
                return;
            }
            else{
                hotelOptions(destination);
            }
        }
        else if(choice==3){
            Choose_hotel(destination);
            return;
        }

    }

    public static void Choose_hotel(String destination){
        Scanner x = new Scanner(System.in);
        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))){

            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Choose the hotel:");
            String h_name = x.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Check-in date:");
            String checkin=x.nextLine();
            LocalDate currentDate = LocalDate.now();
            LocalDate checkinDate = LocalDate.parse(checkin);
            if (checkinDate.isBefore(currentDate)) {
                throw new InvalidDateException("~Invalid start date. Please choose a date in the future.");
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Check-out date:");
            String checkout=x.nextLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] hotelDetails = line.split(",");
                String hotelfromcsv = hotelDetails[1].trim();
                if (h_name.equalsIgnoreCase(hotelfromcsv)){
                    if (Integer.parseInt(hotelDetails[4]) != 0) {
                        int roomcount=updateroomavailable(h_name,checkin,checkout);
                        System.out.println("\n--------------------------------------------------------------------" + hotelDetails[1].toUpperCase() + "-----------------------------------------------------------------------");
                        System.out.println();
                        System.out.format("| %-20s | %-15s | %-20s | %-15s | %-15s | %-10s | %-10s | %-10s | %-10s |\n",
                                "LOCATION", "CONTACT", "TOTAL ROOMS AVAILABLE", "PRICE", "RATING", "BEDROOMS", "BATHROOMS", "SPA", "POOL");

                        String category = hotelDetails[0];
                        String spa = "";
                        String pool = "";
                        String extraFeature = "";
                        int roomavailable=Integer.parseInt(hotelDetails[4])+roomcount;
                        String roomscountfinal=String.valueOf(roomavailable);

                        switch (category) {
                            case "OneStar":
                                break;
                            case "TwoStar":
                                spa = hotelDetails[9];
                                break;
                            case "ThreeStar":
                                spa = hotelDetails[9];
                                pool = hotelDetails[10];
                                break;
                            case "FourStar":
                                spa = hotelDetails[8];
                                extraFeature = hotelDetails[11];
                                pool = hotelDetails[12];
                                break;
                            case "FiveStar":
                                spa = hotelDetails[8];
                                extraFeature = hotelDetails[13];
                                pool = hotelDetails[11];
                                break;
                        }

                        System.out.format("| %-20s | %-15s | %-20s  | %-15s | %-15s | %-10s | %-10s | %-10s | %-10s |\n",
                                hotelDetails[2], hotelDetails[3], roomscountfinal, hotelDetails[5], hotelDetails[6],
                                hotelDetails[7], spa, pool, extraFeature);

                        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        System.out.println("\nDo you want to confirm the booking?(Y/N):");
                        String choice = x.nextLine().toLowerCase();
                        if (choice.equals("y")) {
                            Booking.checkIn(h_name,checkin,checkout);
                        }
                        else {
                            System.out.println("\nDo you want to choose another hotel?(Y/N):");
                            String ans = x.nextLine().toLowerCase();

                            if (ans.equals("y")) {
                                CheckHotels(destination);
                            }
                            else{
                                System.exit(1);
                            }
                        }
                    }
                    else{
                        throw new RoomsUnavailableException("No rooms available at the selected hotel");
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (RoomsUnavailableException e) {
            System.out.println(e.getMessage());
            CheckHotels(destination);
        }
        catch(InvalidDateException e){
            System.out.println(e.getMessage());
            Choose_hotel(destination);
        }
    }

    public static void search_hotel(String destination,double minPrice,double maxPrice){
        int found=0;
        String filepath="C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
        System.out.println("                                                                         HOTELS IN "+destination.toUpperCase()+"                                                               \n");
        try(BufferedReader br=new BufferedReader(new FileReader(filepath))){
            String line;

            while((line=br.readLine())!= null){
                String[] hotelDetails=line.split(",");

                String destinationfromcsv=hotelDetails[2].trim();
                double price=Double.parseDouble(hotelDetails[5]);
                if(destination.equalsIgnoreCase(destinationfromcsv) && price>=minPrice && price<=maxPrice){
                    System.out.println("TYPE:" + hotelDetails[0]);
                    System.out.println("NAME:" + hotelDetails[1]);
                    System.out.println("PRICE:" + hotelDetails[5]);
                    System.out.println("RATING:" + hotelDetails[6]);
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    found=1;
                }
            }
            if(found==0){
                System.out.println("NO HOTELS FOUND IN THAT PRICE RANGE!\n");
                hotelOptions(destination);
                return;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void search_hotel(String destination,String type){
        int found=0;
        String filepath="C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
        System.out.println("                                                                         HOTELS IN "+destination.toUpperCase()+"                                                             \n");
        try(BufferedReader br=new BufferedReader(new FileReader(filepath))){
            String line;
            while((line=br.readLine())!= null){
                String[] hotelDetails=line.split(",");
                String typefromcsv=hotelDetails[0].trim();
                String destinationfromcsv=hotelDetails[2].trim();

                if (destination.equalsIgnoreCase(destinationfromcsv) && type.equalsIgnoreCase(typefromcsv)) {
                    System.out.println("TYPE:" + hotelDetails[0]);
                    System.out.println("NAME:" + hotelDetails[1]);
                    System.out.println("PRICE:" + hotelDetails[5]);
                    System.out.println("RATING:" + hotelDetails[6]);
                    System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    found=1;
                }
            }
            if(found==0){
                System.out.println("NO HOTELS FOUND IN THAT TYPE!\n");
                hotelOptions(destination);
                return;
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static int updateroomavailable(String hotelname,String checkin,String checkout){
        int roomsno=0;
        String filepath1 = "C:\\Users\\Sweety\\Desktop\\Hotel\\Customer.csv";
            try (BufferedReader br1 = new BufferedReader(new FileReader(filepath1))){
                String line1; 
                while ((line1=br1.readLine()) != null) {
                    String customerDetails1[] = line1.split(",");
                    if(hotelname.equalsIgnoreCase(customerDetails1[1])){
                        String parsedcustomerenddate= customerDetails1[3];
                        String parsedcustomerstartdate=customerDetails1[2];

                        String checkoutslicing= checkout.substring(8,10);
                        String checkinslicing = checkin.substring(8, 10);
                        String checkoutmonthslicing=checkout.substring(5,7);
                        String checkinmonthslicing=checkin.substring(5,7);
                        String parsedcustomerenddateslicing= parsedcustomerenddate.substring(8,10);
                        String parsedcustomerstartdateslicing=parsedcustomerstartdate.substring(8,10);
                        String parsedcustomerendmonthslicing= parsedcustomerenddate.substring(5,7);
                        String parsedcustomerstartmonthslicing=parsedcustomerstartdate.substring(5,7);

                        int intcheckinslicing = Integer.parseInt(checkinslicing);
                        int intcheckoutslicing=Integer.parseInt(checkoutslicing);
                        int intcustomerendslicing= Integer.parseInt(parsedcustomerenddateslicing);
                        int intcustomerstartslicing=Integer.parseInt(parsedcustomerstartdateslicing);
                        int intcheckinmonthslicing=Integer.parseInt(checkinmonthslicing);
                        int intcheckoutmonthslicing=Integer.parseInt(checkoutmonthslicing);
                        int intcustomerendmonthslicing=Integer.parseInt(parsedcustomerendmonthslicing);
                        int intcustomerstartmonthslicing=Integer.parseInt(parsedcustomerstartmonthslicing);

                        if(((intcheckinslicing>intcustomerstartslicing)&&(intcheckinslicing>intcustomerendslicing)&&((intcheckinmonthslicing-intcustomerstartmonthslicing==1)||(intcheckinmonthslicing<intcustomerstartmonthslicing))) ||((intcheckoutslicing>intcustomerstartslicing)&&(intcheckoutslicing>intcustomerendslicing)&&(intcheckoutmonthslicing==intcustomerendmonthslicing)&&(intcheckoutmonthslicing==intcustomerstartmonthslicing))){
                            roomsno=roomsno;
                        }

                        if((intcheckoutslicing>=intcustomerstartslicing)&&(intcheckoutslicing<=intcustomerendslicing)&&(intcheckoutmonthslicing==intcustomerstartmonthslicing)&&(intcheckoutmonthslicing==intcustomerendmonthslicing)){
                            roomsno=roomsno;
                        }
                        else if(((intcheckinslicing>=intcustomerstartslicing)&&(intcheckinslicing>=intcustomerendslicing)&&(intcheckinmonthslicing==intcustomerstartmonthslicing))||((intcheckinslicing<intcustomerstartslicing)&&((intcheckinmonthslicing-intcustomerstartmonthslicing==1)||(intcheckinmonthslicing<intcustomerstartmonthslicing)))){
                            String roomscounts=customerDetails1[5];
                            int rooms=Integer.parseInt(roomscounts);
                            roomsno=roomsno+rooms;
                        }
                        else if((intcheckinslicing>=intcustomerstartslicing)&&(intcheckinslicing<=intcustomerendslicing)){
                            roomsno=roomsno;
                        }
                        else if((intcheckoutslicing<intcustomerstartslicing)&&(intcheckinslicing<intcustomerendslicing)&&(intcheckinmonthslicing==intcustomerendmonthslicing)&&(intcheckinmonthslicing==intcustomerstartmonthslicing)){
                            String roomscounts=customerDetails1[5];
                            int rooms=Integer.parseInt(roomscounts);
                            roomsno=roomsno+rooms;
                        }
                        else if(((intcheckoutslicing<intcustomerstartslicing)&&(intcheckoutmonthslicing==intcustomerstartmonthslicing))||((intcheckinslicing<intcustomerstartslicing) &&((intcheckinmonthslicing-intcustomerstartmonthslicing==1)||(intcheckinmonthslicing<intcustomerstartmonthslicing)))){
                            String roomscounts=customerDetails1[5];
                            int rooms=Integer.parseInt(roomscounts);
                            roomsno=roomsno+rooms;
                        }
                        else if(((intcheckinslicing>intcustomerendslicing)&&(intcheckinslicing>intcustomerstartmonthslicing)&&(intcheckoutslicing<intcustomerstartslicing)&&(intcheckoutslicing<intcustomerendslicing)&&(intcheckinmonthslicing==intcustomerendmonthslicing))||((intcheckinslicing>intcustomerendslicing)&&(intcheckinslicing>intcustomerstartmonthslicing)&&(intcheckoutslicing<intcustomerstartslicing)&&(intcheckoutslicing<intcustomerendslicing)&&((intcheckinmonthslicing-intcustomerstartmonthslicing==1)||(intcheckinmonthslicing<intcustomerstartmonthslicing)))){
                            String roomscounts=customerDetails1[5];
                            int rooms=Integer.parseInt(roomscounts);
                            roomsno=roomsno+rooms;
                        }
                        else if(((intcheckoutslicing>=intcustomerstartslicing)&&(intcheckoutslicing<=intcustomerendslicing)&& (intcheckinmonthslicing==intcustomerstartmonthslicing))||((intcheckinslicing<intcustomerstartslicing)&&((intcheckinmonthslicing<intcustomerendmonthslicing)||(intcheckinmonthslicing-intcustomerendmonthslicing==1)))){
                            roomsno=roomsno;
                        }
                        else if(((intcheckinslicing>=intcustomerstartslicing) && (intcheckinslicing<=intcustomerendslicing)) || (((intcheckinslicing<intcustomerstartslicing) && ((intcheckinmonthslicing-intcustomerstartmonthslicing==1)||(intcheckinmonthslicing<intcustomerstartmonthslicing))))){
                            roomsno=roomsno;
                        }
                        else if(((intcheckinslicing<intcustomerstartslicing)&&(intcheckinslicing<intcustomerendslicing))||((intcheckinslicing<intcustomerstartslicing)&& ((intcheckinmonthslicing-intcustomerstartmonthslicing==1)||(intcheckinmonthslicing<intcustomerstartmonthslicing)))){
                            roomsno=roomsno;
                        }
                    }
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            return roomsno;
    }
}



class Main {
    public static void main(String[] args) {
    // Creating 7 OneStar Hotels in Tamil Nadu
    // OneStar hotels
    OneStar onestar1 = new OneStar("OneStar", "Tamil Retreat", "Chennai", "+91 1234567890", 10, 1200.0, 3.9, 2, 1);
    OneStar onestar2 = new OneStar("OneStar", "Cozy Haven", "Madurai", "+91 9876543210", 15, 800.0, 4.2, 1, 1);
    OneStar onestar3 = new OneStar("OneStar", "City Lights", "Trichy", "+91 8765432109", 20, 1500.0, 3.5, 3, 2);
    OneStar onestar4 = new OneStar("OneStar", "Green Valley", "Coimbatore", "+91 7654321098", 12, 1000.0, 4.0, 2, 1);
    OneStar onestar5 = new OneStar("OneStar", "Gateway Mansion", "Coimbatore", "+91 7654321678", 15, 3000.0, 4.0, 2, 1);
    OneStar onestar6 = new OneStar("OneStar", "Ocean Breeze", "Salem", "+91 6543210987", 18, 1300.0, 3.8, 1, 2);
    OneStar onestar7 = new OneStar("OneStar", "Mountain View", "Erode", "+91 5432109876", 14, 1100.0, 4.1, 3, 1);

    // TwoStar hotels
    TwoStar twostar1 = new TwoStar("TwoStar", "City Comfort", "Coimbatore", "+91 8765432109", 20, 1500.0, 4.5, 2, 1, 1);
    TwoStar twostar2 = new TwoStar("TwoStar", "Mountain View Inn", "Trichy", "+91 7890123456", 12, 1700.0, 3.8, 1, 1, 1);
    TwoStar twostar3 = new TwoStar("TwoStar", "Urban Oasis", "Chennai", "+91 6543210987", 15, 1200.0, 4.2, 3, 2, 1);
    TwoStar twostar4 = new TwoStar("TwoStar", "Sunset Haven", "Madurai", "+91 5432109876", 18, 1500.0, 3.9, 2, 1, 2);
    TwoStar twostar5 = new TwoStar("TwoStar", "Golden Sands", "Salem", "+91 9876543210", 14, 1600.0, 4.0, 1, 2, 1);
    TwoStar twostar6 = new TwoStar("TwoStar", "Serene Retreat", "Erode", "+91 8765432109", 22, 1200.0, 4.1, 3, 1, 2);
    TwoStar twostar7 = new TwoStar("TwoStar", "Ocean Heaven", "Erode", "+91 8765567219", 22, 3200.0, 4.1, 3, 1, 2);

    // ThreeStar hotels
    ThreeStar threestar1 = new ThreeStar("ThreeStar", "Royal Oasis", "Ooty", "+91 8901234567", 25, 3200.0, 4.8, 2, 1, 1, 1);
    ThreeStar threestar2 = new ThreeStar("ThreeStar", "Golden Sands Resort", "Kanyakumari", "+91 7654321098", 18, 2200.0, 4.0, 1, 1, 1, 1);
    ThreeStar threestar3 = new ThreeStar("ThreeStar", "City Heights", "Chennai", "+91 6543210987", 15, 2500.0, 4.5, 3, 2, 1, 1);
    ThreeStar threestar4 = new ThreeStar("ThreeStar", "Mountain View Resort", "Madurai", "+91 5432109876", 20, 3000.0, 4.2, 2, 1, 2, 1);
    ThreeStar threestar5 = new ThreeStar("ThreeStar", "Coastal Retreat", "Puducherry", "+91 9876543210", 22, 2600.0, 4.3, 1, 2, 1, 2);
    ThreeStar threestar6 = new ThreeStar("ThreeStar", "Hilltop Haven", "Kodaikanal", "+91 8765432109", 24, 2900.0, 4.6, 3, 1, 1, 2);
    ThreeStar threestar7 = new ThreeStar("ThreeStar", "Suncity Cottages", "Kodaikanal", "+91 7654345909", 24, 1900.0, 4.6, 3, 1, 1, 2);

    // FourStar hotels
    FourStar fourstar1 = new FourStar("FourStar", "Grand Plaza", "Chennai", "+91 9012345678", 30, 3500.0, 4.9, 2, 1, 1, 1, 1, true);
    FourStar fourstar2 = new FourStar("FourStar", "Marina View Hotel", "Rameswaram", "+91 6543210987", 22, 3800.0, 4.3, 1, 1, 1, 1, 1, false);
    FourStar fourstar3 = new FourStar("FourStar", "Skyline Retreat", "Coimbatore", "+91 8901234567", 25, 3400.0, 4.5, 3, 2, 1, 1, 1, true);
    FourStar fourstar4 = new FourStar("FourStar", "Luxury Haven", "Kanyakumari", "+91 7654321098", 18, 4000.0, 4.2, 2, 1, 2, 1, 1, false);
    FourStar fourstar5 = new FourStar("FourStar", "Harbor Lights Resort", "Puducherry", "+91 9876543210", 22, 3900.0, 4.4, 1, 2, 1, 2, 1, true);
    FourStar fourstar6 = new FourStar("FourStar", "Green Valley Suites", "Madurai", "+91 8765432109", 24, 4100.0, 4.6, 3, 1, 1, 2, 1, false);
    FourStar fourstar7 = new FourStar("FourStar", "Coastal Sunrise Haven", "Coimbatore", "+91 8764902109", 24, 2100.0, 4.6, 3, 1, 1, 2, 1, false);

    // FiveStar hotels
    FiveStar fivestar1 = new FiveStar("FiveStar", "Luxury Haven", "Chennai", "+91 9876543210", 40, 3500.0, 5.0, 3, 2, 1, 1, 1, true, 2, 1);
    FiveStar fivestar2 = new FiveStar("FiveStar", "Royal Palace", "Coimbatore", "+91 8765432109", 35, 3000.0, 4.7, 2, 1, 1, 1, 1, true, 1, 2);
    FiveStar fivestar3 = new FiveStar("FiveStar", "Elegance Resort", "Madurai", "+91 7654321098", 45, 4000.0, 4.9, 3, 1, 2, 1, 1, true, 1, 1);
    FiveStar fivestar4 = new FiveStar("FiveStar", "Serene Suites", "Trichy", "+91 6543210987", 38, 3200.0, 4.5, 2, 1, 1, 2, 1, true, 2, 1);
    FiveStar fivestar5 = new FiveStar("FiveStar", "Oceanfront Paradise", "Kodaikanal", "+91 5432109876", 42, 3700.0, 4.8, 1, 2, 1, 1, 1, true, 1, 2);
    FiveStar fivestar6 = new FiveStar("FiveStar", "Majestic Heights", "Salem", "+91 8901234567", 37, 5000.0, 4.6, 3, 1, 1, 2, 1, true, 2, 1);
    FiveStar fivestar7 = new FiveStar("FiveStar", "Mountain Dreamers Lodge", "Kodaikanal", "+91 9081234567", 37, 3100.0, 4.6, 3, 1, 1, 2, 1, true, 2, 1);

        onestar1.write_one_star();
        onestar2.write_one_star();
        onestar3.write_one_star();
        onestar4.write_one_star();
        onestar5.write_one_star();
        onestar6.write_one_star();
        onestar7.write_one_star();

        twostar1.write_two_star();
        twostar2.write_two_star();
        twostar3.write_two_star();
        twostar4.write_two_star();
        twostar5.write_two_star();
        twostar6.write_two_star();
        twostar7.write_two_star();

        threestar1.write_three_star();
        threestar2.write_three_star();
        threestar3.write_three_star();
        threestar4.write_three_star();
        threestar5.write_three_star();
        threestar6.write_three_star();
        threestar7.write_three_star();

        fourstar1.write_four_star();
        fourstar2.write_four_star();
        fourstar3.write_four_star();
        fourstar4.write_four_star();
        fourstar5.write_four_star();
        fourstar6.write_four_star();
        fourstar7.write_four_star();

        fivestar1.write_five_star();
        fivestar2.write_five_star();
        fivestar3.write_five_star();
        fivestar4.write_five_star();
        fivestar5.write_five_star();
        fivestar6.write_five_star();
        fivestar7.write_five_star();
    }
}
package Dash;
import java.io.*;
import java.util.*;
import hotel.*;

public class Admin{

    public static void admin_page(){
        Scanner x=new Scanner(System.in);
        System.out.println("\n                                                                          ADMIN PAGE                                                                          ");
        System.out.println("Checkouts are exclusively managed through this admin page. Kindly use the provided features to facilitate the checkout process efficiently. Thank you!");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("                                                            1.ADD HOTELS           2.CHECK OUTS                                                                           ");
        System.out.print("Enter the choice:");
        int ch=x.nextInt();
        if(ch==1){
            addHotels();
        }
        else if(ch==2){
            deleteRecords();
        }
    }

    public static void addHotels(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nChoose Type:");
        System.out.println("                                            1. OneStar   2. TwoStar   3. ThreeStar   4. FourStar   5. FiveStar                                                 ");
        System.out.print("Enter the hotel type (1-5): ");
        int choice=scanner.nextInt();
        scanner.nextLine();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.print("Enter hotel type:");
        String hotelType = scanner.nextLine();

        System.out.print("Enter hotel name: ");
        String hotelName = scanner.nextLine();

        System.out.print("Enter hotel place: ");
        String hotelPlace = scanner.nextLine();

        System.out.print("Enter hotel contact: ");
        String hotelContact = scanner.nextLine();

        System.out.print("Enter number of rooms: ");
        int hotelRooms = scanner.nextInt();

        System.out.print("Enter hotel price: ");
        double hotelPrice = scanner.nextDouble();

        System.out.print("Enter hotel rating: ");
        double hotelRating = scanner.nextDouble();

        // Additional attributes based on hotel type
        int bedroom, bathroom, livingroom, television, spa, pools, fitnessCenter;
        boolean staffService;

        switch (choice) {
            case 1:
                System.out.print("Enter number of bedrooms: ");
                bedroom = scanner.nextInt();
                System.out.print("Enter number of bathrooms: ");
                bathroom = scanner.nextInt();
                OneStar oneStar = new OneStar(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating, bedroom, bathroom);
                oneStar.write_one_star();
                System.out.println("----------------------------------------------------------------------ADDED A ONESTAR HOTEL---------------------------------------------------------------");
                break;

            case 2:
                System.out.print("Enter number of bedrooms: ");
                bedroom = scanner.nextInt();
                System.out.print("Enter number of bathrooms: ");
                bathroom = scanner.nextInt();
                System.out.print("Enter number of living rooms: ");
                livingroom = scanner.nextInt();
                TwoStar twoStar = new TwoStar(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating, bedroom, bathroom, livingroom);
                twoStar.write_two_star();
                System.out.println("--------------------------------------------------------------------ADDED A TWOSTAR HOTEL---------------------------------------------------------------");
                break;

            case 3:
                System.out.print("Enter number of bedrooms: ");
                bedroom = scanner.nextInt();
                System.out.print("Enter number of bathrooms: ");
                bathroom = scanner.nextInt();
                System.out.print("Enter number of living rooms: ");
                livingroom = scanner.nextInt();
                System.out.print("Enter number of televisions: ");
                television = scanner.nextInt();
                ThreeStar threeStar = new ThreeStar(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating, bedroom, bathroom, livingroom, television);
                threeStar.write_three_star();
                System.out.println("--------------------------------------------------------------------ADDED A THREESTAR HOTEL---------------------------------------------------------------");
                break;

            case 4:
                System.out.print("Enter number of bedrooms: ");
                bedroom = scanner.nextInt();
                System.out.print("Enter number of bathrooms: ");
                bathroom = scanner.nextInt();
                System.out.print("Enter number of living rooms: ");
                livingroom = scanner.nextInt();
                System.out.print("Enter number of televisions: ");
                television = scanner.nextInt();
                System.out.print("Enter number of spa facilities: ");
                spa = scanner.nextInt();
                System.out.print("Enter true if staff service is available, false otherwise: ");
                staffService = scanner.nextBoolean();
                FourStar fourStar = new FourStar(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating, bedroom, bathroom, livingroom, television, spa, staffService);
                fourStar.write_four_star();
                System.out.println("--------------------------------------------------------------------ADDED A FOURSTAR HOTEL---------------------------------------------------------------");
                break;

            case 5:
                System.out.print("Enter number of bedrooms: ");
                bedroom = scanner.nextInt();
                System.out.print("Enter number of bathrooms: ");
                bathroom = scanner.nextInt();
                System.out.print("Enter number of living rooms: ");
                livingroom = scanner.nextInt();
                System.out.print("Enter number of televisions: ");
                television = scanner.nextInt();
                System.out.print("Enter number of spa facilities: ");
                spa = scanner.nextInt();
                System.out.print("Enter true if staff service is available, false otherwise: ");
                staffService = scanner.nextBoolean();
                System.out.print("Enter number of pools: ");
                pools = scanner.nextInt();
                System.out.print("Enter number of fitness centers: ");
                fitnessCenter = scanner.nextInt();
                FiveStar fiveStar = new FiveStar(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating, bedroom, bathroom, livingroom, television, spa,staffService,pools,fitnessCenter);
                fiveStar.write_five_star();
                System.out.println("--------------------------------------------------------------------ADDED A FIVESTAR HOTEL---------------------------------------------------------------");
                break;

            default:
                System.out.println("Enter a valid choice:");
        }
    }

    public static void deleteRecords() {
        Scanner x = new Scanner(System.in);
        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\Customer.csv";
        boolean customerFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            System.out.print("\nCustomer name:");
            String c_name = x.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Hotel name:");
            String hotel_name = x.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.print("Check-out date:");
            String out_date = x.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while ((line = br.readLine()) != null) {
                String customerDetails[] = line.split(",");
                if (c_name.equalsIgnoreCase(customerDetails[0]) && hotel_name.equalsIgnoreCase(customerDetails[1]) && out_date.equals(customerDetails[3])) {
                    String h_name = customerDetails[1].trim();
                    String endDate = customerDetails[3].trim();
                    String rooms = customerDetails[5].trim();
                    int introom = Integer.parseInt(rooms);

                    Booking.checkOut(h_name, endDate, introom);
                    customerFound=true;
                }
                else if(c_name.equalsIgnoreCase(customerDetails[0]) && hotel_name.equalsIgnoreCase(customerDetails[1]) && !out_date.equals(customerDetails[3])){
                    System.out.println("~No checkout for the entered day!");
                    customerFound=true;
                    deleteRecords();
                    return;
                }
            }

            if (!customerFound) {
                System.out.println("\n~No customer booking found for the name: " + c_name.toUpperCase());
                deleteRecords();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        admin_page();
    }
}

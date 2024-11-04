package Dash;
import Login.Login;
import java.util.*;
import java.time.LocalDate;
import java.io.*;

class InsufficientRoomsException extends Exception {
    public InsufficientRoomsException(String message) {
        super(message);
    }
}

public class Booking {
    String name;
    String h_name;
    int persons;
    int rooms;

    public Booking(String name, String h_name,int persons, int rooms) {
        this.name = name;
        this.h_name = h_name;
        this.persons = persons;
        this.rooms = rooms;
    }

    public static void checkIn(String h_name,String startDate,String endDate){
        Scanner y = new Scanner(System.in);
        System.out.println("\n\n------------------------------------------------------------------------BOOKING DETAILS--------------------------------------------------------------");
        System.out.print("Name:");
        String name = y.nextLine();
        System.out.print("Number of persons staying:");
        int persons = y.nextInt();
        System.out.print("Number of Rooms:");
        int rooms = y.nextInt();

        LocalDate currentDate = LocalDate.now();
        String parsedcurrentdate = currentDate.toString();

        Booking booking = new Booking(name, h_name, persons, rooms);

        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";

        List<String[]> allData = new ArrayList<>();

        String currentslicing = parsedcurrentdate.substring(8, 10);
        String startdatesslicing = startDate.substring(8, 10);
        String enddatesslicing = endDate.substring(8,10);

        String currentmonthslicing = parsedcurrentdate.substring(5, 7);
        String startdatemonthslicing = startDate.substring(5, 7);

        int intcurrentslicing = Integer.parseInt(currentslicing);
        int intstartdateslicing = Integer.parseInt(startdatesslicing);
        int intenddateslicing = Integer.parseInt(enddatesslicing);

        int intcurrentmonthslicing = Integer.parseInt(currentmonthslicing);
        int intstartmonthslicing = Integer.parseInt(startdatemonthslicing);

        if(startDate.equals(parsedcurrentdate) || ((intcurrentslicing < intstartdateslicing) && (intcurrentmonthslicing == intstartmonthslicing)) || ((intcurrentslicing > intstartdateslicing) && ((intcurrentmonthslicing-intstartmonthslicing ) == 1||(intcurrentmonthslicing>intstartmonthslicing)))) {
            try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] hotelDetails = line.split(",");
                    String hotel = hotelDetails[1];
                    if (hotel.equalsIgnoreCase(h_name)) {
                        int roomfromcsv = Integer.parseInt(hotelDetails[4]);
                        if (roomfromcsv < booking.rooms) {
                            try{
                                throw new InsufficientRoomsException("\nThere are no sufficient rooms available");
                            }
                            catch (InsufficientRoomsException e) {
                                System.out.println(e.getMessage());
                                System.out.println("\nEnter a valid number of rooms (Rooms Available: " + hotelDetails[4] + "):");
                                checkIn(h_name,startDate,endDate);
                            }
                        }
                        else{
                            int roomfromcsvupdated = roomfromcsv - booking.rooms;
                            hotelDetails[4] = String.valueOf(roomfromcsvupdated);
                            System.out.println("\n--------------------------------------------------------------------------RECEIPT---------------------------------------------------------------------------");
                            System.out.println("\n                                                                          " + h_name.toUpperCase() + "                                                                          ");
                            System.out.println("\nNAME:" + name);
                            System.out.println("NO OF PERSONS:" + persons);
                            System.out.println("NO OF ROOMS BOOKED:" + rooms);
                            System.out.println("CHECK-IN DATE: " + startDate);
                            System.out.println("CHECK-OUT DATE: " + endDate);
                            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.println("TOTAL PRICE FOR THE STAY: "+ Double.parseDouble(hotelDetails[5])*rooms);
                            System.out.println("\n-------------------------------------------------------------------------THANK YOU!-------------------------------------------------------------------------");
                            String filepath1 = "C:\\Users\\Sweety\\Desktop\\Hotel\\Customer.csv";
                            try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath1, true))) {
                                bw.write(name + "," + h_name + "," + startDate + "," + endDate + "," + persons + "," + rooms);
                                bw.newLine();
                                bw.flush();
                            }
                            catch(IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    allData.add(hotelDetails);
                }

            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
            for(String[] data : allData){
                bw.write(String.join(",", data));
                bw.newLine();
            }
            bw.flush();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        Scanner x=new Scanner(System.in);
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("                                                                           LOGIN PAGE                                                                  \n");
        System.out.println("                                                              1.CUSTOMER     2.ADMIN     3.EXIT                                                                           ");
        
        System.out.println("\nYour Choice:");
        int choice = x.nextInt();
        x.nextLine();
        if(choice == 1){
            System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("                                                               1.SIGNUP           2.LOGIN                                                                           ");
            System.out.println("Enter Your Choice(1/2):");
            int ch=x.nextInt();
            if(ch==1){
                x.nextLine();
                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Name:");
                String customer= x.nextLine();
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Password:");
                String password = x.nextLine();
                Login.create_account(customer,password);
                return;
            }
            else if(ch==2){
                x.nextLine();
                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Name:");
                String customer= x.nextLine();
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Password:");
                String password = x.nextLine();
                Login.checkUser(customer,password);
            }
        }
        else if(choice == 2){
            Admin.admin_page();;
            return;
        }
        else if(choice == 3){
            System.exit(1);
        }
    }

    public static void checkOut(String h_name, String endDateUser, int rooms) {
        LocalDate currentDate = LocalDate.now();
        String parsedcurrentdate = currentDate.toString();

        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
        List<String[]> allData = new ArrayList<>();

        if (endDateUser.equals(parsedcurrentdate)) {
            try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] hotelDetails = line.split(",");
                    String hotel = hotelDetails[1];
                    if (hotel.equalsIgnoreCase(h_name)) {
                        int roomfromcsv = Integer.parseInt(hotelDetails[4]);
                        int roomfromcsvupdated = roomfromcsv + rooms;
                        hotelDetails[4] = String.valueOf(roomfromcsvupdated);
                    }
                    allData.add(hotelDetails);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (String[] data : allData) {
                bw.write(String.join(",", data));
                bw.newLine();
            }
            bw.flush();
            System.out.println("                                               CHECKED OUT SUCCESSFULLY!                                                      ");

            deleteCustomer(h_name, endDateUser, rooms);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCustomer(String h_name, String endDateUser, int rooms) {
        String customerFilepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\Customer.csv";
        List<String[]> customerData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(customerFilepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerDetails = line.split(",");
                String hotel = customerDetails[1];
                if (hotel.equalsIgnoreCase(h_name) && endDateUser.equals(customerDetails[3])) {
                    continue;
                }
                customerData.add(customerDetails);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(customerFilepath))) {
            for (String[] data : customerData) {
                bw.write(String.join(",", data));
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


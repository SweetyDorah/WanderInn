package Dash;
import java.util.*;
import hotel.Hotels;

public class Dashboard{
    static String destination;
    
    public void getDestination(){
        Scanner x=new Scanner(System.in);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Destination to search:");
        destination=x.nextLine();

        Hotels.CheckHotels(destination);

        x.close();

    }
}

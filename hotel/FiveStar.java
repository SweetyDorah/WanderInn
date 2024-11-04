package hotel;
import java.io.*;

public class FiveStar extends FourStar {
    int pools;
    int fitness_center;

    public FiveStar(String hotelType, String hotelName, String hotelPlace, String hotelContact, int hotelRooms, double hotelPrice, double hotelRating, int bedroom, int bathroom, int livingroom, int Television, int spa, boolean Staff_service, int pools, int fitness_center){
        super(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating, bedroom, bathroom, livingroom, Television, spa, Staff_service);
        this.pools=pools;
        this.fitness_center=fitness_center;
    }

    public void write_five_star(){
        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))){
            bw.write(hotelType + "," + hotelName + "," + hotelPlace + "," + hotelContact + "," + hotelRooms + "," + hotelPrice + "," + hotelRating + "," + bedroom + "," + bathroom + "," + livingroom + "," + Television + "," + spa + "," + Staff_service + "," + pools + "," + fitness_center);
            bw.newLine();
            bw.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
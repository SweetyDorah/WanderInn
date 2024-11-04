package hotel;
import java.io.*;

public class FourStar extends ThreeStar {
    int spa;
    boolean Staff_service;

    public FourStar(String hotelType, String hotelName, String hotelPlace, String hotelContact, int hotelRooms, double hotelPrice, double hotelRating, int bedroom, int bathroom, int livingroom, int Television, int spa, boolean Staff_service){
        super(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating, bedroom, bathroom, livingroom, Television);
        this.spa=spa;
        this.Staff_service=Staff_service;
    }

    public void write_four_star(){
        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            bw.write(hotelType + "," + hotelName + "," + hotelPlace + "," + hotelContact + "," + hotelRooms + "," + hotelPrice + "," + hotelRating + "," + bedroom + "," + bathroom + ","+ livingroom + "," + Television + "," + spa + "," + Staff_service);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
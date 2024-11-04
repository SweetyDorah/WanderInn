package hotel;
import java.io.*;

public class TwoStar extends OneStar {
    int livingroom;

    public TwoStar(String hotelType, String hotelName, String hotelPlace, String hotelContact, int hotelRooms, double hotelPrice, double hotelRating, int bedroom, int bathroom, int livingroom){
        super(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating, bedroom, bathroom);
        this.livingroom=livingroom;
    }

    public void write_two_star(){
        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            bw.write(hotelType + "," + hotelName + "," + hotelPlace + "," + hotelContact + "," + hotelRooms + "," + hotelPrice + "," + hotelRating + ","+ bedroom+ ","+ bathroom +","+ livingroom);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
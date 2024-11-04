package hotel;
import java.io.*;

public class ThreeStar extends TwoStar {
    int Television;

    public ThreeStar(String hotelType, String hotelName, String hotelPlace, String hotelContact, int hotelRooms, double hotelPrice, double hotelRating, int bedroom, int bathroom, int livingroom, int Television){
        super(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating,bedroom,bathroom,livingroom);
        this.Television=Television;
    }

    public void write_three_star(){
        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            bw.write(hotelType + "," + hotelName + "," + hotelPlace + "," + hotelContact + "," + hotelRooms + "," + hotelPrice + "," + hotelRating + "," + bedroom + "," + bathroom + ","+ livingroom +","+ Television);
            bw.newLine();
            bw.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

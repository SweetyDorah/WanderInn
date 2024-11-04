package hotel;
import java.io.*;

public class OneStar extends Hotels{
    int bedroom;
    int bathroom;

    public OneStar(String hotelType, String hotelName, String hotelPlace, String hotelContact, int hotelRooms, double hotelPrice, double hotelRating, int bedroom, int bathroom) {
        super(hotelType, hotelName, hotelPlace, hotelContact, hotelRooms, hotelPrice, hotelRating);
        this.bedroom = bedroom;
        this.bathroom = bathroom;
    }

    public void write_one_star(){
        String filepath = "C:\\Users\\Sweety\\Desktop\\Hotel\\hotels.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            bw.write(hotelType + "," + hotelName + "," + hotelPlace + "," + hotelContact + "," + hotelRooms + "," + hotelPrice + "," + hotelRating + "," + bedroom + "," + bathroom);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

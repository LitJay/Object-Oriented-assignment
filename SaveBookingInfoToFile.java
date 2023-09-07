import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveBookingInfoToFile {

    ArrayList<String> passengerList = new ArrayList<>();

   
    public SaveBookingInfoToFile(String name, String trainNumber, String seatNumber, String journeyDate, String time, String source, String destination) {
        passengerList.add(name);
        passengerList.add(trainNumber);
        passengerList.add(seatNumber);
        passengerList.add(journeyDate);
        passengerList.add(time);
        passengerList.add(source);
        passengerList.add(destination);
    }

    public void saveToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < passengerList.size(); i++) {
                String data = passengerList.get(i);

                writer.write(data);

                if (i < passengerList.size() - 1) {
                    writer.write(",");
                } else {
                    writer.write("\n");
                }
            }

            writer.flush();
        }
    }
}

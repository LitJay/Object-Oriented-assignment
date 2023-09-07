import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Seats {
    private String[][] TrainSeats = {{"ROW 1", "COLUMN 1"}, {"ROW 2", "COLUMN 1"}, {"ROW 3", "COLUMN 1"}, {"ROW 4", "COLUMN 1"}, {"ROW 5", "COLUMN 1"},
            {"ROW 1", "COLUMN 2"}, {"ROW 2", "COLUMN 2"}, {"ROW 3", "COLUMN 2"}, {"ROW 4", "COLUMN 2"}, {"ROW 5", "COLUMN 2"},
            {"ROW 1", "COLUMN 3"}, {"ROW 2", "COLUMN 3"}, {"ROW 3", "COLUMN 3"}, {"ROW 4", "COLUMN 3"}, {"ROW 5", "COLUMN 3"},
            {"ROW 1", "COLUMN 4"}, {"ROW 2", "COLUMN 4"}, {"ROW 3", "COLUMN 4"}, {"ROW 4", "COLUMN 4"}, {"ROW 5", "COLUMN 4"},
            {"ROW 1", "COLUMN 5"}, {"ROW 2", "COLUMN 5"}, {"ROW 3", "COLUMN 5"}, {"ROW 4", "COLUMN 5"}, {"ROW 5", "COLUMN 5"},
            {"ROW 1", "COLUMN 6"}, {"ROW 2", "COLUMN 6"}, {"ROW 3", "COLUMN 6"}, {"ROW 4", "COLUMN 6"}, {"ROW 5", "COLUMN 6"},
            {"ROW 1", "COLUMN 7"}, {"ROW 2", "COLUMN 7"}, {"ROW 3", "COLUMN 7"}, {"ROW 4", "COLUMN 7"}, {"ROW 5", "COLUMN 7"},
            {"ROW 1", "COLUMN 8"}, {"ROW 2", "COLUMN 8"}, {"ROW 3", "COLUMN 8"}, {"ROW 4", "COLUMN 8"}, {"ROW 5", "COLUMN 8"},
            {"ROW 1", "COLUMN 9"}, {"ROW 2", "COLUMN 9"}, {"ROW 3", "COLUMN 9"}, {"ROW 4", "COLUMN 9"}, {"ROW 5", "COLUMN 9"},
            {"ROW 1", "COLUMN 10"}, {"ROW 2", "COLUMN 10"}, {"ROW 3", "COLUMN 10"}, {"ROW 4", "COLUMN 10"}, {"ROW 5", "COLUMN 10"}};

    private static String[][] trainSeats = new String[5][10];
    private static final String FILE_PATH = "/Users/litjay/Downloads/JavaAssignment/";
    private static final String FILE_EXTENSION = ".txt";

    private List<String> stationNamesA;
    private List<TrainScheduleReader.TrainSchedule> trainSchedulesA;
    private List<String> stationNamesB;
    private List<TrainScheduleReader.TrainSchedule> trainSchedulesB;
    private static int totalSeats;
    private int availableSeats;
    SearchTrain trainInfo = new SearchTrain();

    public String[][] getTrainSeats() {
        return TrainSeats;
    }

    public static int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public Seats(int totalSeats) throws IOException {
        // Perform file reading in the constructor
        this.stationNamesA = TrainScheduleReader.readStations("StationTumpat->Gemas.txt");
        this.trainSchedulesA = TrainScheduleReader.readTrainSchedules("Tumpat->Gemas.txt");
        this.stationNamesB = TrainScheduleReader.readStations("StationGemas-Tumpat.txt");
        this.trainSchedulesB = TrainScheduleReader.readTrainSchedules("Gemas->Tumpat.txt");

        Seats.totalSeats = totalSeats;
        this.availableSeats = totalSeats;

        trainSeats = new String[5][10];
    }

    public void displayTrainSchedulesA() {
        for (TrainScheduleReader.TrainSchedule schedule : trainSchedulesA) {
            System.out.println("Train Model: " + schedule.trainModel);
            System.out.println("Station |  Arrival Times");

            for (String time : schedule.arrivalTimes) {
                System.out.println("  " + time);
            }
            System.out.println(); // Separate schedules with a blank line
        }
    }

    public void displayTrainSchedulesB() {
        int maxWidth = 0;
        for (String station : stationNamesB) {
            if (station.length() > maxWidth) {
                maxWidth = station.length();
            }
        }

        for (TrainScheduleReader.TrainSchedule schedule : trainSchedulesB) {
            System.out.println("Train Model:" + schedule.trainModel);
            System.out.println("Stations        |Arrival Times:");
            System.out.println();
            for (int i = 0; i < stationNamesB.size(); i++) {
                String station = stationNamesB.get(i);
                String time = schedule.arrivalTimes.get(i);
                String formattedStation = String.format("%-" + maxWidth + "s", station);
                System.out.println(i + "." + formattedStation + time);
            }
            System.out.println();
        }
    }

    public void displayTrainStationB() {
        for (String station : stationNamesB) {
            System.out.println("Station Name: " + station);
        }
    }

    public void displaySeatsTrain() {
        for (String[] trainseats : TrainSeats) {
            System.out.println(trainseats[0] + " " + trainseats[1]);
        }
    }

    public static boolean isValidSeat(int row, int column) {
        return row >= 1 && row <= 5 && column >= 1 && column <= 10;
    }

    public boolean isSeatBooked(int row, int column) {
        if (isValidSeat(row, column)) {
            return trainSeats[row - 1][column - 1].equals("Booked");
        }
        return false;
    }

    public void bookSeat(int row, int column, String trainModel, String date) {
        System.out.println(trainSeats[row - 1][column - 1]); // Debug print
        if (isValidSeat(row, column)) {
            if (trainSeats[row - 1][column - 1].equals("Available")) {
                trainSeats[row - 1][column - 1] = "Booked";
                System.out.println("Booked Successfully!");
                try {
                    saveBookingStatus(trainModel, date);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (trainSeats[row - 1][column - 1].equals("Booked")) {
                System.out.println("Invalid seat or seat already booked. Please choose another seat.");
            }
        } else {
            System.out.println("Invalid seat. Please choose a valid seat.");
        }
    }

    public void printSeatStatus() {
			for(int row = 0; row < 5; row++){
                    for (int column = 0; column < 10; column++) {
                System.out.println("Row" + (row + 1) + ", Column " + (column + 1) + ": " + trainSeats[row][column] + " ");
            }
            System.out.println();
        }
    }

    // Load booking status from file
    public static void loadBookingStatus(String trainModel, String date) throws IOException {
        String filePath = FILE_PATH + trainModel + "_" + date + FILE_EXTENSION;
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                int row = 0;
                while ((line = reader.readLine()) != null && row < 5) {
                    String[] status = line.split(",");
                    for (int col = 0; col < 10 && col < status.length; col++) {
                        trainSeats[row][col] = status[col];
                        if (status[col].equals("Booked")) {
                            totalSeats -= 1;
                        }
                    }
                    row++;
                }
            }
        } else {
            // Initialize all seats to "Available" if the file doesn't exist
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 10; col++) {
                    trainSeats[row][col] = "Available";
                }
            }
        }
    }

    // Save booking status to a file
    private static void saveBookingStatus(String trainModel, String date) throws IOException {
        String filePath = FILE_PATH + trainModel + "_" + date + FILE_EXTENSION;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 10; col++) {
                    writer.write(trainSeats[row][col]);
                    if (col < 9) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
        }
    }

    // Set seats to "Available" based on seat numbers
    public static void setSeatsToAvailable(String trainModel, String date, String[] seatNumbers) {
        if (seatNumbers == null) {
            return;
        }

        // Load the current booking status from the file
        try {
            loadBookingStatus(trainModel, date);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String seatNumber : seatNumbers) {
            // Split each seat number into row and column
            String[] parts = seatNumber.split("-");
            if (parts.length == 2) {
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);

                // Check if the seat is valid
                if (isValidSeat(row, col)) {
                    // Set the seat to "Available"
                    trainSeats[row - 1][col - 1] = "Available";
                } else {
                    System.out.println("Invalid seat information: " + seatNumber);
                }
            } else {
                System.out.println("Invalid seat information format: " + seatNumber);
            }
        }

        // Save the updated booking status to a file
        try {
            saveBookingStatus(trainModel, date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

           

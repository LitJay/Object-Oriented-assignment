import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class cancelBooking {
    public static String trainNumber;
    public static String journeyDate;

    // Delete passenger information by name from the passenger_Info.txt file
   public static boolean deletePassengerInfo(String name) {
    ArrayList<String> dataList = new ArrayList<>();
    boolean found = false;
    String fileName = name + ".txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        int lineNumber = 1; // To display line numbers to the user
        while ((line = reader.readLine()) != null) {
            // Split the line into fields using commas as the delimiter
            String[] fields = line.split(",");

            if (fields.length == 7) {
                dataList.add(line);
            } else {
                System.err.println("Invalid data format in line " + lineNumber + ": " + line);
            }
            lineNumber++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    if (!dataList.isEmpty()) {
        // Display the available bookings to the user and let them choose which one to cancel
        System.out.println("Available bookings for " + name + ":");
        for (int i = 0; i < dataList.size(); i++) {
            String[] fields = dataList.get(i).split(",");
            System.out.println((i + 1) + ". From: " + fields[5] + " To: " + fields[6]);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the booking you want to cancel: ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= dataList.size()) {
            // Remove the selected booking
            dataList.remove(choice - 1);

            try (FileWriter writer = new FileWriter(fileName)) {
                for (String line : dataList) {
                    writer.write(line + "\n");
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            found = true;
        } else {
            System.out.println("Invalid choice. No booking was canceled.");
        }
    } else {
        System.out.println("No bookings found for " + name + ".");
    }

    return found;
}

    // Delete the ticket HTML file for a passenger
    public static boolean DeleteTicket(String passengerName) {
        // Generate the filename based on passengerName
        String filename = passengerName + "_ticket.html";

        // Create a File object for the ticket file
        File ticketFile = new File(filename);

        if (ticketFile.exists()) {
            // Delete the file if it exists
            if (ticketFile.delete()) {
                System.out.println("Booking for passenger " + passengerName + " has been canceled, and the ticket has been deleted.");
                return true;
            } else {
                System.err.println("Failed to delete the ticket file.");
                return false;
            }
        } else {
            System.out.println("Booking for passenger " + passengerName + " does not exist.");
            return false;
        }
    }

    // Get seat information for a passenger by name
    public static String[] getSeatInfo(String name) {
        String FileName=name+".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length == 7 && fields[0].equals(name)) {
                    return fields[2].split("-"); // Split the seat info into row and column
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Passenger not found
    }
}

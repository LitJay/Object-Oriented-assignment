import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ConvertTicket {
    String passengerName;
    String trainNumber;
    String seatNumber;
    String journeyDate;
    String source;
    String destination;

    // Constructor to initialize ticket details
    public ConvertTicket(String passengerName, String trainNumber, String seatNumber, String journeyDate,
            String time, String source, String destination) {
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.seatNumber = seatNumber;
        this.journeyDate = journeyDate;
        this.source = source;
        this.destination = destination;
    }

    // Create an HTML ticket with random ticket number and letters
    public void createHtmlTicket() {
        // Generate a random number
        Random random = new Random();
        int randomNumber = random.nextInt(100000);
        String formattedNumber = String.format("%05d", randomNumber);

        // Generate random letters
        StringBuilder randomLetters = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int asciiValue = random.nextInt(26) + 65; // Random uppercase letter (A-Z)
            randomLetters.append((char) asciiValue);
        }

        try {
            // Write ticket information to an HTML file
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.passengerName + "_ticket.html"));
            writer.write("<html><head><style>body { font-family: Arial, sans-serif; } h2 { color: red; }</style></head><body>");
            writer.write("<h2>Train Ticket</h2>");
            writer.write("<p><strong>Ticket No:" + randomLetters.toString() + formattedNumber + "<p>");
            writer.write("<p><strong>Passenger Name:</strong> " + this.passengerName + "</p>");
            writer.write("<p><strong>Train Number:</strong> " + this.trainNumber + "</p>");
            writer.write("<p><strong>Seat Number:</strong> " + this.seatNumber + "</p>");
            writer.write("<p><strong>Journey Date:</strong> " + this.journeyDate + "</p>");
            writer.write("<p><strong>Source:</strong> " + this.source + "</p>");
            writer.write("<p><strong>Destination:</strong> " + this.destination + "</p>");
            writer.write("</body></html>");
            writer.close();
            System.out.println("HTML ticket created successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

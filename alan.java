import java.util.Scanner;
import java.util.List;




public class TrainBookingSystem {
    public static void main(String[] args) {

        // Display main menu
        displayMenu();
    }

    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("===== Train (ETS / InterCity) Booking System Menu =====");
            System.out.println("1. Book a Ticket");
            System.out.println("2. Cancel a Booking");
            System.out.println("3. View Available Trains");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            // Input validation
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.next();
            }

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Ticket booking
                    break;
                case 2:
                    // Ticket cancellation
                    break;
                case 3:
                    // View available train
                    break;
                case 4:
                    System.out.println("Exiting the Train Booking System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        } while (choice != 4);

        scanner.close();
    }

}



public class TrainBookingSystem {

    public static void main(String[] args) {
        displayMenu();
    }

    public static void bookTicket() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Ticket Booking =====");
        System.out.print("Enter the train station: ");
        int trainStation = scanner.nextInt();

        // Check if the specified train exists
        Train selectedTrain = findTrain(trainStation);

        if (selectedTrain != null) {
            System.out.print("Enter the date (YYYY-MM-DD): ");
            String date = scanner.next();

            List<Seat> availableSeats = getAvailableSeats(selectedTrain, date);

            if (!availableSeats.isEmpty()) {
                System.out.println("Available Seats:");
                for (Seat seat : availableSeats) {
                    System.out.println("Seat Number: " + seat.getSeatNumber());
                }

                System.out.print("Enter the seat number you want to book: ");
                int seatNumber = scanner.nextInt();

                // Check for the seat number availablity
                Seat selectedSeat = findSeat(selectedTrain, date, seatNumber);

                if (selectedSeat != null && !selectedSeat.isBooked()) {
                    System.out.print("Enter passenger name: ");
                    String passengerName = scanner.next();

                    // Book the seat
                    selectedSeat.setPassengerName(passengerName);
                    selectedSeat.setBooked(true);
                    System.out.println("Ticket booked successfully!");
                } else {
                    System.out.println("I'm sorry. Seat is already booked or invalid.");
                }
            } else {
                System.out.println("No available seats for the specified date.");
            }
        } else {
            System.out.println("Train not found.");
        }

        scanner.close();
    }

}


public class TrainBookingSystem {

    public static void main(String[] args) {
        
        // Display main menu
        displayMenu();
    }

    public static void cancelTicket() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Ticket Cancellation =====");
        System.out.print("Enter the booking ID or passenger name: ");
        String identifier = scanner.next();

        // Find the booking by the provided identifier
        Booking bookingToCancel = findBooking(identifier);

        if (bookingToCancel != null) {
            // Cancel the booking by updating the seat status
            Seat bookedSeat = bookingToCancel.getSeat();
            bookedSeat.setPassengerName(null);
            bookedSeat.setBooked(false);


            System.out.println("Ticket canceled successfully!");
        } else {
            System.out.println("Booking not found. Please check your booking ID or passenger name.");
        }

        scanner.close();
    }


}


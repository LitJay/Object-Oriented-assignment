import java.util.List;
import java.util.Scanner;

import java.io.*;

public class TrainBookingSystem {
    static Scanner scanner = new Scanner(System.in);
    static int  choice;
    static int Accountchoice;
    private static UserAccount currentUser = null;
    static boolean SignIn=false;
    static  String userName;
    static  String password;
    public static void main(String[] args) {
        // Display main menu
        displayMenu();
    }

    // Display the main menu and handle user choices
    public static void displayMenu() {
        while (true) {
            System.out.println("===== Train (ETS / InterCity) Booking System =====");
            if (currentUser == null) {
                System.out.println("1. Register");
                System.out.println("2. Sign In");
                System.out.println("3. Exit");
            } else {
                System.out.println("1. Book a Ticket");
                System.out.println("2. Cancel a Booking");
                System.out.println("3. View Available Trains");
                System.out.println("4. View Account");
                System.out.println("5. Sign Out");
                System.out.println("6. Exit");
            }
            System.out.print("Enter your choice: ");
    
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            }
    
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over
    
            switch (choice) {
                case 1:
                    // Ticket booking

                    if (currentUser != null && SignIn) {
                        ViewTimetable.getInfoAndPrintTimeTable();
                        bookTicket();
                        System.out.println("Press Enter to return to the main menu...");
                        String enterKey1 = scanner.nextLine();
                        if (enterKey1.isEmpty()) {
                            break; // Exit the loop and return to the main menu
                        }
                    }
                    else if (currentUser == null) {
                        System.out.println("================Register=========================");
                        System.out.println("Please Enter Your");
                        System.out.println("Username:");
                        String UserName= scanner.nextLine();
                        System.out.println("Password:");
                        String Password=scanner.nextLine();
                        System.out.println("Name:");
                        String Name=scanner.nextLine();
                        System.out.println("Phone Number:");
                        String PhoneNumber=scanner.nextLine();
                        System.out.println("Email:");
                        String Email=scanner.nextLine(); 
                        UserAccount.registerUser(UserName,Password,Name,PhoneNumber,Email);
                    SignIn= UserAccount.signIn(UserName, Password);
                    userName=UserName;
                    password=Password;
                    currentUser = UserAccount.getUserByUsername(UserName);
                    }
                    else {
                        System.out.println("Please sign in to book a ticket.");
                    }
                    break;
                case 2:
                    if (currentUser == null) {
                        boolean WrongInfo=false;
                        do{
                        if (currentUser == null) {
                           if(WrongInfo==true)
                        {
                                System.out.println("Wrong UserName or Password, Please Key In Again!");
                        }
                            System.out.println("Please Enter Your:");
                            System.out.println("Username:");
                            String UserName= scanner.nextLine();
                            System.out.println("Password:");
                            String Password=scanner.nextLine();
                           SignIn= UserAccount.signIn(UserName,Password);
                        if(SignIn==false)
                        {
                            WrongInfo=true;
                        }
                           userName=UserName;
                           password=Password;
                           currentUser = UserAccount.getUserByUsername(UserName);
                        } else {
                            System.out.println("You are already signed in.");
                            break;
                        }
                    }while(SignIn==false);
                    } else {
                        // Cancel Booking
                        if (SignIn) {
                            cancelBooking(userName);
                            System.out.println("Press Enter to return to the main menu...");
                            String enterKey2 = scanner.nextLine();
                            if (enterKey2.isEmpty()) {
                                break; // Exit the loop and return to the main menu
                            }
                        } else {
                            System.out.println("Please sign in to cancel a booking.");
                        }
                    }
                    break;
                case 3:
                    // View available trains
                    if (currentUser != null && SignIn) {
                        SearchTrain searchTrain = new SearchTrain();
                        ViewTimetable.getInfoAndPrintTimeTable();
                        searchTrain.getInfo();
                        searchTrain.fetchAndDisplayTrainSchedules();
                        System.out.println("Press Enter to return to the main menu...");
                        String enterKey3 = scanner.nextLine();
                        if (enterKey3.isEmpty()) {
                            break; // Exit the loop and return to the main menu
                        }
                    } else {
                        System.out.println("Please sign in to view available trains.");
                    }
                    break;
                case 4:
                    // View Account
                    if (currentUser != null && SignIn) {
                        viewAccount(userName, password);
                    } else {
                        System.out.println("Please sign in to view your account.");
                    }
                    break;
                case 5:
                    // Sign Out
                    if (currentUser != null) {
                        currentUser = null; // Sign out by setting currentUser to null
                        System.out.println("You have been signed out.");
                    } else {
                        System.out.println("You are not currently signed in.");
                    }
                    break;
                case 6:
                    // Exit the program
                    System.out.println("Exiting the Train Booking System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    // Book a train ticket
    public static void bookTicket() {
        try {
            Scanner input = new Scanner(System.in);
            SearchTrain searchTrain = new SearchTrain();

            System.out.println("===== Ticket Booking =====");
            searchTrain.getInfo();
            searchTrain.fetchAndDisplayTrainSchedules();

            // Collect passenger information


            // Collect train model information
            String model;
            boolean validModel = false;
            int index = -1;

            do {
                System.out.println("Please Choose Your Train Model:");
                model = input.nextLine();

                validModel = false; // Reset validModel flag for each iteration

                for (int i = 0; i < searchTrain.trainmodel.size(); i++) {
                    String trainModelInList = searchTrain.trainmodel.get(i);
                    if (trainModelInList != null && trainModelInList.equals(model)) {
                        validModel = true;
                        index = i;
                        break; // Exit loop when a valid model is found
                    }
                }

                if (!validModel) {
                    System.out.println("Invalid Train Model. Try again.");
                }
            } while (!validModel);

            if (index == -1) {
                System.out.println("No valid train model found.");
                return;
            }

            String time = searchTrain.getStartTime().get(index);

            // Collect date information
            System.out.print("Enter the date (YYYY/MM/DD): ");
            String date;
            String modifiedDate;
            boolean validDate = false;

            do {
                date = input.nextLine();
                modifiedDate = date.replaceAll("[/]", "");
                validDate = modifiedDate.matches("\\d{8}"); // checks if the date format is correct after removing slashes
                if (!validDate) {
                    System.out.println("Invalid Date. Try again.");
                }
            } while (!validDate);

            Seats seats= new Seats(50);
            Seats.loadBookingStatus(model, date);

            int stationBetweens = searchTrain.numberBetween();
            System.out.println("The List Below shows the Available and Booked Seats:");
            seats.displaySeatsTrain();

            // Collect seat information
            boolean validSeat = false;
            int seatRow, seatColumn;

            do {
                System.out.print("Choose a Row: ");
                seatRow = input.nextInt();
                System.out.print("Choose a Column: ");
                seatColumn = input.nextInt();
                validSeat = Seats.isValidSeat(seatRow, seatColumn);
                if (!validSeat) {
                    System.out.println("Invalid Seat or Already Taken. Try again.");
                }
            } while (!validSeat);

            seats.bookSeat(seatRow, seatColumn, model, modifiedDate);

            TrainFare fee = new TrainFare(stationBetweens);
            double feeValue = fee.computeFare(scanner);
            System.out.println("Total is: RM" + feeValue);

            // Collect payment method information
            int choice;

            do {
                System.out.println("Choose Your Payment Method:");
                System.out.println("1. Credit/Debit Card");
                System.out.println("2. Boost/Touch'n Go/Grab E-wallet");
                System.out.println("3. Online Payment");
                choice =  input.nextInt();

                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid Choice! Choose Again.");
                }
            } while (choice < 1 || choice > 3);

            System.out.println("Payment Successful! Enjoy Your Trip!");

            String finalSeatNumber = seatRow + "-" + seatColumn;
            ConvertTicket ticket = new ConvertTicket(userName, model, finalSeatNumber, date, time, SearchTrain.From, SearchTrain.To);
            SaveBookingInfoToFile BookingInfo = new SaveBookingInfoToFile(userName, model, finalSeatNumber, date, time, SearchTrain.From, SearchTrain.To);
            String fileName= userName+".txt";
            BookingInfo.saveToFile(fileName);

            ticket.createHtmlTicket();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cancel a train ticket
    public static void cancelBooking(String passengerName) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Ticket Cancellation =====");
        
        cancelBooking.deletePassengerInfo(passengerName);
        String[] seatNumber = cancelBooking.getSeatInfo(passengerName);
        Seats.setSeatsToAvailable(cancelBooking.trainNumber, cancelBooking.journeyDate, seatNumber);

        // Cancel the ticket and delete the corresponding line in the file
        cancelBooking.DeleteTicket(passengerName);
    }

    private static void viewAccount(String username,String password) 
    {
        
        List<UserAccount> users = UserAccount.loadUserAccounts();
        for (UserAccount user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                
                System.out.println("===== View Account =====");
                System.out.println("Username: " + user.username);
                System.out.println("Name: " + user.name);
                System.out.println("Phone Number: " + user.phoneNumber);
                System.out.println("Email: " + user.email);
                System.out.println("1. Change User Info");
                System.out.println("2. Back to Main Menu");
                System.out.print("Enter your choice: ");
    
                int accountChoice = scanner.nextInt();
                scanner.nextLine();  // Consume newline left-over
    
        switch (accountChoice) {
            case 1:
                // Change User Info
                System.out.println("Please Choose Which Info You Wants to Change");
                System.out.println("1.UserName");
                System.out.println("2.Password");
                System.out.println("3.Name");
                System.out.println("4.PhoneNumber");
                System.out.println("5.Email");
                String Reminder="Your User Info Has been Updated.Please Go back to main menu to view your changes!";
               int userChoice=scanner.nextInt();
               scanner.nextLine();
               if(userChoice==1)
               {
                    System.out.println("Please Enter New UserName:");
                    String NewUsername=scanner.nextLine();
                    UserAccount.updateAccountInfo(userName,NewUsername, null, null,null, null);
                    System.out.println(Reminder);
                    break;
               }
               if(userChoice==2)
               {
                System.out.println("Please Enter New Password:");
                    String NewPassword=scanner.nextLine();
                     UserAccount.updateAccountInfo(userName,null,NewPassword,null,null,null);
                     System.out.println(Reminder);
                     break;
               }
               if(userChoice==3)
               {
                 System.out.println("Please Enter New Name: ");
                 String newName=scanner.nextLine();
                  UserAccount.updateAccountInfo(userName,null,null,newName,null,null);
                  System.out.println(Reminder);
                  break;
               }
               if(userChoice==4)
               {
                 System.out.println("Please Enter New Phone Number: ");
                 String newPhoneNumber=scanner.nextLine();
                  UserAccount.updateAccountInfo(userName,null,null,null,newPhoneNumber,null);
                  System.out.println(Reminder);
                  break;
               }
               if(userChoice==5)
               {
                System.out.println("Please Enter New Emial: ");
                String newEmail=scanner.nextLine();
                  UserAccount.updateAccountInfo(userName,null,null,null,null,newEmail);
                  System.out.println(Reminder);
                  break;
               }
               else
               {
                System.out.println("Invalid Choice. Please Try Again!");
                break;
               }
            case 2:
                // Back to Main Menu
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

}
    }
}


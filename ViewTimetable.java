import java.io.IOException;
import java.util.*;

public class ViewTimetable {

    public static void getInfoAndPrintTimeTable() {
        // Define file paths for train timetables and station names
        String timetableFileA = "/Users/litjay/Downloads/JavaAssignment/Tumpat->Gemas.txt";
        String stationFileA = "/Users/litjay/Downloads/JavaAssignment/StationTumpat->Gemas.txt";
        String stationFileB = "/Users/litjay/Downloads/JavaAssignment/StationGemas-Tumpat.txt";
        String timetableFileB = "/Users/litjay/Downloads/JavaAssignment/Gemas->Tumpat.txt";

        System.out.println("Which Time Table Would You Like To View?");
        System.out.println("1.Tumpat->Gemas");
        System.out.println("2.Gemas->Tumpat");
        int choice = 0;
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        int shiftPositions = 1;
        do {
            if (validInput) {
                System.out.println("Valid Choice! Please Enter Again");
            }

            System.out.print("Enter Your Choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    try {
                        List<TrainScheduleReader.TrainSchedule> schedulesA = TrainScheduleReader.readTrainSchedules(timetableFileA);
                        List<String> stationNamesA = TrainScheduleReader.readStations(stationFileA);
                        printTimetable(schedulesA, stationNamesA, shiftPositions);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        List<TrainScheduleReader.TrainSchedule> schedulesB = TrainScheduleReader.readTrainSchedules(timetableFileB);
                        List<String> stationNamesB = TrainScheduleReader.readStations(stationFileB);
                        ViewTimetable.printTimetable(schedulesB, stationNamesB, shiftPositions);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        } while (choice != 1 && choice != 2);
    }

    public static void printTimetable(List<TrainScheduleReader.TrainSchedule> schedules, List<String> stationNames, int shiftPositions) {
        int maxTrainModelLength = getMaxTrainModelLength(schedules);

        // Shift the arrival times column by the specified number of positions
        for (TrainScheduleReader.TrainSchedule schedule : schedules) {
            shiftArrivalTimes(schedule.arrivalTimes, shiftPositions);
        }
        List<String> shiftedStationNames = new ArrayList<>(stationNames);
        for (int i = 0; i < shiftPositions; i++) {
            shiftedStationNames.add(0, ""); // Add an empty string as a placeholder
        }

        // Print header with station names and train models
        System.out.printf("%-20s", ""); // Blank space for alignment
        for (TrainScheduleReader.TrainSchedule schedule : schedules) {
            System.out.printf("%-20s", formatTrainModel(schedule.trainModel, maxTrainModelLength));
        }
        System.out.println();

        // Print arrival times for each station and train model
        for (String station : shiftedStationNames) {
            System.out.printf("%-20s", formatStation(station));
            for (TrainScheduleReader.TrainSchedule schedule : schedules) {
                List<String> arrivalTimes = schedule.arrivalTimes;
                String arrivalTime = "-";
                if (arrivalTimes.size() > shiftedStationNames.indexOf(station)) {
                    arrivalTime = arrivalTimes.get(shiftedStationNames.indexOf(station));
                }
                System.out.printf("%-20s", formatArrivalTime(arrivalTime));
            }
            System.out.println();
        }
    }

    private static void shiftArrivalTimes(List<String> arrivalTimes, int positions) {
        for (int i = 0; i < positions; i++) {
            arrivalTimes.add(0, "--------------");
        }
    }

    private static int getMaxTrainModelLength(List<TrainScheduleReader.TrainSchedule> schedules) {
        int maxLength = 0;
        for (TrainScheduleReader.TrainSchedule schedule : schedules) {
            int length = schedule.trainModel.length();
            if (length > maxLength) {
                maxLength = length;
            }
        }
        return maxLength;
    }

    private static String formatStation(String station) {
        return station;
    }

    private static String formatTrainModel(String trainModel, int maxLength) {
        return String.format("%-" + maxLength + "s", trainModel);
    }

    private static String formatArrivalTime(String arrivalTime) {
        return arrivalTime;
    }
}

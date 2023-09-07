import java.io.IOException;
import java.time.LocalTime;
import java.time.Duration;
import java.util.*;

public class SearchTrain {

    private static String from;
    private static String to;
    private int startStationIndex = -1;
    private int endStationIndex = -1;
    String startTime;
    private String endTime;
    public static boolean RouteFound=false;
    boolean TrainModelFound=false;
    static int numberBetween;
    public static String trainModel;
    public static String Date;
    public static String From;
    public static String To;
    ArrayList<String> StartTime=new ArrayList();
    ArrayList<String> trainmodel=new ArrayList();
    Scanner input = new Scanner(System.in);

    TrainScheduleReader read= new TrainScheduleReader();
    int index_A;
    int index_B;

    // Get user input for 'from' and 'to' stations
    public void getInfo() {
        System.out.println("Press Enter To Exit or Enter Your Station");
        System.out.println("From: ");
        from = input.nextLine();
        From= from;
        System.out.println("To: ");
        to = input.nextLine();
        To=to;
    }

    // Get the number of stations between 'from' and 'to'
    public int numberBetween() {
        return numberBetween;
    }

    // Fetch and display train schedules based on the provided stations
    public void fetchAndDisplayTrainSchedules() {
        try {
            List<String> stationNamesA =  TrainScheduleReader.readStations("/Users/litjay/Downloads/JavaAssignment/StationTumpat->Gemas.txt");
            List<TrainScheduleReader.TrainSchedule> trainSchedulesA = TrainScheduleReader.readTrainSchedules("/Users/litjay/Downloads/JavaAssignment/Tumpat->Gemas.txt");
            searchAndDisplaySchedules(stationNamesA, trainSchedulesA);
            List<String> stationNamesB = TrainScheduleReader.readStations("/Users/litjay/Downloads/JavaAssignment/StationGemas-Tumpat.txt");
            List<TrainScheduleReader.TrainSchedule> trainSchedulesB = TrainScheduleReader.readTrainSchedules("/Users/litjay/Downloads/JavaAssignment/Gemas->Tumpat.txt");

            if (RouteFound == false) {
                searchAndDisplaySchedules(stationNamesB, trainSchedulesB);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the list of train departure times
    public ArrayList<String> getStartTime() {
        return StartTime;
    }

    // Search and display train schedules for the provided stations
    private void searchAndDisplaySchedules(List<String> stationNames, List<TrainScheduleReader.TrainSchedule> trainSchedules) {
        boolean foundMatchingTrains = false;

        for (TrainScheduleReader.TrainSchedule schedule : trainSchedules) {
            startStationIndex = -1;
            endStationIndex = -1;

            for (int i = 0; i < stationNames.size(); i++) {
                if (from.equals(stationNames.get(i))) {
                    startStationIndex = i;
                } else if (to.equals(stationNames.get(i))) {
                    endStationIndex = i;
                }
            }
            numberBetween = endStationIndex - startStationIndex;

            if (startStationIndex >= 0 && endStationIndex >= 0) {
                startTime = schedule.arrivalTimes.get(startStationIndex);
                endTime = schedule.arrivalTimes.get(endStationIndex);

                if (!startTime.equals("NA") && !endTime.equals("NA") && !startTime.equals("-") && !endTime.equals("-")) {
                    LocalTime startLocalTime = LocalTime.parse(startTime);
                    LocalTime endLocalTime = LocalTime.parse(endTime);
                    if (startLocalTime.isBefore(endLocalTime)) {
                        Duration duration = Duration.between(startLocalTime, endLocalTime);
                        long hours = duration.toHours();
                        long minutes = duration.toMinutesPart();

                        System.out.println("Train Model: " + schedule.trainModel);
                        System.out.println("Departure Time from " + from + ": " + startTime);
                        System.out.println("Arrival Time at " + to + ": " + endTime);
                        System.out.println("Duration: " + hours + " hours " + minutes + " minutes");
                        System.out.println("Total Seats:"+ 50);
                        System.out.println();
                        foundMatchingTrains = true;
                        RouteFound = foundMatchingTrains;
                        trainmodel.add(schedule.trainModel);
                        StartTime.add(startTime);
                    }
                }
            }
        }

        if (!foundMatchingTrains) {
            System.out.println("No trains found for the specified route.");
        }
    }
}

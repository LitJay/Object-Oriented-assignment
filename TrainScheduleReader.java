import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainScheduleReader {

    // Class representing train schedules
    public static class TrainSchedule {
        String trainModel;
        List<String> arrivalTimes;

        public TrainSchedule(String trainModel, List<String> arrivalTimes) {
            this.trainModel = trainModel;
            this.arrivalTimes = arrivalTimes;
        }
    }

  
    public static List<TrainSchedule> readTrainSchedules(String timetableFile) throws IOException {
        List<TrainSchedule> schedules = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(timetableFile))) {
            String line;
            StringBuilder currentTrainData = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (line.endsWith(".")) {
                        currentTrainData.append(line, 0, line.length() - 1);
                        String[] parts = currentTrainData.toString().split(",");
                        if (parts.length > 1) {
                            String trainModel = parts[0].trim();
                            List<String> arrivalTimes = new ArrayList<>();
                            for (int i = 1; i < parts.length; i++) {
                                String time = parts[i].trim();
                                arrivalTimes.add(time);
                            }
                            TrainSchedule schedule = new TrainSchedule(trainModel, arrivalTimes);
                            schedules.add(schedule);
                        }
                        currentTrainData = new StringBuilder();
                    } else {
                        currentTrainData.append(line).append(" ");
                    }
                }
            }
        }
        return schedules;
    }

    public static List<String> readStations(String filename) throws IOException {
        List<String> stations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] stationNames = line.split(",");
                for (String stationName : stationNames) {
                    stations.add(stationName.trim());
                }
            }
        }
        return stations;
    }
}

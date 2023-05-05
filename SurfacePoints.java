import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurfacePoints {

    public static void main(String[] args) throws IOException {
        String fileName = "output.td.txt"; // Replace with your input file path
        List<String[]> records = readDataFile(fileName);
        processCoordinates(records);
    }

    private static List<String[]> readDataFile(String fileName) throws IOException {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                records.add(line.split("\t"));
            }
        }
        return records;
    }

    private static void processCoordinates(List<String[]> records) {
        Map<String, List<double[]>> labelCoordinates = new HashMap<>();
        for (String[] record : records) {
            if (record.length < 9) { // Check if the record has enough elements
                System.err.println("Invalid record: " + Arrays.toString(record));
                continue;
            }
            double x = Double.parseDouble(record[1]);
            double y = Double.parseDouble(record[2]);
            double z = Double.parseDouble(record[3]);

            for (int i = 4; i < 9; i++) {
                String label = record[i];
                if (!label.equals("*")) {
                    labelCoordinates.putIfAbsent(label, new ArrayList<>());
                    labelCoordinates.get(label).add(new double[]{x, y, z});
                }
            }
        }

        for (Map.Entry<String, List<double[]>> entry : labelCoordinates.entrySet()) {
            String label = entry.getKey();
            List<double[]> coordinates = entry.getValue();
            double[] center = calculateCenter(coordinates);
            double[] outmost = findOutmostPoint(coordinates, center);
            System.out.println("Label: " + label);
            // System.out.println("Center: (" + center[0] + ", " + center[1] + ", " + center[2] + ")");
            System.out.println("Outmost: (" + outmost[0] + ", " + outmost[1] + ", " + outmost[2] + ")");
            System.out.println();
        }
    }

    private static double[] calculateCenter(List<double[]> coordinates) {
        double xSum = 0, ySum = 0, zSum = 0;
        int count = coordinates.size();
        for (double[] coordinate : coordinates) {
            xSum += coordinate[0];
            ySum += coordinate[1];
            zSum += coordinate[2];
        }
        return new double[]{xSum / count, ySum / count, zSum / count};
    }

    private static double[] findOutmostPoint(List<double[]> coordinates, double[] center) {
        double[] outmost = null;
        double maxDistance = Double.MIN_VALUE;
        for (double[] coordinate : coordinates) {
            double distance = Math.sqrt(Math.pow(center[0] - coordinate[0], 2) +
                                        Math.pow(center[1] - coordinate[1], 2) +
                                        Math.pow(center[2] - coordinate[2], 2));
            if (distance > maxDistance) {
                maxDistance = distance;
                outmost = coordinate;
            }
        }
        return outmost;
    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Coordinate {
    double x, y, z;

    Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

public class FindCenter {
    public static void main(String[] args) {
        String inputFileName = "output.td.txt";
        String outputFileName = "center_coordinates.txt";
        Map<String, ArrayList<Coordinate>>[] labelMaps = new HashMap[5];

        for (int i = 0; i < 5; i++) {
            labelMaps[i] = new HashMap<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            br.readLine(); // Skip the header line

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length < 9) { // Check if there are at least 9 elements
                    System.err.println("Skipping line with insufficient data: " + line);
                    continue;
                }

                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);

                for (int i = 4; i < 9; i++) {
                    String label = parts[i];

                    if (!label.equals("*")) {
                        ArrayList<Coordinate> coords = labelMaps[i - 4].get(label);

                        if (coords == null) {
                            coords = new ArrayList<>();
                            labelMaps[i - 4].put(label, coords);
                        }

                        coords.add(new Coordinate(x, y, z));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        int index = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            for (int i = 0; i < 5; i++) {
                // bw.write("Level " + (i + 1) + " center points:\n");

                for (Map.Entry<String, ArrayList<Coordinate>> entry : labelMaps[i].entrySet()) {
                    String label = entry.getKey();
                    ArrayList<Coordinate> coords = entry.getValue();

                    double xSum = 0, ySum = 0, zSum = 0;

                    for (Coordinate coord : coords) {
                        xSum += coord.x;
                        ySum += coord.y;
                        zSum += coord.z;
                    }

                    double centerX = xSum / coords.size();
                    double centerY = ySum / coords.size();
                    double centerZ = zSum / coords.size();
                    // label
                    bw.write(String.format("%s %.2f %.2f%n", label, centerX, centerY));
                    index++;
                }

                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

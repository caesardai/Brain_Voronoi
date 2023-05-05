import io
import numpy as np
import matplotlib.pyplot as plt
from scipy.spatial import Voronoi, voronoi_plot_2d

# Replace this with your actual .txt file path
file_content = '''
0 0.91 31.35 27.32
1 27.90 25.81 24.87
2 0.00 20.89 35.88

3 30.90 28.79 27.76
4 56.84 3.90 2.15
5 56.86 10.81 2.94
6 9.50 21.78 24.88
7 20.36 11.92 9.25

8 21.63 5.63 4.97
9 10.68 11.08 8.42
10 8.97 32.87 14.42
11 17.97 14.34 10.57
12 38.24 10.14 7.54
13 50.79 22.98 12.92
14 10.09 37.86 26.69
15 9.85 12.76 34.19
16 57.12 3.33 2.19
17 18.87 38.00 36.63
18 52.27 6.52 22.78
19 39.49 28.35 31.87
20 28.34 21.21 21.79
21 7.73 12.25 11.81
22 30.24 9.67 7.73

23 26.97 25.15 23.90
24 28.64 27.54 26.37
25 7.73 12.27 11.78

26 28.34 10.48 48.58
27 7.28 19.63 13.20
28 32.90 33.20 30.15
29 24.58 30.09 43.98
30 22.35 6.05 5.28
31 9.46 13.02 2.49
32 11.32 10.06 11.52
33 11.90 0.70 1.00
34 8.78 26.83 25.86
35 25.38 56.10 12.12
36 15.15 2.22 2.04
37 54.76 23.35 9.94
38 5.33 15.58 21.67
39 54.22 10.89 12.28
40 56.77 3.07 2.04
41 44.11 21.41 1.00
42 6.52 15.52 27.20
43 38.55 12.34 7.85
44 51.07 38.07 15.45
'''

# Read the file content into levels
levels = []
current_level = []
for line in io.StringIO(file_content):
    line = line.strip()
    if line:
        parts = line.split()
        name, x, y, z = int(parts[0]), float(parts[1]), float(parts[2]), float(parts[3])
        current_level.append((name, x, y, z))
    elif current_level:
        levels.append(current_level)
        current_level = []

if current_level:
    levels.append(current_level)

for i, level in enumerate(levels):
    if len(level) < 2:
        print(f"Skipping Level {i + 1} due to insufficient points")
        continue

    points = np.array([(x, y) for _, x, y, _ in level])
    vor = Voronoi(points)

    fig, ax = plt.subplots()
    voronoi_plot_2d(vor, ax=ax)

    for idx, (name, x, y, _) in enumerate(level):
        ax.annotate(str(name), (x, y), fontsize=8, ha='center', bbox=dict(facecolor='white', edgecolor='black', boxstyle='round,pad=0.1'))

    ax.set_title(f'Level {i + 1}')
    plt.show()

// import org.locationtech.jts.geom.*;
// import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;
// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.layout.Pane;
// import javafx.scene.shape.Line;
// import javafx.stage.Stage;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;

// public class LevelVoronoi extends Application {

//     private static final String FILE_PATH = "path/to/your/coordinates.txt";

//     public static void main(String[] args) {
//         launch(args);
//     }

//     @Override
//     public void start(Stage primaryStage) {
//         List<List<Coordinate>> levels = parseFile(FILE_PATH);
//         int levelIndex = 0; // Choose the level you want to visualize
//         List<Coordinate> coordinates = levels.get(levelIndex);

//         GeometryFactory geometryFactory = new GeometryFactory();
//         VoronoiDiagramBuilder voronoiBuilder = new VoronoiDiagramBuilder();
//         voronoiBuilder.setSites(coordinates);
//         Geometry voronoiDiagram = voronoiBuilder.getDiagram(geometryFactory);

//         Pane root = new Pane();
//         for (int i = 0; i < voronoiDiagram.getNumGeometries(); i++) {
//             Polygon polygon = (Polygon) voronoiDiagram.getGeometryN(i);
//             for (int j = 0; j < polygon.getNumInteriorRing(); j++) {
//                 LineString ring = polygon.getInteriorRingN(j);
//                 for (int k = 0; k < ring.getNumPoints() - 1; k++) {
//                     Line line = new Line(ring.getPointN(k).getX(), ring.getPointN(k).getY(), ring.getPointN(k + 1).getX(), ring.getPointN(k + 1).getY());
//                     root.getChildren().add(line);
//                 }
//             }
//         }

//         primaryStage.setScene(new Scene(root, 800, 600));
//         primaryStage.show();
//     }

//     private static List<List<Coordinate>> parseFile(String filePath) {
//         List<List<Coordinate>> levels = new ArrayList<>();
//         try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//             String line;
//             List<Coordinate> currentLevel = new ArrayList<>();
//             while ((line = reader.readLine()) != null) {
//                 if (line.isEmpty()) {
//                     levels.add(currentLevel);
//                     currentLevel = new ArrayList<>();
//                 } else {
//                     String[] parts = line.split(" ");
//                     double x = Double.parseDouble(parts[1]);
//                     double y = Double.parseDouble(parts[2]);
//                     currentLevel.add(new Coordinate(x, y));
//                 }
//             }
//             if (!currentLevel.isEmpty()) {
//                 levels.add(currentLevel);
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         return levels;
//     }
// }

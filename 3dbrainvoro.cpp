#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include "voro++"
using namespace voro;

// Set up constants for the container geometry
const double x_min=0,x_max=100;
const double y_min=0,y_max=100;
const double z_min=0,z_max=100;
const double cvol=(x_max-x_min)*(y_max-y_min)*(x_max-x_min);

// Set up the number of blocks that the container is divided into
const int n_x=100,n_y=100,n_z=100;

std::vector<std::array<double, 3>> readDataFile(const std::string& fileName) {
    std::vector<std::array<double, 3>> records;
    std::ifstream inputFile(fileName);

    if (inputFile.is_open()) {
        std::string line;
        while (std::getline(inputFile, line)) {
            std::istringstream lineStream(line);
            int index;
            double x, y, z;

            lineStream >> index >> x >> y >> z;
            records.push_back({x, y, z});
        }
        inputFile.close();
    } else {
        std::cerr << "Unable to open file: " << fileName << std::endl;
    }

    return records;
}

int main() {
        int i;
        double x,y,z;

        std::string fileName = "center_coordinates.txt"; // Replace with your input file path
        std::vector<std::array<double, 3>> particles = readDataFile(fileName);

        // Create a container with the geometry given above, and make it
        // non-periodic in each of the three coordinates. Allocate space for
        // eight particles within each computational block
        container con(x_min,x_max,y_min,y_max,z_min,z_max,n_x,n_y,n_z, false,false,false,8);

        for(i=0;i<particles.size();i++) {
            con.put(i, particles[i][0], particles[i][1], particles[i][2]);
        }

        printf("Container volume : %g\n"
            "Voronoi volume   : %g\n"
            "Difference       : %g\n",cvol,vvol,vvol-cvol);
 
        // Output the particle positions in gnuplot format
        con.draw_particles("random_points_p.gnu");

        // Output the Voronoi cells in gnuplot format
        con.draw_cells_gnuplot("random_points_v.gnu");
}

import java.io.File;  
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class getBrainCoordinates {
    
    public static void main (String[] args) {
        // File outfile = new File("input.txt");
        // ArrayList<String> arr = new ArrayList<String>();
        
        String arr;
        
        try {
            FileWriter outfile = new FileWriter("output.txt");
            // int index = 0;
            for (int i = 0; i <= 100; i++) {
                for (int j = 0; j <= 100; j++) {
                    for (int k = 0; k <= 100; k++) {
                        arr = "";
                        
                        String si = String.valueOf(i);
                        String sj = String.valueOf(j);
                        String sk = String.valueOf(k);
                        // arr += ("(");
                        arr += si;
                        arr += (" ");
                        arr += sj;
                        arr += (" ");
                        arr += sk;
                        arr += ("\n");
                        outfile.write(arr);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}


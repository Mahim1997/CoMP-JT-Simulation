
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader {

    //Read from CSV file
    public String fileNameToRead = GraphPlotter.FILE_NAME;

    public Results readThingsFromFile() {
        BufferedReader csvReader = null;
        try {
            Results rs = new Results();
            //Replace file name from csv to png
            rs.fileNameToSaveInPng = (fileNameToRead.replace("csv", "png"));
            String row = "";
            csvReader = new BufferedReader(new FileReader(fileNameToRead));
            
            //Start at Title [so idx = -1]
            int idx = -1;
            
            
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                
                //Initial checking
                if(idx != -1){
//                    System.out.println("ENETRING... printing data[0] = " + data[0]);
                    rs.hour[idx] = Double.parseDouble(data[0]);
                    rs.chi[idx] = Double.parseDouble(data[1]);
                    rs.average_throughput[idx] = Double.parseDouble(data[2]);
                    rs.average_power_consumption[idx] = Double.parseDouble(data[3]);
                    rs.fairness_index[idx] = Double.parseDouble(data[4]);
                }
                idx++;
            }
            csvReader.close();
            return rs;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                csvReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader {

    //Read from CSV file [Conventional Method only]
    //For Conventional Task
    public String fileNameToRead = GraphPlotter.FILE_NAME;

    //For Task 1 of paper'.
    public Result_T_UE_vs_Chi read_UI_vs_Chi_once(String fileName) {
        Result_T_UE_vs_Chi rs = new Result_T_UE_vs_Chi(fileName, 0);

        BufferedReader csvReader = null;
        String row = null;
        try {
            csvReader = new BufferedReader(new FileReader(fileName));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
//                System.out.println("data[0] = " + data[0] + " , data[1] = " + data[1]);
                try {
                    double chi, thpt_avg;
                    chi = Double.parseDouble(data[0]);
                    thpt_avg = Double.parseDouble(data[1]);
                    rs.chi_list.add(chi * 100);
                    rs.avg_UE_throughput_list.add((thpt_avg));
                } catch (NumberFormatException e2) {
                    //Do nothing ... just continue
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

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
                if (idx != -1) {
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

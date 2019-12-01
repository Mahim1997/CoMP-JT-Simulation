package graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Things_One_JT {
    public String legendName;
    public int jt_value;
    public String fileNameToRead;

    public List<Double> distance_list;
    public List<Double> chi_list;
    public List< List<Double>> T_avg_per_chi_list; //Avg Throughput per chi.

    public Things_One_JT() {
        this.distance_list = new ArrayList<>();
        this.T_avg_per_chi_list = new ArrayList<>();
        this.chi_list = Arrays.asList(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0);
        for (int i = 0; i < this.chi_list.size(); i++) {
            this.T_avg_per_chi_list.add(new ArrayList<>());
        }
    }

    public void readFromFileAndStoreInfo() {
        BufferedReader csvReader;
        String row;
        try {
            csvReader = new BufferedReader(new FileReader(this.fileNameToRead));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                try {
                    this.distance_list.add(Double.parseDouble(data[0])); //Distance column
                    for (int col_itr = 1; col_itr < data.length; col_itr++) { //Columns other than distance
                        this.T_avg_per_chi_list.get(col_itr - 1).add(Double.parseDouble(data[col_itr]));
                    }
                } catch (NumberFormatException e) {
                }
            }
        } catch (IOException e) {
        }
    }

    public void printThings() {
        System.out.println("JT = " + this.jt_value + " , fileName = " + this.fileNameToRead);
        for (int row_itr = 0; row_itr < this.distance_list.size(); row_itr++) {
            double distance = this.distance_list.get(row_itr);
            System.out.print("Distance = " + distance + " : ");
            for (int chi_itr = 0; chi_itr < this.T_avg_per_chi_list.size(); chi_itr++) {
                double T_avg_per_chi = this.T_avg_per_chi_list.get(chi_itr).get(row_itr);
                System.out.print(T_avg_per_chi + ", ");
            }
            System.out.println("");
        }
    }
}

package graphs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    private static double round_value(double d, int NUM_SIG_FIG) {

        BigDecimal bd = new BigDecimal(d);
        bd = bd.round(new MathContext(NUM_SIG_FIG));
        double rounded = bd.doubleValue();
        return rounded;
    }

    private static double round_value(double d) {

        BigDecimal bd = new BigDecimal(d);
        bd = bd.round(new MathContext(3));
        double rounded = bd.doubleValue();
        return rounded;
    }

    private static String get3SigFigStr(String s) {
        String[] arr = s.split("=");
        if (arr.length == 1) {
            return s;
        }
        String num_str = arr[1].trim();
        try {
            double d = Double.parseDouble(num_str);
            double d_3SF = round_value(d, 3);
            String to_ret = arr[0] + "= " + String.valueOf(d_3SF);
            return to_ret;
        } catch (Exception e) {

        }
        return null;
    }

    public static List< List<Double>> read_data(String fileName, int num) {
        List< List<Double>> list_of_columns = new ArrayList<>();
        for (int i = 0; i < num; i++) { //INITIALIZE [Little In-efficient]
            list_of_columns.add(new ArrayList<>());
        }
        BufferedReader csvReader = null;
        String row = null;
        int row_num = 0;
        try {
            csvReader = new BufferedReader(new FileReader(fileName));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                try {
//                    double distance = Double.parseDouble(data[0]);
                    for (int i = 0; i < data.length; i++) {
                        list_of_columns.get(i).add((Double.parseDouble(data[i])));
                    }
                } catch (NumberFormatException e) {
                }
            }
        } catch (IOException e) {
        }
        return list_of_columns;
    }

    public static String[] read_headings(String fileName) {
        BufferedReader csvReader = null;
        String row = null;
        int row_num = 0;
        try {
            csvReader = new BufferedReader(new FileReader(fileName));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                for (int i = 0; i < data.length; i++) {
                    data[i] = get3SigFigStr(data[i]);
                }
                return data;
            }
        } catch (IOException e) {
        }
        return null;
    }
    //Read from CSV file [Conventional Method only]
    //For Conventional Task
    public String fileNameToRead = GraphPlotter.FILE_NAME;

    //For Task 1 of paper'.
    public Result_T_UE_vs_Chi read_UE_vs_Chi_once(String fileName) {
        Result_T_UE_vs_Chi rs = new Result_T_UE_vs_Chi(fileName, 0);

        BufferedReader csvReader = null;
        String row = null;
        try {
            csvReader = new BufferedReader(new FileReader(fileName));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
//                System.out.println("data[0] = " + data[0] + " , data[1] = " + data[1]);
                try {
                    double chi, avg_throughput, spectral_efficiency, cell_edge_thpt, fairness_idx, discrimination_idx,
                            entropy, proportion_UE_dropped_avg, 
                            proportion_UE_active, effective_chi_meanBSs, effective_chi_prop_active_UE, Tavg_active_UE;
                    chi = Double.parseDouble(data[0]);
                    avg_throughput = Double.parseDouble(data[1]);
                    spectral_efficiency = Double.parseDouble(data[2]);
                    fairness_idx = Double.parseDouble(data[3]);
                    cell_edge_thpt = Double.parseDouble(data[4]);
                    discrimination_idx = Double.parseDouble(data[5]);
                    entropy = Double.parseDouble(data[6]);
                    proportion_UE_dropped_avg = Double.parseDouble(data[7]);
                    proportion_UE_active = Double.parseDouble(data[8]);
                    effective_chi_meanBSs = Double.parseDouble(data[9]);
                    effective_chi_prop_active_UE = Double.parseDouble(data[10]);
                    Tavg_active_UE = Double.parseDouble(data[11]);
                    if (avg_throughput > GraphPlotter.THRESHOLD_FOR_NOT_TAKING) {
                        //Only take for chi > THRESHLOD [here, 0.02]
                        rs.chi_list.add(chi * 100); //PERCENTAGE
                        rs.avg_UE_throughput_list.add((avg_throughput));
                        rs.spectral_efficiency_list.add(spectral_efficiency);
                        rs.cell_edge_throughput_list.add(cell_edge_thpt);
                        rs.fairness_index_jain_list.add(fairness_idx);
                        rs.discrimination_index_list.add(discrimination_idx);
                        rs.entropy_list.add(entropy);
                        rs.proportion_UE_dropped_list.add(proportion_UE_dropped_avg * 100); //PERCENTAGE
                        rs.proportion_UE_active_list.add(proportion_UE_active * 100);
                        rs.effective_chi_meanBSs_list.add(effective_chi_meanBSs * 100);
                        rs.effective_chi_propActiveUEs_list.add(effective_chi_prop_active_UE * 100);
                        rs.avg_ACTIVE_UE_throughput_list.add(Tavg_active_UE);
                    }
                } catch (NumberFormatException e2) {
                    //Do nothing ... just continue
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    //For CONVENTIONAL [NOT AVG METRICS]
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

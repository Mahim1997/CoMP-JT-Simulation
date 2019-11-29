package util_and_calculators;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import sim_objects.BaseStation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sim_results.SimResult_Avg_T_vs_dist_per_chi;
import simulation_params.SimulationParameters;

public class Helper {

    public static void printListDouble(List<Double> list) {
        for (double x : list) {
            System.out.print(x + ", ");
        }
        System.out.println("");
    }

    public static void printBaseStationLocations(List<BaseStation> baseStations) {
        //Printing locations ... correct locations
        for (BaseStation bs : baseStations) {
            System.out.println(bs.toString());
        }
    }

    public static String getArrayDelim_EnterNewLineBoolean(double[] arr, String delim, boolean enter_new_line) {
        String s = "";

        for (int i = 0; i < arr.length; i++) {
            s += (String.valueOf(arr[i]));
            s += delim;
        }
        if (enter_new_line) {
            s += "\n";
        }
        return s;
    }

    public static double convertToWatts_From_dBm(double power_in_dBm) {
        double p = (power_in_dBm * 0.1) - 3;
        return Math.pow(10, p);
        //return (Math.pow(10, power_in_dBm * 0.1) * Math.pow(10, -3));
    }

    public static double AVERAGE_ARRAY(double[] arr) {
        double sum_arr = SUM_OF_ARRAY(arr);
        return (sum_arr / (double) (arr.length));
    }

    public static double SUM_OF_ARRAY(double[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }

    public static void printMap(Map<Double, BaseStation> map) {
        Set set = map.entrySet();

        // Using iterator in SortedMap 
        Iterator iterator = set.iterator();

        // Traversing map. Note that the traversal 
        // produced sorted (by keys) output . 
        while (iterator.hasNext()) {
            Map.Entry m = (Map.Entry) iterator.next();

            double key = (Double) m.getKey();
            BaseStation value = (BaseStation) m.getValue();

            System.out.println("ReceivedPower_mW : " + key + ", BS : " + value);
        }
    }

    public static double convert_To_mW_From_dBM(double power) {
        return (Math.pow(10, (double) (power * 0.1))); //1mW = 10 ^ (dB/10)
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static double DEGREE_TO_RADIAN(double degree) {
        return (degree * (Math.PI / 180.0));
    }

    public static double log2(double d) {
        return (Math.log(d) / Math.log(2.0));
    }

    public static int how_many_chi_data_points(SimulationParameters simParams) {
        return (int) ((simParams.chi_final - simParams.chi_initial) / simParams.chi_step_size);

    }

    public static void erase_csv_file(String fileName) {
        try (FileWriter fw = new FileWriter(fileName);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void write_user_throughput_and_distance(String fileName, String THROUGHPUT_user_one_BS_KBps, String distanceFromBS) {
        try (FileWriter fw = new FileWriter(fileName, true); //append
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.print(String.valueOf(THROUGHPUT_user_one_BS_KBps) + "," + String.valueOf(distanceFromBS));
            out.println(); //print line.
        } catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }

    }

    public static void erase_CSV_file(String fileName, String row1, String row2) {
        try (FileWriter fw = new FileWriter(fileName); //append
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
//            out.print(String.valueOf("Chi") + "," + String.valueOf("T_avg (kBps)"));
            out.print(row1 + "," + row2);
            out.println(); //print line.
        } catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }
    }

    public static void writeCSV_row1_row2(String fileName, double row1, double row2) {
        writeCSV_row1_row2(fileName, String.valueOf(row1), String.valueOf(row2));
    }

    public static void writeCSV_row1_row2(String fileName, String row1, String row2) {
        try (FileWriter fw = new FileWriter(fileName, true); //append [TRUE]
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.print(String.valueOf(row1) + "," + String.valueOf(row2));
            out.println(); //print line.
        } catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }
    }

    public static void write_to_csv_UE_T_avg_vs_dist_for_diff_chis(List<SimResult_Avg_T_vs_dist_per_chi> list_results,
            String fileName) {
        try (FileWriter fw = new FileWriter(fileName, false); //append [TRUE]
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            //HEADINGS
            out.print("Distance/m" + ",");
            for (int i = 0; i < list_results.size(); i++) {
                out.print("Chi = " + list_results.get(i).chi_value);
                if (i != (list_results.size() - 1)) {
                    out.print(",");
                }
            }
            out.println(); //print line.
            //NOW DATAs
            double[][] dataMatrix = getData_dist_UE_avg_T_different_chi(list_results);
            for (double[] row_data : dataMatrix) {
                for (int j = 0; j < row_data.length; j++) {
                    //PRINT EACH ROW
                    out.print(row_data[j]);
                    out.print(",");
                }
                out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }
    }

    public static double[][] getData_dist_UE_avg_T_different_chi(List<SimResult_Avg_T_vs_dist_per_chi> list_results) {
        int num_rows = list_results.get(0).avg_throughput_UE_list.size();
        int num_columns = list_results.size() + 1; // EXTRA 1 for the distance column [1st col, col idx = 0]

        double[][] arr_data = new double[num_rows][];
        for (int i = 0; i < num_rows; i++) {
            arr_data[i] = new double[num_columns];
        }

        //First column ONLY distance
        for (int i = 0; i < num_rows; i++) {
            arr_data[i][0] = list_results.get(0).distance_list.get(i); //DISTANCE LIST is same for all.
        }
        //Now fill up other values.
        for (int row_iter = 0; row_iter < num_rows; row_iter++) {
            for (int col_iter = 0; col_iter < list_results.size(); col_iter++) {
                arr_data[row_iter][col_iter + 1] = list_results.get(col_iter).avg_throughput_UE_list.get(row_iter);
            }
        }

        return arr_data;
    }

    public static void printBaseStations(List<BaseStation> baseStations) {
        System.out.println("[Helper] >> Printing list of base stations.");
        for (BaseStation bs : baseStations) {
            System.out.print(bs.base_station_id + ", ");
        }
        System.out.println("");
    }

}

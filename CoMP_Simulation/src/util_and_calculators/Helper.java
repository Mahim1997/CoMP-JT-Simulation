package util_and_calculators;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import objects.BaseStation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import simulation_params.SimulationParameters;

public class Helper {

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

}

package comp_simulation;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import objects.BaseStation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import simulation_methods.SimulationResults_HourlyData;
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

    public static void write_to_file(String fileName, SimulationResults_HourlyData finalResult) {

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
//            writer.write("something");

            for (int i = 0; i < SimulationResults_HourlyData.things_to_save.length; i++) {
                writer.write(SimulationResults_HourlyData.things_to_save[i]);
                if (i != (SimulationResults_HourlyData.things_to_save.length - 1)) {
                    writer.write(",");
                }
            }
            //{"Hour", "Chi", "Average Throughput(kBps)", "Average Power Consumed (W)"}
            writer.write("\n");
            int num_hours = 24;
            for (int i = 0; i < num_hours; i++) {

                //{
                //Write all as columns of CSV file
                writer.write(String.valueOf(finalResult.hour_arr[i]) + ",");
                writer.write(String.valueOf(finalResult.chi[i]) + ",");
                writer.write(String.valueOf(finalResult.average_throughput_arr[i]) + ",");
                writer.write(String.valueOf(finalResult.average_power_consumption_arr[i]) + ",");
                writer.write(String.valueOf(finalResult.fairness_index_arr[i]) + ",");
                writer.write(String.valueOf(finalResult.spectral_efficiency_arr[i]) + ",");
                writer.write(String.valueOf(finalResult.cell_edge_throughput_arr[i]));
                //}
                //New line
                writer.write("\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static double log2(double d) {
        return (Math.log(d) / Math.log(2.0));
    }

    public static int how_many_chi_data_points(SimulationParameters simParams) {
        return (int) ((simParams.chi_final - simParams.chi_initial) / simParams.chi_step_size);

    }

}

package comp_simulation;

import objects.BaseStation;
import java.util.List;

public class Helper {

    public static void printBaseStationLocations(List<BaseStation> baseStations) {
        //Printing locations ... correct locations
        for (BaseStation bs : baseStations) {
            System.out.println(bs.toString());
        }
    }

    public static double convertToWatts_From_dBm(double power_in_dBm) {
        double p = (power_in_dBm * 0.1) - 3;
        return Math.pow(10, p);
        //return (Math.pow(10, power_in_dBm * 0.1) * Math.pow(10, -3));
    }

    public static double SUM_OF_ARRAY(double[] arr) {
        double sum = 0;
        for (double x : arr) {
            sum += x;
        }
        return sum;
    }

    public static double convert_To_mW_From_dBM(double power) {
        return (Math.pow(10, (double) (power * 0.1))); //1mW = 10 ^ (dB/10)
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static double DEGREE_TO_RADIAN(double rad) {
        return (rad * (Math.PI / 180.0));
    }

}

package util_and_calculators;

import java.util.Arrays;

public class CalculatorECDF {

    public static void ECDF(double[] arr, double[] x_axis_data, double[] y_axis_data) {
//        x_axis_data = new double[arr.length];
//        y_axis_data = new double[arr.length];
        int num_points = arr.length;
        System.arraycopy(arr, 0, x_axis_data, 0, x_axis_data.length);
        Arrays.sort(x_axis_data);
        //y-axis is [1 .. len(x)] each divided by length[to keep as proportion] { * 100 for percentage }
        for (int i = 0; i < y_axis_data.length; i++) {
            y_axis_data[i] = (i + 1) / (double) num_points;
        }
    }

    public static double get5thPercentile(double[] x_axis_data, double[] y_axis_data) {
        double x_value_5th_percentile = 0;
        //Linear ??

        double _5th_percentile = 0.05; //5/100
        //find the closest point to this
        double closest_point = x_axis_data[0];
        double difference_so_far = Double.MAX_VALUE;

        //Check if any points are below the 5th percentile.
        double x1 = 0, x2 = 0, y1 = 0, y2 = 0; //(x1, y1) are below points of _5th_percentile and (x2,y2) are above
        if (y_axis_data[0] > _5th_percentile) {
            //NONE are below the 5th percentile
            x1 = 0;
            y1 = 0;
            for (int i = 0; i < y_axis_data.length; i++) {
                if (y_axis_data[i] > _5th_percentile) {
                    x2 = x_axis_data[i];
                    y2 = y_axis_data[i];
                    break;
                }
            }

        } else {
            //SOME EXIST...
            for (int i = 0; i < y_axis_data.length; i++) {
                if (y_axis_data[i] > _5th_percentile) {
                    x1 = x_axis_data[i - 1];
                    y1 = y_axis_data[i - 1];
                    x2 = x_axis_data[i];
                    y2 = y_axis_data[i];
                    break;
                }
            }
            //SOME ABOVE 5th percentile ALWAYS
        }
        x_value_5th_percentile = getLinearInterpolation(x1, x2, y1, y2, _5th_percentile);
        return x_value_5th_percentile;
    }

    private static double getLinearInterpolation(double x1, double x2, double y1, double y2, double yL) {
        //Assume straight line.
        /*
        (y2 - y1)/(x2 - x1) = (y2 - yL) / (x2 - xL)
         */
        double temp = ((x2 - x1) / (y2 - y1));
        temp *= (y2 - yL);
        double xL = x2 - temp;
        return xL;
    }

}

package simulation_methods;

import comp_simulation.Helper;
import java.util.ArrayList;
import java.util.List;
import objects.BaseStation;

public class SimulationResults_HourlyData {
    public static String[] things_to_save = {"Hour", "Chi", "Average Throughput(kBps)", "Average Power Consumed (W)"};
    public List<BaseStation> list_of_base_stations = new ArrayList<>();

    public double[] chi;
    public double[] hour_arr;
//    public double []energy_efficiency_arr;
//    public double []sinr_arr;

    //Metrics.
    public double[] cumulative_throughput_arr;
    public double[][] power_consumption_arr; //Number of BaseStations * 24

    //Average Metrics
    public double[] average_power_consumption_arr; //Avg. Power Consumption
    public double[] average_throughput_arr; //Avg Throughput
    public double[] fairness_index_hourly; //Fairness Index (HOURLY)

    public SimulationResults_HourlyData(int numBaseStations) {
        this.hour_arr = new double[24];
        for (int i = 0; i < 24; i++) {
            this.hour_arr[i] = (double) (i + 0.5);
        }

        //Metrics [INITIALIZATION of array]
        this.cumulative_throughput_arr = new double[24];
        this.power_consumption_arr = new double[24][];
        for (int i = 0; i < this.power_consumption_arr.length; i++) {
            this.power_consumption_arr[i] = new double[numBaseStations];
        }
        this.average_throughput_arr = new double[24];
        this.average_power_consumption_arr = new double[24];
        this.chi = new double[24];

    }

    public void copy_chi(double []chi_copy){
        System.arraycopy(chi_copy, 0, chi, 0, chi.length);
    }
    
    public void printAllData() {
//        System.out.print("Cumulative Throughput: ");
//        System.out.println(Helper.getArrayDelim_EnterNewLineBoolean(cumulative_throughput_arr, " ", false));

        System.out.print("Average Throughput: ");
        System.out.println(Helper.getArrayDelim_EnterNewLineBoolean(average_throughput_arr, " ", false));

        System.out.print("Average Power Consumption: ");
        System.out.println(Helper.getArrayDelim_EnterNewLineBoolean(average_power_consumption_arr, " ", false));

    }

    public void formAverage(SimulationResults_HourlyData[] results) {
        for (int i = 0; i < 24; i++) { //24 hours
            this.average_throughput_arr[i] = 0;
            this.average_power_consumption_arr[i] = 0;
        }

        for (int i = 0; i < results.length; i++) {

            //24 hours
            for (int k = 0; k < 24; k++) {
                //1. Avg Throughput
                this.average_throughput_arr[k] += results[i].average_throughput_arr[k];
                //2. Avg Power Consumption
                this.average_power_consumption_arr[k] += results[i].average_power_consumption_arr[k];
            }

        }
        //for 24 hours
        double monte_carlo = (double)(results.length);
        for (int k = 0; k < 24; k++) {
            //Average throughput
            this.average_throughput_arr[k] /= monte_carlo;
            //Average Power Consumption
            this.average_power_consumption_arr[k] /= monte_carlo;
        }

    }

}

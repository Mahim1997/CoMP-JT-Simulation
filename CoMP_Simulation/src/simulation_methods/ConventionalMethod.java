package simulation_methods;

import objects.BaseStation;
import comp_simulation.Helper;
import comp_simulation.ResourceBlockCalculator;
import java.util.ArrayList;
import java.util.List;
import objects.User;
import simulation_params.SimulationParameters;

public class ConventionalMethod {

    public static void runConventionalSimulation(SimulationParameters simParams) {
        //1. Place the Base Stations.
        List<BaseStation> baseStations = new ArrayList<>();

        BaseStation.placeBaseStations(baseStations, simParams.cell_radius, simParams.tier);

        //Printing them
//        Helper.printBaseStationLocations(baseStations);
        double[] hour_arr = {0.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5,
            10.5, 11.5, 12.5, 13.5, 14.5, 15.5, 16.5, 17.5, 18.5, 19.5, 20.5, 21.5, 22.5, 23.5};
        double[] throughput_arr = new double[24];
        double[] energy_efficiency_arr = new double[24];

        runSimulation_Conventional_Generate_Data(simParams, baseStations, hour_arr, throughput_arr, energy_efficiency_arr);
    }

    private static void runSimulation_Conventional_Generate_Data(SimulationParameters simParams,
            List<BaseStation> baseStations, double[] hour_arr, double[] throughput_arr, double[] energy_efficiency_arr) {
        //Data will be stored in those arrays ... 24 hr data.

        SimulationResults_HourlyData[] results = new SimulationResults_HourlyData[simParams.monte_carlo];
        double inter_bs_distance = Math.pow(3, 0.5) * simParams.cell_radius;
        for (int i = 0; i < 1 /*simParams.monte_carlo*/; i++) {

            results[i] = runSimulation_Once(inter_bs_distance, i, simParams, baseStations);

        }

        //Now take the average...
    }

    private static SimulationResults_HourlyData runSimulation_Once(double inter_bs_distance, int mc,
            SimulationParameters simParams, List<BaseStation> baseStations) {
        SimulationResults_HourlyData simResults = new SimulationResults_HourlyData();

        //With respect to distance will be taken.
        System.out.println("Inside runSimulation_Once, mc = " + mc + " , BW = " + simParams.bandwidth);

        //1. Calculate no. of resource blocks.
        double bw_MHz = simParams.bandwidth / Math.pow(10, 6);
        int no_resource_blocks = ResourceBlockCalculator.numberOfResourceBlocks(bw_MHz);
        System.out.println("Num. resource blocks = " + no_resource_blocks);

        for (int hour = 0; hour < 24; hour++) {
            //DO CALCULATION FOR THIS HOUR
            for (int baseStation_iter = 0; baseStation_iter < baseStations.size(); baseStation_iter++) {
                BaseStation bs = baseStations.get(baseStation_iter);

                //Do calculation wrt this BASE STATION
                //2. Take random traffic for each Base Station Using CHI //FLOOR(chi * num_resource_blocks * rand)
                int num_users_this_bs = (int) (Math.random() * simParams.chi[hour] * no_resource_blocks);
//                System.out.println("Hour = " + hour + " , Base station bs = " + bs + ", no_users = " + num_users_this_bs);
                //Use array of users.

                User[] users_arr = new User[num_users_this_bs];

                for (int user_no = 0; user_no < num_users_this_bs; user_no++) {
                    
                    //Assume this user is connected to my Base station
                    
                    double theta = Math.random() * Math.PI; //an angle randomly taken from 0 to π
                    double x_user = inter_bs_distance * Math.cos(Helper.DEGREE_TO_RADIAN(theta));
                    double y_user = inter_bs_distance * Math.sin(Helper.DEGREE_TO_RADIAN(theta));
                    users_arr[user_no] = new User(x_user, y_user);
                    
                }

            }
        }

        return simResults;
    }

}

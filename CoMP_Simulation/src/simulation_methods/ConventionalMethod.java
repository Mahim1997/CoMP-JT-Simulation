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

            SimulationResults_HourlyData runSimulation_Once = runSimulation_Once(inter_bs_distance, i, simParams, baseStations);
            for (int k = 0; k < runSimulation_Once.average_throughput_arr.length; k++) {
                System.out.println(runSimulation_Once.cumulative_throughput_arr[k]);
            }
            results[i] = runSimulation_Once;
        }

        //Now take the average...
    }

    private static SimulationResults_HourlyData runSimulation_Once(double inter_bs_distance, int mc,
            SimulationParameters simParams, List<BaseStation> baseStations) {

        SimulationResults_HourlyData simResults = new SimulationResults_HourlyData(baseStations.size());

        //With respect to distance will be taken.
        System.out.println("Inside runSimulation_Once, mc = " + mc + " , BW = " + simParams.bandwidth);

        //1. Calculate no. of resource blocks.
        double bw_MHz = simParams.bandwidth / Math.pow(10, 6);
        int no_resource_blocks = ResourceBlockCalculator.numberOfResourceBlocks(bw_MHz);
        System.out.println("Num. resource blocks = " + no_resource_blocks);

        //Calculate FSPL_dB upfront [Fixed S Path Loss in dB]
        double FSPL_dB = 20 * Math.log10(simParams.path_loss_reference_distance)
                + 20 * Math.log10(simParams.frequency_carrier)
                + 92.45;

        //Calculate Power NOUSE up front
        double Pn_mW = Helper.convert_To_mW_From_dBM(-174 + 10 * Math.log10(simParams.bandwidth));

        //FOR EACH HOUR
        for (int hour = 0; hour < 24; hour++) {
            //FOR EACH B.S.
            double cumulative_throughput_this_hour = 0;

            for (int baseStation_iter = 0; baseStation_iter < baseStations.size(); baseStation_iter++) {
                BaseStation bs = baseStations.get(baseStation_iter);
                //2. Take random traffic for each Base Station Using CHI //FLOOR(chi * num_resource_blocks * rand)
                int num_users_this_bs = (int) (Math.random() * simParams.chi[hour] * no_resource_blocks);
                //FOR EACH U.E.
                for (int user_no = 0; user_no < num_users_this_bs; user_no++) {

                    //Assume this user is connected to my Base station [THIS BASE STATION i.e. bs]                    
                    double theta = Math.random() * 2 * Math.PI; //an angle randomly taken from 0 to π
                    double x_user = inter_bs_distance * Math.cos(Helper.DEGREE_TO_RADIAN(theta));
                    double y_user = inter_bs_distance * Math.sin(Helper.DEGREE_TO_RADIAN(theta));
                    User user = new User(x_user, y_user);
                    //Perform user-wise computations.

                    //Pass the list of all base stations to THIS user and get distances to all BS.
//                    distances_to_all_BS = user.get_distances_of_all_baseStations(baseStations);
                    double distance_to_this_BS_of_this_user = user.getDistanceFromBS(bs);

                    //Array of received powers of all base stations for THIS USER.
                    double[] power_received_from_all_BS_of_this_user
                            = user.get_RECEIVED_POWER_of_all_BS(FSPL_dB, baseStations, simParams);

                    //JUST THIS BASE STATION'S RECEIVED POWER.
                    double power_received_from_THIS_BS = user.get_RECEIVED_POWER_mW_for_one_BS(FSPL_dB, bs, simParams);

                    double noise = Pn_mW;
                    double total_recv_power_of_just_other_BS
                            = Helper.SUM_OF_ARRAY(power_received_from_all_BS_of_this_user) - power_received_from_THIS_BS;

                    user.SINR_user_one_BS = power_received_from_THIS_BS / (noise + total_recv_power_of_just_other_BS);

                    //Metric 1. Cumulative Throughput of this hour [ThCon]
                    double throughput_of_user_for_BS_this_hour = 180 * (Math.log(1 + user.SINR_user_one_BS) / Math.log(2));  //per (User,BS,Hour)
                    cumulative_throughput_this_hour += throughput_of_user_for_BS_this_hour; //Cumulative Throughput of this (Hour)

                }
                //Metric 2. Cumulative Power Consumption. [PcCon] PCcon(BS,hr) = NTRX * (P_0 + chi[BS,hr]*P_max*del_p)
                double power_consumed_each_BS_each_hr = simParams.power_numberOfTransceivers
                        * (simParams.power_zeroOutput + simParams.chi[hour] * simParams.power_max * simParams.power_delP);
                simResults.power_consumption_arr[hour][baseStation_iter] = power_consumed_each_BS_each_hr; //hour,BS

            }
            simResults.cumulative_throughput_arr[hour] = cumulative_throughput_this_hour;
        }

        //Measure Average METRICS..
        double num_bs_double = (double) (baseStations.size());
        for (int hr = 0; hr < 24; hr++) {
            simResults.average_throughput_arr[hr] = simResults.cumulative_throughput_arr[hr] / num_bs_double;
            double hourly_power_consumption = 0;
            for (int bs = 0; bs < baseStations.size(); bs++) {
                hourly_power_consumption += simResults.power_consumption_arr[hr][bs];
            }
            simResults.average_power_consumption_arr[hr] = hourly_power_consumption;
        }

        return simResults;
    }

}

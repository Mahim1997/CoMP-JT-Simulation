package simulation_for_paper_distance_based;

import comp_simulation.Main;
import util_and_calculators.Helper;
import util_and_calculators.ResourceBlockCalculator;
import java.util.ArrayList;
import java.util.List;
import sim_objects.BaseStation;
import sim_objects.User;
import sim_results.SimResult_Avg_T_vs_dist_per_chi;

import simulation_params.SimulationParameters;
import util_and_calculators.MetricCalculatorAfter;

public class Sim_UE_avg_Throughput_vs_distance {

    private SimulationParameters simParams;
    private boolean IS_CONVENTIONAL_TAKEN = false;

    public Sim_UE_avg_Throughput_vs_distance(SimulationParameters simParams) {
        this.simParams = simParams;
    }

    //BELOW for Task 2 ... varying distance and calculating avg UE throughput
    public void runSimulationForSecondTask_NEW() {

        simParams.distance_initial = 0.1;
        simParams.distance_final = simParams.cell_radius;
//        simParams.distance_final = 2; //DEBUG DISTANCE
        simParams.distance_increment = 10; //All in m

        String folderName = "UE_T_avg_vs_distance_BS";
        Main.PREV_MODE_JT = Main.JT_MODE;
        for (int JT = simParams.JT_INITIAL; JT <= simParams.JT_FINAL; JT++) {
            simParams.JT_VALUE = JT;
            if (JT == 0) {
                this.IS_CONVENTIONAL_TAKEN = true;
                Main.JT_MODE = Main.JT_DISTANCE;
                simParams.JT_VALUE = 1;
            } else {
                this.IS_CONVENTIONAL_TAKEN = false;
                Main.JT_MODE = Main.PREV_MODE_JT;
            }
            String fileName = folderName + "/UE_T_avg_vs_distance_BS_MC_" + String.valueOf(simParams.monte_carlo)
                    + "_JT_" + String.valueOf(simParams.JT_VALUE) + ".csv";
            if (Main.TAKE_AFTER_CALCULATION) {
                fileName = folderName + "/UE_T_avg_vs_distance_BS_MC_" + String.valueOf(simParams.monte_carlo)
                        + "_JT_" + String.valueOf(simParams.JT_VALUE) + "_Take_after_calcs.csv";
            }

            if (this.IS_CONVENTIONAL_TAKEN) {
                fileName = folderName + "/UE_T_avg_vs_distance_BS_MC_" + String.valueOf(simParams.monte_carlo)
                        + "_JT_0.csv";
                if (Main.TAKE_AFTER_CALCULATION) {
                    fileName = folderName + "/UE_T_avg_vs_distance_BS_MC_" + String.valueOf(simParams.monte_carlo)
                            + "_JT_0_Take_after_calcs.csv";
                }
            }
            System.out.println("->>File name is " + fileName);

            List<SimResult_Avg_T_vs_dist_per_chi> list_results = new ArrayList<>();
            for (double chi = simParams.chi_initial; chi < simParams.chi_final; chi += simParams.chi_step_size_task_2) {
                simParams.chi_for_position = chi;
                SimResult_Avg_T_vs_dist_per_chi result_one_sim = run_T_avg_vs_Distance();
                list_results.add(result_one_sim);
            }
//Write in same CSV file for SAME JT value .... different chi values ....
            Helper.write_to_csv_UE_T_avg_vs_dist_for_diff_chis(list_results, fileName);
        }
    }

    public SimResult_Avg_T_vs_dist_per_chi run_T_avg_vs_Distance() {
        //Place Base Stations [Fixed Positions throughout all the simulations]
        SimResult_Avg_T_vs_dist_per_chi simResult = new SimResult_Avg_T_vs_dist_per_chi();
        List<BaseStation> baseStations = new ArrayList<>();
        BaseStation.placeBaseStations(baseStations, simParams.cell_radius, simParams.tier);
        //Always fixed parameters for all chi.
        double inter_bs_distance = Math.pow(3, 0.5) * simParams.cell_radius; // root(3) * cell_radius = IBS
        double FSPL_dB = (20 * Math.log10(simParams.path_loss_reference_distance))
                + (20 * Math.log10(simParams.frequency_carrier)) + 92.45; //FSPL_dB = 20*log_10(d_0) + 20*log_10(fc) + 92.45

        //Erase CSV files.
//        Helper.erase_CSV_file(fileName, "Distance(m)", "T_avg (kBps)");
        double chi = simParams.chi_for_position;
        simResult.chi_value = chi;
        simResult.JT_value = simParams.JT_VALUE;

        for (double distance = simParams.distance_initial; distance <= simParams.distance_final;
                distance += simParams.distance_increment) {
            simParams.distance_taken = distance;
            System.out.println("-->>Runnning simulation of avg UE throughput (kBps) vs distance(m) = " + simParams.distance_taken
                    + " , monte_carlo = " + simParams.monte_carlo + " times , JT = " + simParams.JT_VALUE + " , chi = " + chi);
            double avg_tpt = run_sim_one_distance_monte_carlo(FSPL_dB, inter_bs_distance, chi, baseStations);
            System.out.println("-->>Dist = " + simParams.distance_taken + " , T_avg = " + avg_tpt);
            simResult.distance_list.add(simParams.distance_taken);
            simResult.avg_throughput_UE_list.add(avg_tpt);
        }
        //run monte carlo
        return simResult;
    }

    public double run_sim_one_distance_monte_carlo(double FSPL_dB, double inter_bs_distance,
            double chi, List<BaseStation> baseStations) {
        double avg_throughput = 0;
        for (int mc = 1; mc <= simParams.monte_carlo; mc++) {
            double thpt = run_sim_for_one_distance_one_iteration(FSPL_dB, inter_bs_distance, chi, baseStations);
            avg_throughput += thpt;
        }

        avg_throughput /= ((double) (simParams.monte_carlo));
        return avg_throughput;
    }

    public double run_sim_for_one_distance_one_iteration(double FSPL_dB, double inter_bs_distance,
            double chi, List<BaseStation> baseStations) {
        double cumulative_throughput = 0;
        double num_users_total = 0;
        double bw_MHz = simParams.bandwidth / Math.pow(10, 6);
        int no_resource_blocks = ResourceBlockCalculator.numberOfResourceBlocks(bw_MHz);
        int num_users_per_BS = (int) (chi * no_resource_blocks); //All B.S. same chi
//        System.out.println("-->>num_users_per_BS = " + num_users_per_BS + " , no_resource_blocks = " + no_resource_blocks);
//        //TESTING ...
//        num_users_per_BS = 1;
        double Pn = simParams.NOISE_SPECTRAL_POWER_DENSITY + (10 * Math.log10(simParams.bandwidth));
        double Pn_mW = Helper.convert_To_mW_From_dBM(Pn);

        //Make each Base Station have num available slots as THIS NUMBER intially ... will decrement as UE is added.
        for (BaseStation bs : baseStations) {
            bs.num_available_slots = (int) (1 * no_resource_blocks); //chi = 1 IS the max
            bs.num_initial_slots = bs.num_available_slots;
        }

//        Helper.printBaseStations(baseStations);
        List<User> list_of_all_users = new ArrayList<>();
//        for (int itr_user = 0; itr_user < num_users_per_BS; itr_user++) {
        for (int bs_iter = 0; bs_iter < baseStations.size(); bs_iter++) {            
            BaseStation bs = baseStations.get(bs_iter);
            for (int itr_user = 0; itr_user < num_users_per_BS; itr_user++) {
//            for (int bs_iter = 0; bs_iter < baseStations.size(); bs_iter++) {
//                BaseStation bs = baseStations.get(bs_iter);
//                System.out.println("-->>TRYING TO CONNECT TO bs.id = " + bs.base_station_id + " , UE_id = " + itr_user);
                double theta = Math.random() * 2 * Math.PI; //an angle randomly taken from 0 to π [ALREADY in radians]
                double radial_distance_wrt_BS = simParams.distance_taken;
                /*
                Random rand = new Random();
                double random_double = rand.nextDouble();
                double x_user = (inter_bs_distance * random_double * Math.cos(theta)) + bs.x_pos;
                double y_user = (inter_bs_distance * random_double * Math.sin(theta)) + bs.y_pos;
                 */
                double x_user = (radial_distance_wrt_BS * Math.cos(theta)) + bs.x_pos; //r*cos(theta) + bs.x
                double y_user = (radial_distance_wrt_BS * Math.sin(theta)) + bs.y_pos; //r*sin(theta) + bs.y

                User user = new User(x_user, y_user, simParams);

                //Now calculation parts ...
                user.copyListOfBaseStations(baseStations);

                user.calculateReceivedPowersOfAllBaseStations(Pn_mW, FSPL_dB, baseStations);
                //WHICH APPROACH...
                if (Main.JT_MODE.equals(Main.JT_SINR)) {
                    user.sortBaseStations_wrt_Pr_mW_DESC();
                } else if (Main.JT_MODE.equals(Main.JT_DISTANCE)) {
                    user.sortBaseStations_wrt_Distances_ASC();
                } else if (Main.JT_MODE.equals(Main.JT_HYBRID)) {
                    //TO DO HYBRID....
                    System.out.println("-->>TO DO JT_HYBRID ...");
                }
//%% Power consumption of 7 BS's based on modified chi for hourly basis (BS x 24Hr )
//PcJT(BS,hr) = simParams.NTRX * ( simParams.P0 + chi(BS,hr) * simParams.Pmax * simParams.delp );
                double[] power_arr = user.getReceivedPowerArray();
//arr[0]:Co-ordinating received power in mW, arr[1]:OTHERs P_Rx, arr[2]:TOTAL, arr[3]: sum(OTHERs P_Rx * chi)
                double powers_recv_coordinating_BS = power_arr[0];
                double powers_recv_competing_BS_X_chi = power_arr[3];
                user.calculate_SINR_and_Throughput_of_UE(Pn_mW, powers_recv_coordinating_BS, powers_recv_competing_BS_X_chi);

                cumulative_throughput += user.THROUGHPUT_user_one_BS_KBps;
                //After calculations... [to get the same num_slots_available]
                user.sortBaseStations_wrt_baseStationID(); //SORT to get back the previous base stations list ids.
                baseStations = user.getListOfBaseStations(); //COPY base stations to get the available_tokens
                num_users_total++;

                list_of_all_users.add(user); //FOR DEBUG
//                bs.list_users.add(user); //FOR DEBUG
            }
//            System.out.println("");
        }


        /*
        //-->>>FOR DEBUGGING...
        List<BaseStation> list2 = list_users.get(list_users.size() - 1).getListOfBaseStations(); //Get final instance.
        for (int i = 0; i < list2.size(); i++) {
            BaseStation bs = list2.get(i);
            int x = 0;
            for (int j = 0; j < bs.list_users.size(); j++) {
                User ue = bs.list_users.get(j);
                if (ue.is_UE_dropped) {
                    x++;
                }
            }
            System.out.println("BS = " + bs.base_station_id + " , # UE dropped = " + x
                    + ", BS initialSlots = " + bs.num_initial_slots + " , BS available slots = " + bs.num_available_slots);
        }
         */
        //NORMAL USAGE [NOT USED ANYMORE]
        double avg_throughput = (num_users_total == 0) ? 0 : (cumulative_throughput / num_users_total);

        if (Main.TAKE_AFTER_CALCULATION) {

            List<User> new_user_list;

            if (Main.DUMMY_RING_TAKE) {
                List<User> without_outer_ring_users = new ArrayList<>();
                for (int iter = 0; iter < list_of_all_users.size(); iter++) {
                    User u = list_of_all_users.get(iter);
                    if (u.base_station_tier <= simParams.tier) {
                        System.out.println("-->>TAKING user with bs_tier = " + u.base_station_tier + " , bs_id = " + u.base_station_chosen_id);
                        without_outer_ring_users.add(u);
                    }
                }
                new_user_list = MetricCalculatorAfter.getNewUsersListAfter_Tavg_calculation(without_outer_ring_users,
                        baseStations, simParams, Pn_mW);
            } else {
                //Take all base stations.
                new_user_list = MetricCalculatorAfter.getNewUsersListAfter_Tavg_calculation(list_of_all_users,
                        baseStations, simParams, Pn_mW);

            }

            double cuml_throughput = 0.0;
            for (int i = 0; i < new_user_list.size(); i++) {
                cuml_throughput += (new_user_list.get(i).THROUGHPUT_user_one_BS_KBps);
            }
            num_users_total = (double)new_user_list.size();
            avg_throughput = (num_users_total == 0) ? 0 : (cuml_throughput / num_users_total);
        }

        return avg_throughput;
    }

//----------------------------------------------------------------------------------------------------------
}

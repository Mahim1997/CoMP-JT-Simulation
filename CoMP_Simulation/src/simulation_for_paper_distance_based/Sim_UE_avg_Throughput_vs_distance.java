package simulation_for_paper_distance_based;

import comp_simulation.Main;
import util_and_calculators.Helper;
import util_and_calculators.ResourceBlockCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sim_objects.BaseStation;
import sim_objects.User;
import sim_results.SimResult_Avg_T_vs_dist_per_chi;

import simulation_params.SimulationParameters;

public class Sim_UE_avg_Throughput_vs_distance {

    private SimulationParameters simParams;

    public Sim_UE_avg_Throughput_vs_distance(SimulationParameters simParams) {
        this.simParams = simParams;
    }

    //BELOW for Task 2 ... varying distance and calculating avg UE throughput
    public void runSimulationForSecondTask_NEW() {

        simParams.distance_initial = 0.1;
        simParams.distance_final = simParams.cell_radius;
        simParams.distance_increment = 50; //All in m

        
        String folderName = "UE_T_avg_vs_distance_BS";
        Main.PREV_MODE_JT = Main.JT_MODE;
        for (int JT = simParams.JT_INITIAL; JT <= simParams.JT_FINAL; JT++) {
            simParams.JT_VALUE = JT;
            if (JT == 0) {
                Main.JT_MODE = Main.JT_CONVENTIONAL;
            } else {
                Main.JT_MODE = Main.PREV_MODE_JT;
            }
            String fileName = folderName + "/UE_T_avg_vs_distance_BS_MC_" + String.valueOf(simParams.monte_carlo)
                    + "_JT_" + String.valueOf(simParams.JT_VALUE) + ".csv";
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
        for (int mc = 0; mc <= simParams.monte_carlo; mc++) {
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

//        //TESTING ...
//        num_users_per_BS = 1;
        double Pn = simParams.NOISE_SPECTRAL_POWER_DENSITY + (10 * Math.log10(simParams.bandwidth));
        double Pn_mW = Helper.convert_To_mW_From_dBM(Pn);

        //Make each Base Station have num available slots as THIS NUMBER intially ... will decrement as UE is added.
        for (BaseStation baseStation : baseStations) {
            baseStation.num_available_slots = (int) (1 * no_resource_blocks); //chi = 1 IS the max
        }

        for (int bs_iter = 0; bs_iter < baseStations.size(); bs_iter++) {
            BaseStation bs = baseStations.get(bs_iter);
            for (int itr_user = 0; itr_user < num_users_per_BS; itr_user++) {
                double theta = Math.random() * 2 * Math.PI; //an angle randomly taken from 0 to π [ALREADY in radians]
                Random rand = new Random();

                double radial_distance_wrt_BS = simParams.distance_taken;
                /*double random_double = rand.nextDouble();
                double x_user = (inter_bs_distance * random_double * Math.cos(theta)) + bs.x_pos;
                double y_user = (inter_bs_distance * random_double * Math.sin(theta)) + bs.y_pos;
                 */
                double x_user = (radial_distance_wrt_BS * Math.cos(theta)) + bs.x_pos; //r*cos(theta) + bs.x
                double y_user = (radial_distance_wrt_BS * Math.sin(theta)) + bs.y_pos; //r*sin(theta) + bs.y

                User user = new User(x_user, y_user);
                user.formSimulationParameters(simParams);

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
                double[] power_arr = user.getReceivedPowerArray();//arr[0]:Total received power in mW, arr[1]:OTHERs P_Rx, arr[2]:TOTAL

                if (Main.JT_MODE.equals(Main.JT_CONVENTIONAL)) {
                    double power_recv_bs = user.getListOfBaseStations().get(bs_iter).power_received_by_user_mW;
                    double total_power_of_all_bs = power_arr[2];
                    power_arr[0] = power_recv_bs;
                    power_arr[1] = total_power_of_all_bs - power_recv_bs;
                    //total power_arr[2] is SAME.
                }

                user.calculate_SINR_and_Throughput_of_UE(Pn_mW, power_arr);
                cumulative_throughput += user.THROUGHPUT_user_one_BS_KBps;
                //After calculations... [to get the same num_slots_available]
                baseStations = user.getListOfBaseStations();
                num_users_total++;
            }
        }
        double avg_throughput = (num_users_total == 0) ? 0 : (cumulative_throughput / num_users_total);
        return avg_throughput;
    }

//----------------------------------------------------------------------------------------------------------
}

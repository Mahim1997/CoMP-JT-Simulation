package simulation_for_paper_November_19;

import comp_simulation.Helper;
import comp_simulation.ResourceBlockCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import objects.BaseStation;
import objects.User;
import simulation_params.SimulationParameters;

public class Sim_UE_Throughput_vs_Chi {

    private SimulationParameters simParams;

    public Sim_UE_Throughput_vs_Chi(SimulationParameters simParams) {
        this.simParams = simParams;
    }

    public void runSimulationChi() {
        SimResults_Throughput_Chi simResults = new SimResults_Throughput_Chi(simParams);
        //Place Base Stations [Fixed Positions throughout all the simulations]
        List<BaseStation> baseStations = new ArrayList<>();
        BaseStation.placeBaseStations(baseStations, simParams.cell_radius, simParams.tier);

        //Always fixed parameters for all chi.
        double inter_bs_distance = Math.pow(3, 0.5) * simParams.cell_radius; // root(3) * cell_radius = IBS
        double FSPL_dB = (20 * Math.log10(simParams.path_loss_reference_distance))
                + (20 * Math.log10(simParams.frequency_carrier)) + 92.45; //FSPL_dB = 20*log_10(d_0) + 20*log_10(fc) + 92.45

        for (double chi_to_run = simParams.chi_initial; chi_to_run < simParams.chi_final; chi_to_run += simParams.chi_step_size) {
            double avg_throughput_one_chi = runSimulationForOneChi_MonteCarlo(FSPL_dB, inter_bs_distance, chi_to_run, baseStations);
            simResults.chi_list.add(chi_to_run);
            simResults.avg_UE_throughput_list.add(avg_throughput_one_chi);
        }
        simResults.printLists();
    }

    private double runSimulationForOneChi_MonteCarlo(double FSPL_dB, double inter_bs_distance,
            double chi, List<BaseStation> baseStations) {
        double avg_throughput = 0;
        System.out.println("-->>Runnning simulation of avg UE throughput (kBps) vs chi = " + chi
                + " , monte_carlo = " + simParams.monte_carlo + " times.");
        for (int mc = 0; mc < simParams.monte_carlo; mc++) {
            double avg_throughput_oneChi_oneMC = runSimulationForOneChi_OneIteration(FSPL_dB, inter_bs_distance, chi, baseStations);
            avg_throughput += avg_throughput_oneChi_oneMC;
        }
        avg_throughput /= ((double) (simParams.monte_carlo));

        return avg_throughput;
    }

    private double runSimulationForOneChi_OneIteration(double FSPL_dB, double inter_bs_distance,
            double chi, List<BaseStation> baseStations) {
//        System.out.println("-->>Inside running for one chi = " + chi);

        //Calculate fixed values beforehand to save computation time inside the loops.
        double num_users_total = 0;
        double bw_MHz = simParams.bandwidth / Math.pow(10, 6);
        int no_resource_blocks = ResourceBlockCalculator.numberOfResourceBlocks(bw_MHz);
        int num_users_per_BS = (int) (chi * no_resource_blocks); //All B.S. same chi
        double Pn = -174 + (10 * Math.log10(simParams.bandwidth));
        double Pn_mW = Helper.convert_To_mW_From_dBM(Pn);

        //Adding the users...
        for (int base_station_iter = 0; base_station_iter < baseStations.size(); base_station_iter++) {
            BaseStation bs = baseStations.get(base_station_iter);
            //Now place UEs random positions , wrt this Base Station
            for (int user_iter = 0; user_iter < num_users_per_BS; user_iter++) {
                double theta = Math.random() * 2 * Math.PI; //an angle randomly taken from 0 to π [ALREADY in radians]
                Random rand = new Random();
                double ibs_random_user_wrt_BS = rand.nextDouble();
                double x_user = (inter_bs_distance * ibs_random_user_wrt_BS * Math.cos(theta)) + bs.x_pos;
                double y_user = (inter_bs_distance * ibs_random_user_wrt_BS * Math.sin(theta)) + bs.y_pos;
                User user = new User(x_user, y_user);
                user.formSimulationParameters(simParams);
                bs.users_of_this_baseStation.add(user);
            }
        }
        double cumulative_throughput_kBps = 0;
        //Now power calculations... FOR EACH USER
        for (int i = 0; i < baseStations.size(); i++) {
            BaseStation bs = baseStations.get(i);
            for (int k = 0; k < bs.users_of_this_baseStation.size(); k++) {
                User user = bs.users_of_this_baseStation.get(k);
                num_users_total += 1;
//Received Powers will be stored in a map [already sorted in DESCENDING order]
                user.calculateReceivedPowersOfAllBaseStations(Pn_mW, FSPL_dB, baseStations);
//power_arr[0] = Pr_mW for co-ordinating BSs, arr[1] = Pr_mW of remaining OTHER BSs, arr[2] = TOTAL Pr_mW
                double[] power_arr = user.getBestAndOtherReceivedPower(simParams.JT_VALUE);
                user.calculate_SINR_and_Throughput_of_UE(Pn_mW, power_arr);
                cumulative_throughput_kBps += user.THROUGHPUT_user_one_BS_KBps;
            }
        }

        double average_throughput = cumulative_throughput_kBps / num_users_total; //num_users_total is ALREADY in double
        return average_throughput;
    }

}

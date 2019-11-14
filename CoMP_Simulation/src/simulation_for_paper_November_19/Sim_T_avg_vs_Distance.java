package simulation_for_paper_November_19;

import comp_simulation.Helper;
import comp_simulation.ResourceBlockCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import objects.BaseStation;
import objects.User;
import simulation_params.SimulationParameters;

public class Sim_T_avg_vs_Distance {

    private SimulationParameters simParams;

    public Sim_T_avg_vs_Distance(SimulationParameters simParams) {
        this.simParams = simParams;
    }

    //BELOW for Task 2 ... varying distance and calculating avg UE throughput
    public void runSimulationForSecondTask() {

        simParams.chi_for_position = 0.6; // To keep consistent wrt task 1 [so , JTs don't fluctuate themselves]
        simParams.distance_initial = 0.1;
        simParams.distance_final = simParams.cell_radius;
        simParams.distance_increment = 10; //All in m

        for (int JT = simParams.JT_INITIAL; JT <= simParams.JT_FINAL; JT++) {
//            for(double chi = 0.1; chi < 0.9; chi++){
//                
//            }
            simParams.JT_VALUE = JT;

            run_T_avg_vs_Distance();
        }
    }

    public void run_T_avg_vs_Distance() {
        SimResults_Throughput_Chi simResults = new SimResults_Throughput_Chi(simParams);
        //Place Base Stations [Fixed Positions throughout all the simulations]
        List<BaseStation> baseStations = new ArrayList<>();
        BaseStation.placeBaseStations(baseStations, simParams.cell_radius, simParams.tier);
        String folderName = "UE_T_avg_vs_minDis_BS";
        String fileName = folderName + "/UE_T_avg_vs_minDis_BS_MC_" + String.valueOf(simParams.monte_carlo)
                + "_JT_" + String.valueOf(simParams.JT_VALUE) + ".csv";
        //Always fixed parameters for all chi.
        double inter_bs_distance = Math.pow(3, 0.5) * simParams.cell_radius; // root(3) * cell_radius = IBS
        double FSPL_dB = (20 * Math.log10(simParams.path_loss_reference_distance))
                + (20 * Math.log10(simParams.frequency_carrier)) + 92.45; //FSPL_dB = 20*log_10(d_0) + 20*log_10(fc) + 92.45

        //Erase CSV files.
        Helper.erase_CSV_file(fileName, "Distance(m)", "T_avg (kBps)");
        double chi = simParams.chi_for_position;
        for (double distance = simParams.distance_initial; distance <= inter_bs_distance; distance += simParams.distance_increment) {
            simParams.distance_taken = distance;

            System.out.println("-->>Runnning simulation of avg UE throughput (kBps) vs distance(m) = " + simParams.distance_taken
                    + " , monte_carlo = " + simParams.monte_carlo + " times , JT = " + simParams.JT_VALUE);
            //Run monte_carlo times for THIS value of CHI and write that to the CSV file.
            double avg_tpt = run_sim_one_distance_monte_carlo(FSPL_dB, inter_bs_distance, chi, baseStations);
            Helper.writeCSV_row1_row2(fileName, String.valueOf(distance), String.valueOf(avg_tpt));
        }
        //run monte carlo

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
        
        
        double Pn = -174 + (10 * Math.log10(simParams.bandwidth));
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
                user.sortBaseStations_wrt_Pr_mW();
//%% Power consumption of 7 BS's based on modified chi for hourly basis (BS x 24Hr )
//PcJT(BS,hr) = simParams.NTRX * ( simParams.P0 + chi(BS,hr) * simParams.Pmax * simParams.delp );
                double[] power_arr = user.getReceivedPowerArray();//arr[0]:Total received power in mW, arr[1]:OTHERs P_Rx, arr[2]:TOTAL
                user.calculate_SINR_and_Throughput_of_UE(Pn_mW, power_arr);
                /*
                System.out.println("AFTER SORTING ... printing base stations ....");
                for(BaseStation bs1:user.getListOfBaseStations()){
                    System.out.println(bs1.toString());
                }
                System.out.println("PRINTING power_arr, 0->" + power_arr[0] + ", 1->" + power_arr[1] + ", 2->" + power_arr[2]);
                System.out.println("UE Throughput = " + user.THROUGHPUT_user_one_BS_KBps + ", UE SINR = " + user.SINR_user_one_BS);
                System.out.println("------------------------------------------------");
                 */

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

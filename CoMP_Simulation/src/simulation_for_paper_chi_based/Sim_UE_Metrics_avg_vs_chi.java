package simulation_for_paper_chi_based;

import comp_simulation.Main;
import util_and_calculators.Helper;
import util_and_calculators.ResourceBlockCalculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import sim_objects.BaseStation;
import sim_objects.User;
import sim_results.SimResult_oneMC;
import sim_results.SimResults;
import simulation_params.SimulationParameters;

public class Sim_UE_Metrics_avg_vs_chi {
    private static boolean IS_CONVENTIONAL = false;
    private final SimulationParameters simParams;

    public Sim_UE_Metrics_avg_vs_chi(SimulationParameters simParams) {
        this.simParams = simParams;
    }

    public void runSimulationTask1() {
        Main.PREV_MODE_JT = Main.JT_MODE;
        for (int JT = simParams.JT_INITIAL; JT <= simParams.JT_FINAL; JT++) {
            simParams.JT_VALUE = JT;
            if (JT == 0) {
                IS_CONVENTIONAL = true;
//                Main.JT_MODE = Main.JT_CONVENTIONAL;
                Main.JT_MODE = Main.JT_DISTANCE;
                simParams.JT_VALUE = 1;
            } else {
                IS_CONVENTIONAL = false;
                Main.JT_MODE = Main.PREV_MODE_JT;
            }
            run_UE_Tavg_vs_chi();
        }
    }

    public void run_UE_Tavg_vs_chi() {
        //Place Base Stations [Fixed Positions throughout all the simulations]
        List<BaseStation> baseStations = new ArrayList<>();
        BaseStation.placeBaseStations(baseStations, simParams.cell_radius, simParams.tier);
        String folderName = "Avg_Th_Chi";
        String fileName = folderName + "/Avg_Throughput_vs_chi_MC_" + String.valueOf(simParams.monte_carlo)
                + "_JT_" + String.valueOf(simParams.JT_VALUE) + ".csv";
        if(IS_CONVENTIONAL){
            System.out.println("-->>HEREE >>>> Running for Conventional ... ");
            fileName = folderName + "/Avg_Throughput_vs_chi_MC_" + String.valueOf(simParams.monte_carlo)
                + "_JT_0.csv";
        }
        //Always fixed parameters for all chi.
        double inter_bs_distance = Math.pow(3, 0.5) * simParams.cell_radius; // root(3) * cell_radius = IBS
        double FSPL_dB = (20 * Math.log10(simParams.path_loss_reference_distance))
                + (20 * Math.log10(simParams.frequency_carrier)) + 92.45; //FSPL_dB = 20*log_10(d_0) + 20*log_10(fc) + 92.45

        //for each chi
        SimResults simResults = new SimResults();
        SimResult_oneMC res_one_MC;
        for (double chi = simParams.chi_initial; chi <= simParams.chi_final; chi += simParams.chi_step_size) {
            System.out.println("-->>Runnning simulation of avg UE throughput (kBps) vs chi = " + chi
                    + " , monte_carlo = " + simParams.monte_carlo + " times , JT = " + simParams.JT_VALUE
                    + " , fileName = " + fileName);
            //Run monte_carlo times for THIS value of CHI and write that to the CSV file.
            res_one_MC = run_sim_one_chi_monte_carlo(FSPL_dB, inter_bs_distance, chi, baseStations);
            simResults.enterMetricsForOneMC(chi, res_one_MC);
            simResults.write_to_csv_file(fileName);

        }

    }

    public SimResult_oneMC run_sim_one_chi_monte_carlo(double FSPL_dB, double inter_bs_distance,
            double chi, List<BaseStation> baseStations) {
        double avg_throughput = 0;
        List<User> list_users;
        SimResult_oneMC finalSimResult = new SimResult_oneMC();
        SimResult_oneMC currentSimResult;
        for (int mc = 0; mc <= simParams.monte_carlo; mc++) {
            list_users = run_sim_for_one_chi_one_iteration(FSPL_dB, inter_bs_distance, chi, baseStations);
            currentSimResult = new SimResult_oneMC();
//Calculate metrics like Avg T, Spectral Efficiency, etc
            currentSimResult.calculate_metrics(list_users, simParams);
            finalSimResult.addMetrics(currentSimResult);
        }
        double divide_by = (double) (simParams.monte_carlo);
        finalSimResult.divideMetricsBy(divide_by);

        return finalSimResult;
    }

    public List<User> run_sim_for_one_chi_one_iteration(double FSPL_dB, double inter_bs_distance,
            double chi, List<BaseStation> baseStations) {
        double cumulative_throughput = 0;
        double num_users_total = 0;
        double bw_MHz = simParams.bandwidth / Math.pow(10, 6);
        int no_resource_blocks = ResourceBlockCalculator.numberOfResourceBlocks(bw_MHz);
        int num_users_per_BS = (int) (chi * no_resource_blocks); //All B.S. same chi
        double Pn = simParams.NOISE_SPECTRAL_POWER_DENSITY + (10 * Math.log10(simParams.bandwidth)); //Pn = -174 + 10*log(BW)
        double Pn_mW = Helper.convert_To_mW_From_dBM(Pn);

        //Make each Base Station have num available slots as THIS NUMBER intially ... will decrement as UE is added.
        for (BaseStation bs : baseStations) {
            bs.num_available_slots = (int) (1 * no_resource_blocks); //chi = 1 IS the max
            bs.num_initial_slots = bs.num_available_slots;
        }

        List<User> list_of_all_users = new ArrayList<>();
        for (int bs_iter = 0; bs_iter < baseStations.size(); bs_iter++) {
            BaseStation bs = baseStations.get(bs_iter);
            for (int itr_user = 0; itr_user < num_users_per_BS; itr_user++) {
                double theta = Math.random() * 2 * Math.PI; //an angle randomly taken from 0 to π [ALREADY in radians]
                Random rand = new Random();
                double random_double_val = rand.nextDouble();
//                double x_user = (inter_bs_distance * random_double_val * Math.cos(theta)) + bs.x_pos;
//                double y_user = (inter_bs_distance * random_double_val * Math.sin(theta)) + bs.y_pos;

// --------------------------- Keep UE within cell-radius of each Base Station -------------------------------------
                double x_user = (simParams.cell_radius * random_double_val * Math.cos(theta)) + bs.x_pos;
                double y_user = (simParams.cell_radius * random_double_val * Math.sin(theta)) + bs.y_pos;
                User user = new User(x_user, y_user);
                user.formSimulationParameters(simParams);
                //Now calculation parts ...
                user.copyListOfBaseStations(baseStations);
                user.calculateReceivedPowersOfAllBaseStations(Pn_mW, FSPL_dB, baseStations);//Calculate received powers

                //WHICH APPROACH...
                if (Main.JT_MODE.equals(Main.JT_SINR)) {
                    user.sortBaseStations_wrt_Pr_mW_DESC();
                } else if (Main.JT_MODE.equals(Main.JT_DISTANCE)) {
//                    System.out.println("Distance based sorting...");
                    user.sortBaseStations_wrt_Distances_ASC();
                } else if (Main.JT_MODE.equals(Main.JT_HYBRID)) {
                    //TO DO HYBRID....
                    System.out.println("-->>TO DO JT_HYBRID ... 117 of Sim_HT_T_avg_vs_chi.java");
                }

//%% Power consumption of 7 BS's based on modified chi for hourly basis (BS x 24Hr )
//PcJT(BS,hr) = simParams.NTRX * ( simParams.P0 + chi(BS,hr) * simParams.Pmax * simParams.delp );
//power_arr[0]:Co-ordinating BS's received power, arr[1]:Other's Pr_mW, arr[2]:TOTAL
                double[] power_arr = user.getReceivedPowerArray();
                //FOR CONVENTIONAL APPROACH.
                if (Main.JT_MODE.equals(Main.JT_CONVENTIONAL)) {
                    double power_recv_bs = user.getListOfBaseStations().get(bs_iter).power_received_by_user_mW;
                    double total_power_of_all_bs = power_arr[2];
                    power_arr[0] = power_recv_bs;
                    power_arr[1] = total_power_of_all_bs - power_recv_bs;
                    //total power_arr[2] is SAME.
                }
                double factor = power_arr[3];
                user.calculate_SINR_and_Throughput_of_UE(Pn_mW, power_arr, factor);
                cumulative_throughput += user.THROUGHPUT_user_one_BS_KBps;
                user.sortBaseStations_wrt_baseStationID(); //SORT to get back the previous base stations list ids.
                
//                System.out.println("-->>AFTER sorting wrt CID ... printing basestaiotns. ");
//                Helper.printBaseStations(baseStations);
                
                baseStations = user.getListOfBaseStations();  //After calculations... [to get the same num_slots_available]
                num_users_total++;
                list_of_all_users.add(user); //for further computations...
//                bs.list_users.add(user);
            }
        }
        
        /*
        //-->>>FOR DEBUGGING...
        List<BaseStation> list2 = list_of_all_users.get(list_of_all_users.size() - 1).getListOfBaseStations(); //Get final instance.
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

        
        
        return list_of_all_users;
    }

//----------------------------------------  CLASS ENDS -----------------------------------------------------------
}

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

    /*
    JT = VALUE_OF_JT    = 0 -> Conventional [only distances]
                        = 1 -> DPS
                        = 2 -> JT (2 BS give power simultaneously to the UE)
                        and so on...
     */
    public static int VALUE_OF_JT = 1;
    private SimulationParameters simParams;

    public Sim_UE_Throughput_vs_Chi(SimulationParameters simParams) {
        this.simParams = simParams;
    }

    public void runSimulationChi() {
        //Place Base Stations [Fixed Positions throughout all the simulations]
        List<BaseStation> baseStations = new ArrayList<>();
        BaseStation.placeBaseStations(baseStations, simParams.cell_radius, simParams.tier);

        //Always fixed parameters for all chi.
        double inter_bs_distance = Math.pow(3, 0.5) * simParams.cell_radius; // root(3) * cell_radius = IBS
        double FSPL_dB = (20 * Math.log10(simParams.path_loss_reference_distance))
                + (20 * Math.log10(simParams.frequency_carrier)) + 92.45; //FSPL_dB = 20*log_10(d_0) + 20*log_10(fc) + 92.45

        for (double chi_to_run = simParams.initial_chi; chi_to_run < simParams.final_chi; chi_to_run += simParams.step_size) {
            runSimulationForOneChi(FSPL_dB, inter_bs_distance, chi_to_run, baseStations);
        }
    }

    private void runSimulationForOneChi(double FSPL_dB, double inter_bs_distance,
            double chi, List<BaseStation> baseStations) {
        System.out.println("-->>Inside running for one chi = " + chi);

        //Calculate fixed values beforehand to save computation time inside the loops.
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

        //Now power calculations... FOR EACH USER
        for (int i = 0; i < baseStations.size(); i++) {
            BaseStation bs = baseStations.get(i);
            for (int k = 0; k < bs.users_of_this_baseStation.size(); k++) {
                User user = bs.users_of_this_baseStation.get(k);
                //Calculate the received powers of EACH B.S. for THIS user. 
                user.calculateReceivedPowersOfAllBaseStations(Pn_mW, FSPL_dB, baseStations); //will store in a map [already sorted]
                user.printSortedMap();
                System.out.println("-------------------------------------");
                
                //Now keep the first (JT) number of Base stations as myself.
                
            }
        }

    }

}

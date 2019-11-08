package objects;

import comp_simulation.Helper;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import simulation_params.SimulationParameters;

public class User {

    public double x_pos;
    public double y_pos;

    public double SINR_user_one_BS;
    public double PATH_LOSS_this_user_mW;
    public double POWER_RECEIVED_ONE_BS_mW;
    public double THROUGHPUT_user_one_BS_KBps;

    public double get_RECEIVED_POWER_mW_for_one_BS(double FSPL_dB, BaseStation bs,
            SimulationParameters simParams) {
        double normal_random_generated_value = 0;
        double mean = 0;
        Random rand = new Random();
        normal_random_generated_value = (rand.nextGaussian() * simParams.path_loss_standard_deviation) + mean;
        double PL_dB = FSPL_dB
                + (10 * simParams.path_loss_exponent_alpha * (getDistanceFromBS(bs) / (simParams.path_loss_reference_distance * 1000)))
                + normal_random_generated_value;

        double Pr = simParams.power_transmitted - PL_dB;    //received power = transmitted power - (FIXED LOSS + VARIABLE LOSS)
        double Pr_mW = Helper.convert_To_mW_From_dBM(Pr);
        return Pr_mW;
    }

    public double[] get_RECEIVED_POWER_of_all_BS(double FSPL_dB, List<BaseStation> baseStations,
            SimulationParameters simParams) {

        double[] Pr_mW_arr = new double[baseStations.size()];
        for (int i = 0; i < baseStations.size(); i++) {
            Pr_mW_arr[i] = get_RECEIVED_POWER_mW_for_one_BS(FSPL_dB, baseStations.get(i), simParams);
        }

        return Pr_mW_arr;
    }

    public User(double x_pos, double y_pos) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    public double getDistanceFromBS(BaseStation bs) {
        return Helper.getDistance(this.x_pos, this.y_pos, bs.x_pos, bs.y_pos);
    }

    public double[] get_distances_of_all_baseStations(List<BaseStation> baseStations) {
        int num_bs = baseStations.size();
        double[] distance_arr = new double[num_bs];

        for (int i = 0; i < baseStations.size(); i++) {
            distance_arr[i] = (this.getDistanceFromBS(baseStations.get(i)));
        }

        return distance_arr;
    }

//-------------------------------------------------------------------------------------------------------    
//BELOW are for paper's work
    private SimulationParameters simParams;
    public TreeMap<Double, BaseStation> sorted_map_of_ReceivedPower_vs_BS = new TreeMap<>();

    public void formSimulationParameters(SimulationParameters s) {
        this.simParams = s;
    }

    public double calculateReceivedPowerFromOneBaseStation(double Pn_mW, double FSPL_dB, BaseStation bs) {
        //total power loss = FSPL_dB (fixed) + distance_loss + fading_effect
        double d_UE_from_BS = getDistanceFromBS(bs);
        double alpha = simParams.path_loss_exponent_alpha;
        double d0 = simParams.path_loss_reference_distance;
        Random rand = new Random();
        double normal_random_generated_value = (rand.nextGaussian() * simParams.path_loss_standard_deviation);
        double PL_dB = FSPL_dB /*fixed loss*/
                + (10 * alpha * Math.log10(d_UE_from_BS / (d0 * 1000))) /*for distance based path loss*/
                + normal_random_generated_value;/*for fading*/

        double Pt_dB = simParams.power_transmitted;
        //Pr_mW is the received power from THIS BS in mW unit
        double Pr_mW = Helper.convert_To_mW_From_dBM(Pt_dB - PL_dB);

        return Pr_mW;
    }

    public void calculateReceivedPowersOfAllBaseStations(double Pn_mW, double FSPL_dB, List<BaseStation> baseStations) {
        //Pn_mW is the noise power in mW AND is fixed, where Pn = -174 + 10*log_10(BW) and Pn_mW = to_mw(Pn)
        for (int i = 0; i < baseStations.size(); i++) {
            BaseStation bs = baseStations.get(i);

            double Pr_BS_mW = calculateReceivedPowerFromOneBaseStation(Pn_mW, FSPL_dB, bs);
            this.sorted_map_of_ReceivedPower_vs_BS.put(Pr_BS_mW, bs);
        }
    }

    public void sortMapDescending() {
        // Create the map and provide the comparator as a argument
        Map<Double, BaseStation> desc_sort_map = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o2.compareTo(o1);
            }
        });
        desc_sort_map.putAll(this.sorted_map_of_ReceivedPower_vs_BS);
        this.sorted_map_of_ReceivedPower_vs_BS.clear();
        this.sorted_map_of_ReceivedPower_vs_BS = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o2.compareTo(o1);
            }
        });
        this.sorted_map_of_ReceivedPower_vs_BS.putAll(desc_sort_map);
    }

    public void printSortedMap() {
        Helper.printMap(this.sorted_map_of_ReceivedPower_vs_BS);
    }

    public double[] getReceivedPowersFromSortedAscendingOrderMap() {
        return null;

    }
}

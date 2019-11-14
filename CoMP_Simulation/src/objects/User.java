package objects;

import util_and_calculators.Helper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import simulation_params.SimulationParameters;

public class User {

    public double x_pos;
    public double y_pos;

    private List<BaseStation> baseStations;
    private SimulationParameters simParams;
    public double SINR_user_one_BS;
    public double THROUGHPUT_user_one_BS_KBps;

    //For Task 2
    public double distance_min_BS;

    public User(double x, double y) {
        this.x_pos = x;
        this.y_pos = y;
        this.baseStations = new ArrayList<>();
    }

    public List<BaseStation> getListOfBaseStations() {
        return this.baseStations;
    }

    public void copyListOfBaseStations(List<BaseStation> baseStationList) {
        this.baseStations.clear();
        this.baseStations = new ArrayList<>(baseStationList);
    }

    public void formSimulationParameters(SimulationParameters simParams) {
        this.simParams = simParams;
    }

    public double getDistanceFromBS(BaseStation bs) {
        return Helper.getDistance(x_pos, y_pos, bs.x_pos, bs.y_pos);
    }

    public double calculate_Pr_mW_One_BS(double Pn_mW, double FSPL_dB, BaseStation bs) {
        //total power loss = FSPL_dB (fixed) + distance_loss + fading_effect
        double d_UE_from_BS = getDistanceFromBS(bs);
        double alpha = simParams.path_loss_exponent_alpha;
        double d0 = simParams.path_loss_reference_distance;
        Random rand = new Random();
//For fading_effect
        double normal_random_generated_value = (rand.nextGaussian() * simParams.path_loss_standard_deviation); //mean = 0
        double PL_dB = FSPL_dB /*fixed loss*/
                + (10 * alpha * Math.log10(d_UE_from_BS / (d0 * 1000))) /*for distance based path loss*/
                + normal_random_generated_value;/*for fading*/

        double Pt_dB = simParams.power_transmitted;
        //Pr_mW is the received power from THIS BS in mW unit
        double Pr_mW = Helper.convert_To_mW_From_dBM(Pt_dB - PL_dB);

        return Pr_mW;
    }

    public void calculateReceivedPowersOfAllBaseStations(double Pn_mW, double FSPL_dB, List<BaseStation> baseStations) {
        this.baseStations.clear();
        for (int i = 0; i < baseStations.size(); i++) {
            BaseStation bs = baseStations.get(i);
            double pow = calculate_Pr_mW_One_BS(Pn_mW, FSPL_dB, bs);
            bs.power_received_by_user_mW = pow;
            this.baseStations.add(bs);
        }
    }

    public void sortBaseStations_wrt_Pr_mW() {
        //Descending order sort
        Collections.sort(this.baseStations, (BaseStation b1, BaseStation b2) -> {
            if (b1.power_received_by_user_mW < b2.power_received_by_user_mW) {
                return 1;
            } else {
                return -1;
            }
        });
    }

    public double[] getReceivedPowerArray() {
        double[] power_arr = new double[3]; //arr[0]:Co-ordinating BS's received power, arr[1]:Other's Pr_mW, arr[2]:TOTAL

        if (simParams.JT_VALUE > baseStations.size()) {
            System.out.println("In User.getReceivedPowerArray() ... simParams.JT_VALUE > baseStations.size() .. exiting -1");
            System.exit(-1);
        }

        //Take best JT number of BASE_STATIONS (IF AVAILABLE)
        boolean drop_ue = false;
        for (int i = 0; i < simParams.JT_VALUE; i++) {
            if (baseStations.get((i)).num_available_slots <= 0) { //descending order ... sorted wrt Received power in mW
                //NO MORE AVAILABLE FOR USERS....
                drop_ue = true;
                break;
            }
        }
        double coordinating_bs_received_power_idx_0 = 0, others_power_idx_1 = 0, total_power_idx_2 = 0;
        if (drop_ue) {
            //DROPPED
            coordinating_bs_received_power_idx_0 = 0;
            for (int i = 0; i < baseStations.size(); i++) {
                total_power_idx_2 += baseStations.get(i).power_received_by_user_mW;
            }
            others_power_idx_1 = total_power_idx_2;
        } else {
            //TAKEN
            int num_BS_selected_so_far = 0;
            for (int i = 0; i < baseStations.size(); i++) {
                BaseStation bs = baseStations.get(i);
                total_power_idx_2 += bs.power_received_by_user_mW;
                if (num_BS_selected_so_far < simParams.JT_VALUE) {
                    //Within JT , so decrement no_available_slots for BS.
                    bs.num_available_slots--;
                    coordinating_bs_received_power_idx_0 += bs.power_received_by_user_mW;
                } else {
                    others_power_idx_1 += bs.power_received_by_user_mW;
                }
                num_BS_selected_so_far++;
            }
        }
        power_arr[0] = coordinating_bs_received_power_idx_0;
        power_arr[1] = others_power_idx_1;
        power_arr[2] = total_power_idx_2;
        return power_arr;
    }

    //calculates the throughput and SINR [Pn_mW is the noise power in mW]
    public void calculate_SINR_and_Throughput_of_UE(double Pn_mW, double[] received_powers_mW) {
        double numerator = received_powers_mW[0];
        double denominator = received_powers_mW[1];
        this.SINR_user_one_BS = (numerator / (Pn_mW + denominator));
        this.THROUGHPUT_user_one_BS_KBps = 180 * (Helper.log2(1 + this.SINR_user_one_BS));

    }
}

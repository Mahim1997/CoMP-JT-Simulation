package sim_objects;

import util_and_calculators.Helper;
import java.util.ArrayList;
import java.util.Collections;
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

    public boolean is_UE_dropped = false;

    //For AFTERWARDS throughput calculations.
    public List<Double> power_received_from_eachBS_or_X_chi; //wrt base station ID
    public List<Integer> indices_base_stations_connected;

    public List<BaseStation> getListOfBaseStations() {
        return this.baseStations;
    }

    public User(double x, double y) {
        this.x_pos = x;
        this.y_pos = y;
        this.baseStations = new ArrayList<>();
        this.is_UE_dropped = false;
        this.power_received_from_eachBS_or_X_chi = new ArrayList<>();
        this.indices_base_stations_connected = new ArrayList<>();
    }

    private String getBaseStationIDs(List<BaseStation> bs_l) {
        String s = "";
        for (BaseStation bs : bs_l) {
            s += (String.valueOf(bs.base_station_id) + ",");
        }
        return s;
    }

    public void copyListOfBaseStations(List<BaseStation> baseStationList) {
        this.baseStations.clear();
        this.baseStations = new ArrayList<>(baseStationList);
    }

    public void sortBaseStations_wrt_baseStationID() {
        //Ascending order sort
        Collections.sort(this.baseStations, (BaseStation b1, BaseStation b2) -> {
            if (b2.base_station_id < b1.base_station_id) {
                return 1;
            } else {
                return -1;
            }
        });
    }

    public void formSimulationParameters(SimulationParameters simParams) {
        this.simParams = simParams;
    }

    public double getDistanceFromBS(BaseStation bs) {
        return Helper.getDistance(x_pos, y_pos, bs.x_pos, bs.y_pos);
    }

    public double calculate_received_Power_one_BS(double Pn_mW, double FSPL_dB, BaseStation bs) {
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
            double pow = calculate_received_Power_one_BS(Pn_mW, FSPL_dB, bs);
            bs.power_received_by_user_mW = pow;
            this.baseStations.add(bs);
        }
    }

    public void sortBaseStations_wrt_Distances_ASC() {
        //Ascending order sort
        Collections.sort(this.baseStations, (BaseStation b1, BaseStation b2) -> {
//            if (b2.power_received_by_user_mW < b1.power_received_by_user_mW) {
            if (getDistanceFromBS(b2) < getDistanceFromBS(b1)) {
                return 1;
            } else {
                return -1;
            }
        });
    }

    public void sortBaseStations_wrt_Pr_mW_DESC() {
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
        double[] power_arr = new double[4]; //arr[0]:Co-ordinating BS's received power, arr[1]:Other's Pr_mW, arr[2]:TOTAL, arr[3]:factor
        double denominator_competing_bs_power_multiplied_with_chi = 0;
        if (simParams.JT_VALUE > baseStations.size()) {
            System.out.println("In User.getReceivedPowerArray() ... simParams.JT_VALUE > baseStations.size() .. exiting -1");
            System.exit(-1);
        }

        //Take best JT number of BASE_STATIONS (IF AVAILABLE)
        boolean drop_ue = false;
        for (int i = 0; i < simParams.JT_VALUE; i++) {
            if (baseStations.get((i)).num_available_slots <= 0) { //descending order ... sorted wrt Received power in mW
                //NO MORE AVAILABLE FOR USERS....
//                System.out.println("-->>Inside User.java, UE dropped .. bs_id = " + baseStations.get(i).base_station_id + " , num_available slots = "
//                 + baseStations.get(i).num_available_slots);
                drop_ue = true;
                this.is_UE_dropped = true; //DROP UE.
                break;
            }
        }
        double coordinating_bs_received_power_idx_0 = 0, others_power_idx_1 = 0, total_power_idx_2 = 0;
        if (drop_ue == true) {
            //DROPPED
            coordinating_bs_received_power_idx_0 = 0;
            for (int i = 0; i < baseStations.size(); i++) {
                total_power_idx_2 += baseStations.get(i).power_received_by_user_mW;
            }
            others_power_idx_1 = total_power_idx_2;
        } else {
            //TAKEN
            int num_BS_selected_so_far = 0;
            denominator_competing_bs_power_multiplied_with_chi = 0;
            for (int i = 0; i < baseStations.size(); i++) {
                BaseStation bs = baseStations.get(i);
                total_power_idx_2 += bs.power_received_by_user_mW;
                if (num_BS_selected_so_far < simParams.JT_VALUE) {
                    bs.num_available_slots--;//Within JT , so decrement no_available_slots for BS. [Co-ordinating BSs]
                    coordinating_bs_received_power_idx_0 += bs.power_received_by_user_mW;
                    this.indices_base_stations_connected.add(bs.base_station_id); //Add to base station ID list
                    this.power_received_from_eachBS_or_X_chi.add(bs.power_received_by_user_mW);
                } else {
                    //Competing Base-Stations is here.
                    double factor = (double) ((((double) (bs.num_initial_slots - bs.num_available_slots))
                            / ((double) (bs.num_initial_slots))));
                    denominator_competing_bs_power_multiplied_with_chi += (factor * bs.power_received_by_user_mW);
                    others_power_idx_1 += bs.power_received_by_user_mW;
                    this.power_received_from_eachBS_or_X_chi.add((factor * bs.power_received_by_user_mW));
                }
                num_BS_selected_so_far++;
            }
        }
        power_arr[0] = coordinating_bs_received_power_idx_0;
        power_arr[1] = others_power_idx_1;
        power_arr[2] = total_power_idx_2;
        power_arr[3] = denominator_competing_bs_power_multiplied_with_chi;
        return power_arr;
    }

    //calculates the throughput and SINR [Pn_mW is the noise power in mW]
    public void calculate_SINR_and_Throughput_of_UE(double power_noise_mW, double[] received_powers_mW) {
        double powers_of_coordinating_baseStations = received_powers_mW[0];
        double powers_of_competing_baseStations = received_powers_mW[1];
        this.SINR_user_one_BS = (powers_of_coordinating_baseStations / (power_noise_mW + powers_of_competing_baseStations));
        this.THROUGHPUT_user_one_BS_KBps = 180.0 * (Helper.log2(1 + this.SINR_user_one_BS));
    }

    public void calculate_SINR_and_Throughput_of_UE(double power_noise_mW, double powers_of_coordinating_baseStations,
            double powers_of_competing_baseStations_X_chi) {
        this.SINR_user_one_BS = (powers_of_coordinating_baseStations) / (power_noise_mW + powers_of_competing_baseStations_X_chi);
//        this.SINR_user_one_BS = (powers_of_coordinating_baseStations / (power_noise_mW + (factor * powers_of_competing_baseStations)));
        this.THROUGHPUT_user_one_BS_KBps = 180 * (Helper.log2(1 + this.SINR_user_one_BS));
    }
}

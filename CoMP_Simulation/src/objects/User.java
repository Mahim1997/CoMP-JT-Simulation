package objects;

import comp_simulation.Helper;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import simulation_params.SimulationParameters;

public class User {

    public double x_pos;
    public double y_pos;

    //Hashmap <SINR, BASESTATION> for DPS, and JT methods. //TODO
    //One param SINR for Conventional Method
    public double SINR_user_one_BS;
    public double PATH_LOSS_this_user_mW;
    public double POWER_RECEIVED_ONE_BS_mW;
    public double THROUGHPUT_user_one_BS_KBps;

    public double get_RECEIVED_POWER_mW_for_one_BS(double FSPL_dB, BaseStation bs,
            SimulationParameters simParams) {
        double normal_random_generated_value = 0;
        double mean = 0;
        Random rand = new Random();

//        rand.setSeed(2);
//        simParams.path_loss_standard_deviation = 1;
        normal_random_generated_value = (rand.nextGaussian() * simParams.path_loss_standard_deviation) + mean;

        
        double PL_dB = FSPL_dB
                + (10 * simParams.path_loss_exponent_alpha * (getDistanceFromBS(bs) / (simParams.path_loss_reference_distance * 1000)))
                + normal_random_generated_value;

        double Pr = simParams.power_transmitted - PL_dB;    //received power = transmitted power - (FIXED LOSS + VARIABLE LOSS)
        //received power = transmitted power - PL_dB

        double Pr_mW = Helper.convert_To_mW_From_dBM(Pr);
        
//        System.out.print("getDistanceFromBS(bs) = " + getDistanceFromBS(bs) + " , ");
//        System.out.println("Rand value = " + normal_random_generated_value + " , PL_dB = " + PL_dB + " , FSPL_dB = " + FSPL_dB + " , Pr = " + Pr + " , Pr_mW = " + Pr_mW);
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
}

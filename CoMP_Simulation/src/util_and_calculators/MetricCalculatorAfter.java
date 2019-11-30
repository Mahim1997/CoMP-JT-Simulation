package util_and_calculators;

import java.util.ArrayList;
import java.util.List;
import sim_objects.BaseStation;
import sim_objects.User;
import simulation_params.SimulationParameters;

public class MetricCalculatorAfter {

    public static List<User> getNewUsersListAfter_Tavg_calculation(List<User> list_of_all_users,
            List<BaseStation> baseStations, SimulationParameters simParams, double power_noise_mW) {
        List<User> list_new_users = new ArrayList<>();
        double num_UE_active = 0;
        for (int ue_itr = 0; ue_itr < list_of_all_users.size(); ue_itr++) {
            User user = list_of_all_users.get(ue_itr);
            double coordinating_powers_recv = 0, competing_powers_recv = 0;
            
            if (!user.is_UE_dropped) { //UE is taken.
                num_UE_active ++;
                for (int bs_itr = 0; bs_itr < baseStations.size(); bs_itr++) { // FOR EACH B.S.
                    BaseStation bs = baseStations.get(bs_itr);
                    if (user.indices_base_stations_connected.contains(bs.base_station_id)) {
                        //Co-ordinating base stations ...
                        //coordinating_powers_recv += (user.power_received_from_eachBS_or_X_chi.get(bs_itr));//
                        coordinating_powers_recv += (user.power_received_from_eachBS_or_X_chi[bs.base_station_id]);
                    } else {
                        //Competing base-stations
                        double factor = bs.get_current_chi();
//                        competing_powers_recv += (user.power_received_from_eachBS_or_X_chi.get(bs_itr) * factor);
                        competing_powers_recv += (user.power_received_from_eachBS_or_X_chi[bs.base_station_id] * factor);
                    }
                    double SINR_one_UE_one_BS = ((coordinating_powers_recv) / (power_noise_mW + competing_powers_recv));
                    user.SINR_user_one_BS = SINR_one_UE_one_BS;
                    user.THROUGHPUT_user_one_BS_KBps = MetricCalculatorAfter.calculateThroughput_kBps_1BS_1UE(SINR_one_UE_one_BS);
                    
                    
                }
                
            }
            list_new_users.add(user);
        }
//        System.out.println("-->>MetricCalculator.java, NUM ACTIVE  UE = " + num_UE_active + " , NUM_TOTAL UE = " + list_new_users.size());
        return list_new_users;
    }

    private static double calculateThroughput_kBps_1BS_1UE(double SINR) {
        return (180.0 * (Helper.log2(1 + SINR)));
    }
}

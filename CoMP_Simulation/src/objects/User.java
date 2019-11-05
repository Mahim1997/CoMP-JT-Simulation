package objects;

import comp_simulation.Helper;
import java.util.List;
import java.util.ArrayList;
import simulation_params.SimulationParameters;

public class User {
    public double x_pos;
    public double y_pos;
    
    //Hashmap <SINR, BASESTATION> for DPS, and JT methods. //TODO
    
    
    //One param SINR for Conventional Method
    public double SINR_user_one_BS;
    public double PATH_LOSS_this_user_mW;
    public double POWER_RECEIVED_ONE_BS_mW;

    public double get_RECEIVED_POWER_mW_for_one_BS(double FSPL_dB, BaseStation bs,
            SimulationParameters simParams){
        double PL_dB = FSPL_dB + 
                10 * 
                simParams.path_loss_exponent_alpha * 
                (getDistanceFromBS(bs)/(simParams.path_loss_reference_distance * 1000));
                
        double Pr = simParams.power_transmitted - PL_dB;    //received power = transmitted power - (FIXED LOSS + VARIABLE LOSS)
                                                            //received power = transmitted power - PL_dB
                                                            
        return Helper.convert_To_mW_From_dBM(Pr);
    }
    
    public double[] get_RECEIVED_POWER_of_all_BS(double FSPL_dB, List<BaseStation> baseStations,
            SimulationParameters simParams){

        double []Pr_mW_arr = new double[baseStations.size()];
        for(int i=0; i<baseStations.size(); i++){
            Pr_mW_arr[i] = get_RECEIVED_POWER_mW_for_one_BS(FSPL_dB, baseStations.get(i), simParams);
        }
        
        return Pr_mW_arr;
    }
    
    
    
    public User(double x_pos, double y_pos) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }
    
    
    public double getDistanceFromBS(BaseStation bs){
        return Helper.getDistance(x_pos, y_pos, bs.x_pos, bs.y_pos);
    }

    public double[] get_distances_of_all_baseStations(List<BaseStation> baseStations) {
        int num_bs = baseStations.size();
        double []distance_arr = new double[num_bs];
        
        for(int i=0; i<baseStations.size(); i++){
            distance_arr[i] = (this.getDistanceFromBS(baseStations.get(i)));
        }
        
        return distance_arr;
    }
}

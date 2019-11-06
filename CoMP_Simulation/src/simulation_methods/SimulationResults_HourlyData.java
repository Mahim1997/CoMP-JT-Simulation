package simulation_methods;

import java.util.ArrayList;
import java.util.List;
import objects.BaseStation;

public class SimulationResults_HourlyData {
    
    public List<BaseStation> list_of_base_stations = new ArrayList<>();
    
    public double []hour_arr;
//    public double []energy_efficiency_arr;
//    public double []sinr_arr;
    
    //Metrics.
    public double []cumulative_throughput_arr;
    public double [][]power_consumption_arr; //Number of BaseStations * 24
    public double []average_power_consumption_arr;
    public double []average_throughput_arr;
    
    public SimulationResults_HourlyData(int numBaseStations){
        this.hour_arr = new double[24];
        for(int i=0; i<24; i++){
            this.hour_arr[i] = (double)(i + 0.5);
        }
        
        //Metrics [INITIALIZATION of array]
        this.cumulative_throughput_arr = new double[24];
        this.power_consumption_arr = new double[24][];
        for(int i=0; i<this.power_consumption_arr.length; i++){
            this.power_consumption_arr[i] = new double[numBaseStations];
        }
        this.average_throughput_arr = new double[24];
        this.average_power_consumption_arr = new double[24];
        
    }
    
}

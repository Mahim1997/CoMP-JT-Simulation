package simulation_methods;

public class SimulationResults_HourlyData {
    public double []hour_arr;
    public double []throughput_arr;
    public double []energy_efficiency_arr;
    public double []sinr_arr;
    
    public SimulationResults_HourlyData(){
        this.hour_arr = new double[24];
        for(int i=0; i<24; i++){
            this.hour_arr[i] = (double)(i + 0.5);
        }
        this.throughput_arr = new double[24];
        this.energy_efficiency_arr = new double[24];
        this.sinr_arr = new double[24];
        
    }
}

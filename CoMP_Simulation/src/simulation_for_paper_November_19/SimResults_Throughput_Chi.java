package simulation_for_paper_November_19;

import java.util.ArrayList;
import java.util.List;
import simulation_params.SimulationParameters;

public class SimResults_Throughput_Chi {

    public List<Double> chi_list;
    public List<Double> avg_UE_throughput_list;

    public SimResults_Throughput_Chi(SimulationParameters simParams) {
        this.chi_list = new ArrayList<>();
        this.avg_UE_throughput_list = new ArrayList<>();
    }

    public void printLists(){
        for(int i=0; i<chi_list.size(); i++){
            double chi = chi_list.get(i);
            double avg_throughput = avg_UE_throughput_list.get(i);
            System.out.println("Chi = " + chi + " , Avg UE Throughput(kBps) = " + avg_throughput);
        }
    }
    
}

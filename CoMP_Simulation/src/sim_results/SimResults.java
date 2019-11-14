package sim_results;

import java.util.ArrayList;
import java.util.List;
import util_and_calculators.FileWriter_CSV;

public class SimResults {

    public List<Double> chi_list = new ArrayList<>();
    public List<Double> avg_throughput_list = new ArrayList<>();
    public List<Double> spectral_efficiency_list = new ArrayList<>();
    public List<Double> fairness_index_jain_list = new ArrayList<>();
    public List<Double> cell_edge_throughput_list = new ArrayList<>();
    public List<Double> discrimination_index_list = new ArrayList<>();
    public List<Double> entropy_list = new ArrayList<>();
    
    public String[] headings_arr = {"Chi(%)", "Avg UE Throughput(kBps)", "Spectral Efficiency", "Fairness Idx",
    "Cell-Edge Throughput(kBps)", "Discrimination Idx", "Entropy"};
    
    public void write_to_csv_file(String fileName){
        FileWriter_CSV.erase_csv_file(fileName);
        FileWriter_CSV.write_to_csv_file(fileName, this);
        System.out.println("-->>Writing to file done.");
    }

  
}

package sim_results;

import java.util.ArrayList;
import java.util.List;
import util_and_calculators.FileWriter_CSV;

public class SimResults {
// ------------------------------------- TASK 1 --------------------------------
    public List<Double> chi_list = new ArrayList<>();
    public List<Double> avg_throughput_list = new ArrayList<>();
    public List<Double> spectral_efficiency_list = new ArrayList<>();
    public List<Double> fairness_index_jain_list = new ArrayList<>();
    public List<Double> cell_edge_throughput_list = new ArrayList<>();
    public List<Double> discrimination_index_list = new ArrayList<>();
    public List<Double> entropy_list = new ArrayList<>();
    public List<Double> power_consumed_avg_BS_list = new ArrayList<>();
    public List<Double> proportion_UE_dropped_list = new ArrayList<>();

//New metrics , 30 Nov 2019.
    public List<Double> proportion_UE_active_list = new ArrayList<>();
    public List<Double> effective_chi_avg_list_mean_BSs = new ArrayList<>();
    public List<Double> effective_chi_prop_active_UEs = new ArrayList<>();
    public List<Double> avg_throughput_active_UE_list = new ArrayList<>();
    
    
    public String[] headings_arr = {"Chi(proportion)", "T_avg UE(kBps)", "Spectral Efficiency", "Fairness Idx",
        "Cell-Edge Throughput(kBps)", "Discrimination Idx", "Entropy", "Proportion UE dropped",
        "Proportion of UE active", "Eff chi mean BSs", "Eff chi active UEs", "T_Avg Active UE(kBps)"};


    public void write_to_csv_file(String fileName) {
        FileWriter_CSV.erase_csv_file(fileName);
        FileWriter_CSV.write_to_csv_file(fileName, this);
        System.out.println("-->>Writing to file done.");
    }

    public void enterMetricsForOneMC(double chi, SimResult_oneMC res) {
        //Add to lists.
        this.chi_list.add(chi);
        this.avg_throughput_list.add(res.avg_throughput);
        this.spectral_efficiency_list.add(res.spectral_efficiency);
        this.fairness_index_jain_list.add(res.fairness_index);
        this.cell_edge_throughput_list.add(res.cell_edge_throughput);
        this.discrimination_index_list.add(res.discrimination_index);
        this.entropy_list.add(res.entropy);
        this.power_consumed_avg_BS_list.add(res.power_consumed_avg_BS);
        this.proportion_UE_dropped_list.add(res.proportion_UE_dropped);
        this.proportion_UE_active_list.add(res.proportion_of_active_UE);
        this.effective_chi_avg_list_mean_BSs.add(res.effective_chi_mean_of_all_BS);
        this.effective_chi_prop_active_UEs.add(res.effective_chi_prop_active_UE);
        this.avg_throughput_active_UE_list.add(res.avg_throughput_of_active_UEs);

    }

}

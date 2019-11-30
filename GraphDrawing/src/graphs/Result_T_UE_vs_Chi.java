package graphs;

import java.util.ArrayList;
import java.util.List;

public class Result_T_UE_vs_Chi {
    public List<Double> chi_list;
    public List<Double> avg_UE_throughput_list;
    public List<Double> spectral_efficiency_list = new ArrayList<>();
    public List<Double> fairness_index_jain_list = new ArrayList<>();
    public List<Double> cell_edge_throughput_list = new ArrayList<>();
    public List<Double> discrimination_index_list = new ArrayList<>();
    public List<Double> entropy_list = new ArrayList<>();
    public List<Double> proportion_UE_dropped_list = new ArrayList<>();
    
//Nov 30, 2019
    public List<Double> proportion_UE_active_list = new ArrayList<>();
    public List<Double> effective_chi_meanBSs_list = new ArrayList<>();
    public List<Double> effective_chi_propActiveUEs_list = new ArrayList<>();
    public List<Double> avg_ACTIVE_UE_throughput_list = new ArrayList<>();
    
    public String legendName;
    public int JT_num;

    public Result_T_UE_vs_Chi(String name_of_legend, int jt_value){
        this.chi_list = new ArrayList<>();
        this.avg_UE_throughput_list = new ArrayList<>();
        this.legendName = name_of_legend;
        this.JT_num = jt_value;
    }

}

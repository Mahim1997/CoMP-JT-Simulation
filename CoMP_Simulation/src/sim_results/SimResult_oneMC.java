package sim_results;

import java.util.List;
import sim_objects.BaseStation;
import sim_objects.User;
import simulation_params.SimulationParameters;
import util_and_calculators.CalculatorECDF;
import util_and_calculators.Helper;

public class SimResult_oneMC {

    //Stores simulation results of one monte carlo run
//    public double chi = 0;
    public double avg_throughput = 0;
    public double spectral_efficiency = 0;
    public double fairness_index = 0;
    public double cell_edge_throughput = 0;
    public double discrimination_index = 0;
    public double entropy = 0;
    public double power_consumed_avg_BS = 0;
    public double proportion_UE_dropped = 0;

//New Metrics
    public double proportion_of_active_UE = 0;
    public double effective_chi_prop_active_UE = 0;
    public double effective_chi_mean_of_all_BS = 0;
    public double avg_throughput_of_active_UEs = 0;

    public void calculate_metrics(List<User> list_users, SimulationParameters simParams, double chi) {

        int total_users_num = list_users.size();
        if (total_users_num == 0) {
            return; //all will be zero
        }
        double num_users_double = (double) total_users_num;

        double sum_throughput = 0;
        double sum_squares_of_throughput = 0;

        double user_fairness = 0;
        double user_proportion_entropy = 0;
        double T_f = 0;

        for (int i = 0; i < total_users_num; i++) {
            User u = list_users.get(i);
            sum_throughput += (u.THROUGHPUT_user_one_BS_KBps);
            sum_squares_of_throughput += (u.THROUGHPUT_user_one_BS_KBps * u.THROUGHPUT_user_one_BS_KBps);
        }
        //Calculate metrics which are possible.
        this.avg_throughput = sum_throughput / num_users_double;
        this.spectral_efficiency = sum_throughput / (simParams.bandwidth * 1000); //BW was in Hz, to convert to kHz, to keep consistent with kBps
        this.fairness_index = (sum_throughput * sum_throughput) / (sum_squares_of_throughput * num_users_double);

        double[] throughput_data = new double[list_users.size()];
        for (int i = 0; i < throughput_data.length; i++) {
            throughput_data[i] = list_users.get(i).THROUGHPUT_user_one_BS_KBps;
        }
        double[] x_axis_data = new double[throughput_data.length];
        double[] y_axis_data = new double[throughput_data.length];
        CalculatorECDF.ECDF(throughput_data, x_axis_data, y_axis_data);
        this.cell_edge_throughput = CalculatorECDF.get5thPercentile(x_axis_data, y_axis_data);

        //Once again for discrimination index and other params
        T_f = (sum_squares_of_throughput) / (sum_throughput);
        double per_user_value, cumulative_discrimination_index = 0;
        double p_i, cumulative_entropy = 0;

        double num_UE_dropped_double = 0, num_UE_active_double = 0, chi_effective_cumulative = 0;
        double cumulative_active_UE_throughput = 0, num_active_UE_double = 0;
        
        for (int i = 0; i < total_users_num; i++) {
            User user = list_users.get(i);
            per_user_value = (T_f - user.THROUGHPUT_user_one_BS_KBps) / (T_f); //for discrimination index
            cumulative_discrimination_index += per_user_value;
            p_i = (user.THROUGHPUT_user_one_BS_KBps) / sum_throughput; //for entropy
            cumulative_entropy += ((p_i == 0) ? 0 : (p_i * Helper.log2(p_i)));
            num_UE_dropped_double += ((user.is_UE_dropped) ? 1.0 : 0.0);
            num_UE_active_double += ((user.is_UE_dropped) ? 0.0 : 1.0);
            cumulative_active_UE_throughput += ((user.is_UE_dropped) ? 0 : (user.THROUGHPUT_user_one_BS_KBps));
            num_active_UE_double += (user.is_UE_dropped ? 0 : 1); //If active then 1.
        }

        List<BaseStation> baseStations = list_users.get(list_users.size() - 1).getListOfBaseStations();
        double cumulative_chi_BSs = 0;
        double num_baseStations_double = (double) baseStations.size();
        for (int k = 0; k < baseStations.size(); k++) {
            BaseStation bs = baseStations.get(k);
            cumulative_chi_BSs += (bs.get_current_chi());
        }
//        System.out.println("-->>SimResult_oneMC => NUM_ACTIVE_UE = " + num_UE_active_double + " , total num users = " + num_users_double);
        this.discrimination_index = cumulative_discrimination_index / num_users_double;
        this.entropy = -1.0 * cumulative_entropy;
        this.proportion_UE_dropped = num_UE_dropped_double / num_users_double;
        this.proportion_of_active_UE = num_UE_active_double / num_users_double;
        this.effective_chi_mean_of_all_BS = (cumulative_chi_BSs / num_baseStations_double) * chi;
        this.effective_chi_prop_active_UE = this.proportion_of_active_UE * chi;
        this.avg_throughput_of_active_UEs = cumulative_active_UE_throughput / num_active_UE_double;
    }

    public void addMetrics(SimResult_oneMC res) {
        
        this.avg_throughput += res.avg_throughput;
        this.cell_edge_throughput += res.cell_edge_throughput;
        this.discrimination_index += res.discrimination_index;
        this.entropy += res.entropy;
        this.fairness_index += res.fairness_index;
        this.spectral_efficiency += res.spectral_efficiency;
        this.power_consumed_avg_BS += res.power_consumed_avg_BS;
        this.proportion_UE_dropped += res.proportion_UE_dropped;
//New Metrics [Nov 30 2019]
        this.proportion_of_active_UE += res.proportion_of_active_UE;
        this.effective_chi_mean_of_all_BS += res.effective_chi_mean_of_all_BS;
        this.avg_throughput_of_active_UEs += res.avg_throughput_of_active_UEs;
        this.effective_chi_prop_active_UE += res.effective_chi_prop_active_UE;
    }

    public void divideMetricsBy(double div) { //for monte carlo divisions.
        this.avg_throughput /= div;
        this.cell_edge_throughput /= div;
        this.discrimination_index /= div;
        this.entropy /= div;
        this.fairness_index /= div;
        this.spectral_efficiency /= div;
        this.power_consumed_avg_BS /= div;
        this.proportion_UE_dropped /= div;
//New metrics [30 Nov 2019]
        this.proportion_of_active_UE /= div;
        this.effective_chi_mean_of_all_BS /= div;
        this.avg_throughput_of_active_UEs /= div;
        this.effective_chi_prop_active_UE /= div;
    }
}

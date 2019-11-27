package comp_simulation;

import sim_for_paper_distance_prevThings.Sim_Metrics_avg_vs_Distance_OLD;
import sim_for_paper_distance_prevThings.Sim_UE_T_vs_Dist_NOT_AVG_OLD;
import simulation_for_paper_chi_based.Sim_UE_Metrics_avg_vs_chi;
import simulation_for_paper_distance_based.Sim_UE_avg_Throughput_vs_distance;
import simulation_params.SimulationParameterBuilder;
import simulation_params.SimulationParameters;

public class SimulationRunner {

    public static void runSimulation(String mode) {
        SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
        simParams.simulationType = mode;
        simParams.printParameters();
        System.out.println("==-->> About to run simulation for " + (mode.replace("_", " ")));
        if (mode.equals(Main.chi_based)) { //Task 1
            Sim_UE_Metrics_avg_vs_chi runner = new Sim_UE_Metrics_avg_vs_chi(simParams);
            runner.runSimulationTask1();
        } else if (mode.equals(Main.distance_based_prev_avg)) { // Task 2.a OLD
            Sim_Metrics_avg_vs_Distance_OLD runner = new Sim_Metrics_avg_vs_Distance_OLD(simParams);
            runner.runSimulationForSecondTask_OLD();
        } else if (mode.equals(Main.distance_based_prev_all_UEs)) { // Task 2.b OLD
            Sim_UE_T_vs_Dist_NOT_AVG_OLD runner = new Sim_UE_T_vs_Dist_NOT_AVG_OLD(simParams);
            runner.runSimulationForSecondTask_OLD();
        } //NEW Task 2
        else if (mode.equals(Main.distance_based_avg_NEW)) {
            Sim_UE_avg_Throughput_vs_distance runner = new Sim_UE_avg_Throughput_vs_distance(simParams);
            runner.runSimulationForSecondTask_NEW();
        }
        // to do 
    }

}

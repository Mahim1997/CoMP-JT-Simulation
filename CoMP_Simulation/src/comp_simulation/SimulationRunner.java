package comp_simulation;

import sim_for_paper_distance_prevThings.Sim_Metrics_avg_vs_Distance;
import sim_for_paper_distance_prevThings.Sim_UE_T_vs_Dist_NOT_AVG;
import simulation_for_paper_chi_based.Sim_UE_Metrics_avg_vs_chi;
import simulation_params.SimulationParameterBuilder;
import simulation_params.SimulationParameters;

public class SimulationRunner {
    public static void runSimulation(String mode) {

        if (mode.equals(Main.chi_based)) { //Task 1
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            Sim_UE_Metrics_avg_vs_chi runner = new Sim_UE_Metrics_avg_vs_chi(simParams);
            System.out.println("Running simulation for " + (mode.replace("_", " ")));
            simParams.simulationType = mode;
            simParams.printParameters();
            runner.runSimulationTask1();
        } else if (mode.equals(Main.distance_based_prev_avg)) { // Task 2.a
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            Sim_Metrics_avg_vs_Distance runner = new Sim_Metrics_avg_vs_Distance(simParams);
            System.out.println("Running simulation for " + (mode.replace("_", " ")));
            simParams.simulationType = mode;
            simParams.printParameters();
            runner.runSimulationForSecondTask();
        } else if (mode.equals(Main.distance_based_prev_all_UEs)) { // Task 2.b
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            Sim_UE_T_vs_Dist_NOT_AVG runner = new Sim_UE_T_vs_Dist_NOT_AVG(simParams);
            System.out.println("Running simulation for " + (mode.replace("_", " ")));
            simParams.simulationType = mode;
            simParams.printParameters();
            runner.runSimulationForSecondTask();
        }
        // to do 
    }


}

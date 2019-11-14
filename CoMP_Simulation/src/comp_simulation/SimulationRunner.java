package comp_simulation;

import simulation_for_paper_November_19.Sim_T_avg_vs_Distance;
import simulation_for_paper_November_19.Sim_UE_T_vs_Dist_NOT_AVG;
import simulation_for_paper_November_19.Sim_UE_T_avg_vs_chi;
import simulation_params.SimulationParameterBuilder;
import simulation_params.SimulationParameters;

public class SimulationRunner {

    public static void runSimulation(String mode) {

        if (mode.equals(Main.throughput_vs_chi)) { //Task 1
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            Sim_UE_T_avg_vs_chi runner = new Sim_UE_T_avg_vs_chi(simParams);
            System.out.println("Running simulation for " + (mode.replace("_", " ")));
            simParams.simulationType = mode;
            simParams.printParameters();
            runner.runSimulationTask1();
        } else if (mode.equals(Main.UE_T_avg_vs_distance)) { // Task 2.a
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            Sim_T_avg_vs_Distance runner = new Sim_T_avg_vs_Distance(simParams);
            System.out.println("Running simulation for " + (mode.replace("_", " ")));
            simParams.simulationType = mode;
            simParams.printParameters();
            runner.runSimulationForSecondTask();
        } else if (mode.equals(Main.UE_T_vs_distance)) { // Task 2.b
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

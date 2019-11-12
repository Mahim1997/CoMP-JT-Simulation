package comp_simulation;

import simulation_for_paper_November_19.Sim_T_avg_vs_Distance;
import simulation_for_paper_November_19.Simulation_New;
import simulation_params.SimulationParameterBuilder;
import simulation_params.SimulationParameters;

public class SimulationRunner {

    public static void runSimulation(String mode) {

        if (mode.equals(Main.throughput_vs_chi)) { //Task 1
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            Simulation_New runner = new Simulation_New(simParams);
            System.out.println("Running simulation for " + (mode.replace("_", " ")));
            simParams.simulationType = mode;
            simParams.printParameters();
            runner.runSimulationTask1();
        } else if (mode.equals(Main.UE_T_vs_distance)) { // Task 2
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            Sim_T_avg_vs_Distance runner = new Sim_T_avg_vs_Distance(simParams);
            System.out.println("Running simulation for " + (mode.replace("_", " ")));
            simParams.simulationType = mode;
            simParams.printParameters();
            runner.runSimulationForSecondTask();
        }
        // to do 
    }
}

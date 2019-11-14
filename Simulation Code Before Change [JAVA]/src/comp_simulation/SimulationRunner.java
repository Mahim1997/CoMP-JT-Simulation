package comp_simulation;

import simulation_for_paper_November_19.Sim_UE_New;
import simulation_methods.ConventionalMethod;
import simulation_params.SimulationParameterBuilder;
import simulation_params.SimulationParameters;

public class SimulationRunner {

    public static void runSimulation(String mode) {
        if(mode.equals(Main.conventional_mode)){
            System.out.println("Running simulation for " + mode);
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            simParams.simulationType = mode;
            simParams.printParameters();
            ConventionalMethod.runConventionalSimulation(simParams);
        }else if(mode.equals(Main.throughput_vs_chi)){
            System.out.println("Running simulation for " + (mode.replace("_", " ")));
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            simParams.simulationType = mode;
            simParams.printParameters();
            Sim_UE_New runner = new Sim_UE_New(simParams);
            runner.runSimulationChi();
        }else if(mode.equals(Main.UE_T_vs_distance)){
            System.out.println("Running simulation for " + (mode.replace("_", " ")));
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            simParams.simulationType = mode;
            simParams.printParameters();
            Sim_UE_New runner = new Sim_UE_New(simParams);
            runner.runSimulationForSecondTask();
        }
        // to do 
    }
}

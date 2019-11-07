package comp_simulation;

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
        }
        // to do 
    }
}

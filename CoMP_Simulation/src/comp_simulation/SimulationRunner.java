package comp_simulation;

import simulation_methods.ConventionalMethod;
import simulation_params.SimulationParameterBuilder;
import simulation_params.SimulationParameters;

public class SimulationRunner {

    public static void runSimulation(String method) {
        if(method.equals(Main.throughput_conventional)){
            System.out.println("Running simulation for " + method);
            SimulationParameters simParams = SimulationParameterBuilder.buildSimulationParameters_Urban();
            simParams.simulationType = method;
            simParams.printParameters();
            ConventionalMethod.runConventionalSimulation(simParams);
        }
        // to do 
    }
}

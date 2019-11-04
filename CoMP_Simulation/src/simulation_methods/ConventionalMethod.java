package simulation_methods;

import base_station.BaseStation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import simulation_params.SimulationParameters;

public class ConventionalMethod {

    public static void runConventionalSimulation(SimulationParameters simParams) {
        //1. Place the Base Stations.
        List<BaseStation> baseStaions = new ArrayList<>();
        int tier = 3; //testing.
        BaseStation.placeBaseStations(baseStaions, 10, tier); // for now , all are at initial positions.
        for(BaseStation bs: baseStaions){
            System.out.println(bs.toString());
        }
    }

    
}

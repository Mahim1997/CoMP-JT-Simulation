package comp_simulation;

public class Main {

    public static String throughput_vs_chi = "Throughput_vs_Chi_for_JT"; //Throughput AVG vs CHI [chi is varied] (Task 1)
    public static String UE_T_vs_distance = "UE_T_vs_Distance_for_JT"; //UE Throughput vs UE closest BS distance [chi = 0.5] (Task 2)
//    public static String fileName_for_T_vs_distance = "Throughput_vs_Distance";
    
    public static void main(String[] args) {
//        SimulationRunner.runSimulation(conventional_mode);
//        SimulationRunner.runSimulation(throughput_vs_chi); //For Task 1
        SimulationRunner.runSimulation(UE_T_vs_distance); //For Task 2
    }
}

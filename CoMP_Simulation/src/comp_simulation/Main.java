package comp_simulation;

public class Main {

    public static String PREV_MODE_JT; //OTHER FILES MAY USE THIS VARIABLE
    public static String throughput_vs_chi = "Throughput_vs_Chi_for_JT"; //Throughput AVG vs CHI [chi is varied] (Task 1)
    public static String UE_T_avg_vs_distance = "UE_T_avg_vs_Distance_for_JT"; //UE Throughput vs UE closest BS distance [chi = 0.5] (Task 2.a)
    public static String UE_T_vs_distance = "UE_T_vs_Distance_for_JT"; //UE Throughput vs UE closest BS distance [chi = 0.5] (Task 2.b)
    public static String JT_SINR = "JT_SINR";
    public static String JT_DISTANCE = "JT_DISTANCE";
    public static String JT_HYBRID = "JT_HYBRID";
    public static String JT_CONVENTIONAL = "JT_CONVENTIONAL";
    public static String JT_MODE = JT_CONVENTIONAL;

//    public static String fileName_for_T_vs_distance = "Throughput_vs_Distance";
    public static void main(String[] args) {
//        SimulationRunner.runSimulation(conventional_mode);
        SimulationRunner.runSimulation(throughput_vs_chi); //For Task 1
//        SimulationRunner.runSimulation(UE_T_avg_vs_distance); //For Task 2.a
//        SimulationRunner.runSimulation(UE_T_vs_distance); //For Task 2.b
    }
}

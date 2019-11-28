package comp_simulation;

public class Main {

    public static void main(String[] args) {
        System.out.println("Running for JT MODE = " + JT_MODE);
        
        SimulationRunner.runSimulation(distance_based_avg_NEW); //For Task 2 NEW
        SimulationRunner.runSimulation(chi_based); //For Task 1
        
//        SimulationRunner.runSimulation(distance_based_prev_avg); //For Task 2.a
//        SimulationRunner.runSimulation(distance_based_prev_all_UEs); //For Task 2.b
        
    }

    public static String PREV_MODE_JT; //OTHER FILES MAY USE THIS VARIABLE

    public static String distance_based_prev_avg = "UE_T_avg_vs_Distance_prev_for_JT"; //OLD //UE Throughput vs UE closest BS distance [chi = 0.5] (Task 2.a)
    public static String distance_based_prev_all_UEs = "UE_T_vs_Distance_prev_for_JT"; //OLD //UE Throughput vs UE closest BS distance [chi = 0.5] (Task 2.b)    

    public static String chi_based = "Metrics_vs_Chi_for_JT"; //Metrics AVG vs CHI [chi is varied] (Task 1)    
    public static String distance_based_avg_NEW = "UE_T_avg_vs_Distance_for_JT"; //For Task 2 [NEW]

//---------------------------- MODES FOR JT -------------------------------------
    public static String JT_SINR = "JT_SINR";
    public static String JT_DISTANCE = "JT_DISTANCE";
    public static String JT_HYBRID = "JT_HYBRID";
    public static String JT_CONVENTIONAL = "JT_CONVENTIONAL";

// ---->>> MODE FOR JT
    public static String JT_MODE = JT_SINR;

}

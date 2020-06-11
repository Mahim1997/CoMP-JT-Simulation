package comp_simulation;

public class Main {

    public static boolean GET_OUTER_RING_BASE_STATIONS = true;
    public static boolean NEW_SIMULATION_STRATEGY = true;
    public static boolean TAKE_AFTER_CALCULATION = true;
//    public static double CHI_STEP_SIZE_TASK_1 = 0.001;
    public static int JT_INITIAL;
    public static int JT_FINAL;
    public static int MONTE_CARLO = 1000;
    public static boolean DYNAMIC_JT_CHANGE_WRT_CHI_FLAG = false;
    public static int JT_DYNAMIC_CHANGE_START = 3;

    public static double chi_critical_JT_3_JT_2 = 0.30;
    public static double chi_critical_JT_2_JT_1 = 0.48;
    public static int DYNAMIC_JT_VALUE_FOR_FILE = 6;

    public static int TIER_START = 1;
    public static int TIER_FINAL = 14; //BESHI HOYE GELO ????
    public static boolean DUMMY_RING_TAKE = false;
    public static boolean CENTER_BS_ONLY = true;
    
    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>> Running in Main.main()");

//        Main.JT_INITIAL = 2;
//        Main.JT_FINAL = 15;
        Main.TAKE_AFTER_CALCULATION = true;
        System.out.println("-------------------Running Simulation ----------------------");
//        SimulationRunner.runSimulation(distance_based_avg_NEW); //For Task 2 NEW                       
//        SimulationRunner.runSimulation(chi_based); //For Task 1 NEW 
//        SimulationRunner.runSimulation(TRAFFIC_MODE_task_1); //For Traffic mode ... Task 1 extension
//        Main.JT_INITIAL = 1;
//        Main.JT_FINAL = 1;



        //Center BS Only
        Main.CENTER_BS_ONLY = true;
        Main.TIER_START = 1;
        Main.TIER_FINAL = 11; // do only upto 11 for now ... later do for 12 as well [JT = 0 done] June 10, 2020
        //JT = 2
        Main.JT_INITIAL = 1;
        Main.JT_FINAL = 5;
        SimulationRunner.runSimulation(TIER_VARIATION_FOR_DUMMY_RING);
    }

    public static String PREV_MODE_JT; //OTHER FILES MAY USE THIS VARIABLE

    public static String distance_based_prev_avg = "UE_T_avg_vs_Distance_prev_for_JT"; //OLD //UE Throughput vs UE closest BS distance [chi = 0.5] (Task 2.a)
    public static String distance_based_prev_all_UEs = "UE_T_vs_Distance_prev_for_JT"; //OLD //UE Throughput vs UE closest BS distance [chi = 0.5] (Task 2.b)    

    public static String chi_based = "Metrics_vs_Chi_for_JT"; //Metrics AVG vs CHI [chi is varied] (Task 1)    
    public static String distance_based_avg_NEW = "UE_T_avg_vs_Distance_for_JT"; //For Task 2 [NEW]
    public static String TRAFFIC_MODE_task_1 = "TRAFFIC_MODE_task_1";
    public static String TIER_VARIATION_FOR_DUMMY_RING = "TIER_VARIATION_FOR_DUMMY_RING";
//---------------------------- MODES FOR JT -------------------------------------
    public static String JT_SINR = "JT_SINR";
    public static String JT_DISTANCE = "JT_DISTANCE";
    public static String JT_HYBRID = "JT_HYBRID";
    public static String JT_CONVENTIONAL = "JT_CONVENTIONAL";

// ---->>> MODE FOR JT
    public static String JT_MODE = JT_SINR;

}

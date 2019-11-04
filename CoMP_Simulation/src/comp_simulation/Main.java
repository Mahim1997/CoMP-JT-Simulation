package comp_simulation;

public class Main {

    public static String throughput_conventional = "Throughput vs Time in Conventional Setting";

    /*
    Existing modes:
    1. throughput_conventional : To plot a graph for Throughput vs Time in Conventional Setting.
     */
    public static void main(String[] args) {
//        test1();
        SimulationRunner.runSimulation(throughput_conventional);
    }

    static void test1() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Math.random());
        }
    }

}

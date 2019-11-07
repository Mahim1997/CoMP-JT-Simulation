package comp_simulation;

public class Main {

    public static String conventional_mode = "Conventional Mode";

    /*
    Existing modes:
    1. conventional_mode : least of distances from user to B.S.
     */
    public static void main(String[] args) {
//        test1();
        SimulationRunner.runSimulation(conventional_mode);
    }

    static void test1() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Math.random());
        }
    }

}

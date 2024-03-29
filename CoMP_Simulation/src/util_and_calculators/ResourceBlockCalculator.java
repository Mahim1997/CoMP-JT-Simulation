/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util_and_calculators;

/**
 *
 * @author viper
 */
public class ResourceBlockCalculator {

    private static double[] bandwidth_MHz_arr
            = {1.4, 3, 5, 10, 15, 20};
    private static int[] resource_blocks_num
            = {6, 15, 25, 50, 75, 100};

    public static double BANDWIDTH_OF_RESOURCE_BLOCK_IN_kHz = 180; //For now, using 180 kHz as bandwidth of each resource block.

    public static int numberOfResourceBlocks(double bandwidth_MHz) {

        for (int i = 0; i < bandwidth_MHz_arr.length; i++) {
            if (bandwidth_MHz == bandwidth_MHz_arr[i]) {

                return resource_blocks_num[i];
            }
        }

        //DEFAULT 10.
        return 10;
    }
}


package graphs;

public class RunnerTask2 {
    public static Things_One_JT[] read_all_jt_csv_files(){
        Things_One_JT[] jt_arr = new Things_One_JT[4]; //jt = 0, jt = 1, jt = 2, jt = 3
        for(int i=0; i<jt_arr.length; i++){
            Things_One_JT jt_now = jt_arr[i];
            jt_now = new Things_One_JT();
            jt_now.fileNameToRead = "UE_T_avg_vs_distance_BS_MC_1000_JT_" + String.valueOf(i) + "_Take_after_calcs.csv";
            jt_now.readFromFileAndStoreInfo();
        }
        jt_arr[0].printThings();
        
        

        
        
        return jt_arr;
    }

    public static void plotForTask2() {
        System.out.println("-->>Running for task 2");
    }
}

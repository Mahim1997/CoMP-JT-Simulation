package util_and_calculators;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import sim_results.SimResults;

public class FileWriter_CSV {

    public static void erase_csv_file(String fileName) {
        try (FileWriter fw = new FileWriter(fileName);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.print("");
        } catch (IOException e) {
        }

    }

    public static void write_to_csv_file(String fileName, SimResults res) {
        try (FileWriter fw = new FileWriter(fileName, true); //APPEND -> true
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            //FIRST THE HEADINGS..
            for (int i = 0; i < res.headings_arr.length; i++) {
                out.print(res.headings_arr[i]);
                if (i < (res.headings_arr.length - 1)) {
                    out.print(",");
                }
            }
            out.println(); //newline
            for (int i = 0; i < res.chi_list.size(); i++) {
                //ALL THE DATA....
                out.print(res.chi_list.get(i) + "," + res.avg_throughput_list.get(i) + ","
                        + res.spectral_efficiency_list.get(i) + "," + res.fairness_index_jain_list.get(i) + ","
                        + res.cell_edge_throughput_list.get(i) + "," + res.discrimination_index_list.get(i) + ","
                        + res.entropy_list.get(i) + ","
                        + res.proportion_UE_dropped_list.get(i) + ","
                        + res.proportion_UE_active_list.get(i) + ","
                        + res.effective_chi_avg_list_mean_BSs.get(i) + ","
                        + res.effective_chi_prop_active_UEs.get(i) + ","
                        + res.avg_throughput_active_UE_list.get(i)
                );

                out.println(); //newline
            }

        } catch (IOException e) {
        }
    }
}

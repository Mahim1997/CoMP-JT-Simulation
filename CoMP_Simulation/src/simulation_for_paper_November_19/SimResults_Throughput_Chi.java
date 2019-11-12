package simulation_for_paper_November_19;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import simulation_params.SimulationParameters;

public class SimResults_Throughput_Chi {

    public static String[] array_headings = {"Chi(%)", "Avg Throughput per user(kBps)"};
    public List<Double> chi_list;
    public List<Double> avg_UE_throughput_list;
    public static String[] array_headings_task2 = {"Distance(km)", "Avg Throughput per user(kBps)"};

    public List<Double> distance_list;

    public SimResults_Throughput_Chi(SimulationParameters simParams) {
        this.chi_list = new ArrayList<>();
        this.avg_UE_throughput_list = new ArrayList<>();
        this.distance_list = new ArrayList<>();
    }

    public void printLists() {
        for (int i = 0; i < chi_list.size(); i++) {
            double chi = chi_list.get(i);
            double avg_throughput = avg_UE_throughput_list.get(i);
            double distance = distance_list.get(i);
            System.out.println("Chi = " + chi + " , Avg UE Throughput(kBps) = " + avg_throughput + ", Distance = " + distance + " km");
        }
    }

    public void writeToCSV_Distance_vs_T_avg(String fileName) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            for (int i = 0; i < array_headings_task2.length; i++) {
                writer.write(array_headings_task2[i]);
                //put comma IF NOT last element
                if (i != (array_headings_task2.length - 1)) {
                    writer.write(",");
                }
            }
            writer.write("\n");
            for (int i = 0; i < this.avg_UE_throughput_list.size(); i++) {
                double avg_throughput = this.avg_UE_throughput_list.get(i);
                double distance = this.distance_list.get(i);

                writer.write(String.valueOf(distance) + ",");
                writer.write(String.valueOf(avg_throughput));
                writer.write("\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writeToCSV(String fileName) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            for (int i = 0; i < array_headings.length; i++) {
                writer.write(array_headings[i]);
                //put comma IF NOT last element
                if (i != (array_headings.length - 1)) {
                    writer.write(",");
                }
            }
            writer.write("\n");
            for (int i = 0; i < this.avg_UE_throughput_list.size(); i++) {
                double avg_throughput = this.avg_UE_throughput_list.get(i);
                double chi = this.chi_list.get(i);

                writer.write(String.valueOf(chi) + ",");
                writer.write(String.valueOf(avg_throughput));
                writer.write("\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

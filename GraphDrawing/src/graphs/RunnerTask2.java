package graphs;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class RunnerTask2 {

    public static String folderName = "UE_T_avg_vs_distance_BS";
    public static String[] fileNames_arr = {"chi_0_1.png", "chi_0_2.png", "chi_0_3.png", "chi_0_4.png",
        "chi_0_5.png", "chi_0_6.png", "chi_0_7.png", "chi_0_8.png", "chi_0_9.png", "chi_1_0.png"};
    public static String[] titles_arr = {"Chi = 0.1", "Chi = 0.2", "Chi = 0.3", "Chi = 0.4", "Chi = 0.5", "Chi = 0.6", "Chi = 0.7",
        "Chi = 0.8", "Chi = 0.9", "Chi = 1.0"};
    private static String OUTPUT_FOLDER_TASK_2 = "UE_T_avg_vs_distance_BS/Graphs_Task2";

    private static Things_One_JT[] read_all_jt_csv_files() {
        Things_One_JT[] jt_arr = new Things_One_JT[4]; //jt = 0, jt = 1, jt = 2, jt = 3
        for (int i = 0; i < jt_arr.length; i++) {
            jt_arr[i] = new Things_One_JT();
        }
        for (int i = 0; i < jt_arr.length; i++) {
            jt_arr[i].jt_value = i;
            jt_arr[i].fileNameToRead = folderName + "/UE_T_avg_vs_distance_BS_MC_1000_JT_" + String.valueOf(i) + "_Take_after_calcs.csv";
            jt_arr[i].legendName = "JT = " + String.valueOf(i);
            jt_arr[i].readFromFileAndStoreInfo();

        }
//        jt_arr[1].printThings();
        return jt_arr;
    }

    public static void plotDataOnce(Things_One_JT[] jt_things, String fileNameToSave, int chi_idx, String title) {

        System.out.println("Running Task2Runner.plotDataOnce()  fileToSaveName = " + fileNameToSave);

        GraphPlotter.stage.setTitle(title);
        //defining the axes

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Distance(m)");
        yAxis.setLabel("AVG UE Throughput(kBps)");
        //creating the chart

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);
        lineChart.setTitle(title);

        //List of Serieses, initialization
        List<XYChart.Series> series_list = new ArrayList<>();

        for (int i = 0; i < jt_things.length; i++) {
            series_list.add(new XYChart.Series());
        }
        //Set legends
        for (int i = 0; i < series_list.size(); i++) {
            series_list.get(i).setName(jt_things[i].legendName);
        }

        //populating the series with data

        for (int jt_iter = 0; jt_iter < series_list.size(); jt_iter++) {
            XYChart.Series series = series_list.get(jt_iter);
            for (int i = 0; i < jt_things[jt_iter].distance_list.size(); i++) {
                //add(distance, T_avg)
                double distance = jt_things[jt_iter].distance_list.get(i);
                double T_avg = jt_things[jt_iter].T_avg_per_chi_list.get(chi_idx).get(i);
                series.getData().add(new XYChart.Data(distance, T_avg));
            }
        }

        Scene scene = new Scene(lineChart, 1000, 800); //Height and Width [Default values]

        lineChart.setAnimated(false);
        for (int i = 0; i < series_list.size(); i++) {
            lineChart.getData().add(series_list.get(i)); //append
        }

        GraphPlotter.stage.setScene(scene);
//        scene.getStylesheets().add("file.css");

        GraphPlotter.saveAsPng(scene, fileNameToSave);
    }

    public static void plotForTask2() {
        System.out.println("-->>Running for task 2 in RunnerTask2.plotForTask2()");
        Things_One_JT[] jt_arr = RunnerTask2.read_all_jt_csv_files(); //read data.
        //System.out.println("-->> Len headings = " + RunnerTask2.fileNames_arr.length + " , jt_arr.0.chi.size = " + jt_arr[0].chi_list.size());
        for (int chi_idx = 0; chi_idx < fileNames_arr.length; chi_idx++) {
            String fileNameToSave = fileNames_arr[chi_idx];
            fileNameToSave = (OUTPUT_FOLDER_TASK_2 + "/" + fileNameToSave);
            plotDataOnce(jt_arr, fileNameToSave, chi_idx, RunnerTask2.titles_arr[chi_idx]);
        }
    }
}

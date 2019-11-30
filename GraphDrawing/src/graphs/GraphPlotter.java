package graphs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class GraphPlotter extends Application {

    private int JT_FINAL = 5;

    public static String FILE_NAME = "Conventional.csv";
    public static boolean TAKE_AFTER_CALCS = true;
    public static String OUTPUT_FOLDER_NAME_TASK1 = "Avg_Metrics_vs_Chi/GRAPHS_UE_vs_CHI";

    public static double THRESHOLD_FOR_NOT_TAKING = 0.02;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Read data..

        stage = primaryStage;
        System.out.println("Reading data... from csv file ...");
//        Reader reader = new Reader();
//        Results rs = reader.readThingsFromFile();
        System.out.println("Plotting graph ...");
//        plotForNormalConventional(rs);
        plot_UE_things_vs_chi();
//        plot_avg_T_vs_dist();

//stage.show();
        System.out.println("After saving files .... exiting SYS.exit(0)");
        System.exit(0);
    }

    public void saveAsPng(Scene scene, String path) {
        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//--------------------------------------  PAPER TASKS  -----------------------------------------------------
    private void plotGraphAndSaveForTask1(String y_axis_label, String x_axis_label, String fileNameToSave,
            List<Result_T_UE_vs_Chi> listResults, String monte_carlo_str, String mode) {
        String onlyFileNameWithoutPath = fileNameToSave;
        fileNameToSave = OUTPUT_FOLDER_NAME_TASK1 + "/" + fileNameToSave;

        System.out.println("Running plotGraphAndSaveForTask1()  fileToSaveName = " + fileNameToSave);
        String titleGraph = onlyFileNameWithoutPath.replace(".png", "");
        titleGraph = titleGraph.replace("_", " ");
        titleGraph += (", Monte Carlo = " + monte_carlo_str);
        stage.setTitle("Graphs");
        //defining the axes

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(x_axis_label);
        yAxis.setLabel(y_axis_label);
        //creating the chart

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);
        lineChart.setTitle(titleGraph);

        //List of Serieses, initialization
        List<XYChart.Series> series_list = new ArrayList<>();
        for (int i = 0; i < listResults.size(); i++) {
            series_list.add(new XYChart.Series());
        }
        //Set legends
        for (int i = 0; i < series_list.size(); i++) {
            series_list.get(i).setName(listResults.get(i).legendName);
        }

        //populating the series with data
        int num_data_points = listResults.get(0).chi_list.size();

        for (int series_iter = 0; series_iter < series_list.size(); series_iter++) {
            XYChart.Series series = series_list.get(series_iter);
            for (int i = 0; i < num_data_points; i++) {
                if (mode.equalsIgnoreCase(Mode.AVG_UE_Throughput)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).avg_UE_throughput_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.SPECTRAL_EFFICIENCY)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).spectral_efficiency_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.CELL_EDGE_THROUGHPUT)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).cell_edge_throughput_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.FAIRNESS_INDEX)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).fairness_index_jain_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.DISCRIMINATION_INDEX)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).discrimination_index_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.ENTROPY)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).entropy_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.PROPORTION_UE_DROPPED)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).proportion_UE_dropped_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.PROPORTION_UE_ACTIVE)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).proportion_UE_active_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.EFFECTIVE_CHI_MEAN_BSs)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).effective_chi_meanBSs_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.EFFECTIVE_CHI_PROP_ACTIVE)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).effective_chi_propActiveUEs_list.get(i)));
                } else if (mode.equalsIgnoreCase(Mode.AVG_ACTIVE_UE_THROUGHPUT)) {
                    series.getData().add(new XYChart.Data(listResults.get(series_iter).chi_list.get(i),
                            listResults.get(series_iter).avg_ACTIVE_UE_throughput_list.get(i)));
                }

            }
        }

        Scene scene = new Scene(lineChart, 1000, 800); //Height and Width [Default values]

        lineChart.setAnimated(false);
        for (int i = 0; i < series_list.size(); i++) {
            lineChart.getData().add(series_list.get(i)); //append
        }

        stage.setScene(scene);
//        scene.getStylesheets().add("file.css");

        saveAsPng(scene, fileNameToSave);

    }

    public void plot_UE_things_vs_chi() {
        String folderName = "Avg_Metrics_vs_Chi";
        String fileName = "";//"Avg_Throughput_vs_chi_MC_1000_JT_1";
//        String imageFile = "Avg UE Throughput vs Chi.png";
        Reader reader = new Reader();
        List<Result_T_UE_vs_Chi> list = new ArrayList<>();

        String monte_carlo_str = "1000";
        for (int JT = 0; JT <= JT_FINAL; JT++) {
            fileName = folderName + "/Avg_Throughput_vs_chi_MC_" + monte_carlo_str + "_JT_" + String.valueOf(JT) + ".csv";
            if (TAKE_AFTER_CALCS) {
                fileName = folderName + "/Avg_Throughput_vs_chi_MC_" + monte_carlo_str + "_JT_" + String.valueOf(JT) + "_Take_after_calcs.csv";
            }
//            System.out.println("FileName to read .. = " + fileName + " , image file name = " + imageFile);
            Result_T_UE_vs_Chi res = reader.read_UE_vs_Chi_once(fileName);
            res.legendName = "JT=" + (String.valueOf(JT));
//            res.legendName = "" + (String.valueOf(JT));
            list.add(res);
        }

        plotGraphAndSaveForTask1("Average UE Throughput (kBps)", "Chi (%)", "Avg UE Throughput vs Chi.png", list, monte_carlo_str, Mode.AVG_UE_Throughput);
        plotGraphAndSaveForTask1("Spectral Efficiency", "Chi (%)", "Avg Spectral Efficiency vs Chi.png", list, monte_carlo_str, Mode.SPECTRAL_EFFICIENCY);
        plotGraphAndSaveForTask1("Jain's Fairness Index", "Chi (%)", "Jain's Fairness Index vs Chi.png", list, monte_carlo_str, Mode.FAIRNESS_INDEX);
        plotGraphAndSaveForTask1("Cell-Edge Throughput (kBps)", "Chi (%)", "Cell Edge Throughput vs Chi.png", list, monte_carlo_str, Mode.CELL_EDGE_THROUGHPUT);
        plotGraphAndSaveForTask1("Discrimination Index", "Chi (%)", "Discrimination Index vs Chi.png", list, monte_carlo_str, Mode.DISCRIMINATION_INDEX);
        plotGraphAndSaveForTask1("Entropy", "Chi (%)", "Entropy vs Chi.png", list, monte_carlo_str, Mode.ENTROPY);
        plotGraphAndSaveForTask1("%UE dropped", "Chi (%)", "%UE dropped vs Chi.png", list, monte_carlo_str, Mode.PROPORTION_UE_DROPPED);
//Nov 30, 2019
        plotGraphAndSaveForTask1("%UE active", "Chi(%)", "%UE active vs Chi.png", list, monte_carlo_str, Mode.PROPORTION_UE_ACTIVE);
        plotGraphAndSaveForTask1("Effective Chi(%) mean BSs", "Chi(%)", "Effective_chi_mean_BSs vs Chi.png", list, monte_carlo_str, Mode.EFFECTIVE_CHI_MEAN_BSs);
        plotGraphAndSaveForTask1("Effective Chi(%) prop active UEs", "Chi(%)", "Effective_chi_prop_active_UEs vs Chi.png", list, monte_carlo_str, Mode.EFFECTIVE_CHI_PROP_ACTIVE);
        plotGraphAndSaveForTask1("Avg ACTIVE UE Throughput (kBps)", "Chi(%)", "T_avg active UE vs Chi.png", list, monte_carlo_str, Mode.AVG_ACTIVE_UE_THROUGHPUT);
    }

}

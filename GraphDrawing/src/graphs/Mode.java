package graphs;


public class Mode {

    public static String AVG_UE_Throughput = "AVERAGE_THROUGHPUT";
    public static String SPECTRAL_EFFICIENCY = "SPECTRAL_EFFICIENCY";
    public static String FAIRNESS_INDEX = "FAIRNESS_INDEX";
    public static String CELL_EDGE_THROUGHPUT = "CELL_EDGE_THROUGHPUT";
    public static String DISCRIMINATION_INDEX = "DISCRIMINATION_INDEX";
    public static String ENTROPY = "ENTROPY";
    public static String PROPORTION_UE_DROPPED = "PROPORTION_UE_DROPPED";
//New metrics [30 Nov 2019]
    public static String PROPORTION_UE_ACTIVE = "PROPORTION_UE_ACTIVE";
    public static String EFFECTIVE_CHI_MEAN_BSs = "EFFECTIVE_CHI_MEAN_BSs";
    public static String EFFECTIVE_CHI_PROP_ACTIVE = "EFFECTIVE_CHI_PROP_ACTIVE";
    public static String AVG_ACTIVE_UE_THROUGHPUT = "AVG_ACTIVE_UE_THROUGHPUT";
}
/*
    // ----------------------------------------------- TASK 2 ----------------------------------
    private void plot_avg_T_vs_dist() {
        String folderName = "UE_T_avg_vs_distance_BS";
        String fileName = "", outputFileNameToSave = "";
        int JT_final = 3, JT_initial = 0;
        String monte_carlo = "1000";

        for (int JT = JT_initial; JT <= JT_final; JT++) {
            fileName = folderName + "/UE_T_avg_vs_distance_BS_MC_" + monte_carlo + "_JT_" + String.valueOf(JT) + ".csv";
            if (TAKE_AFTER_CALCS) {
                fileName = folderName + "/UE_T_avg_vs_distance_BS_MC_" + monte_carlo + "_JT_Take_after_calcs" + String.valueOf(JT) + ".csv";

            }
            outputFileNameToSave = folderName + "/UE_T_avg_vs_distance_BS_MC_" + monte_carlo + "_JT_" + String.valueOf(JT) + ".png";
            String[] headings = Reader.read_headings(fileName);
            List< List<Double>> data_matrix = Reader.read_data(fileName, headings.length);

            plotGraphAndSaveForTask2("Distance(m)", "Avg UE Throughput (kBps)", "Avg UE Throughput vs Distance for various chi",
                    headings, data_matrix, outputFileNameToSave);

        }

    }

    private void plotGraphAndSaveForTask2(String x_axis_title,
            String y_axis_title, String titleGraph, String[] headings, List< List<Double>> column_data_list, String outputFileNameToSave) {

        //headings[0]: Distance(m)
        //headings[1]: Chi = 0.1 and so on...
        //column_data_list[0]: Distance column
        //column_data_list[1]: Chi = 0.1 column of UE avg Throughputs
        stage.setTitle("Graphs");
        //defining the axes

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(x_axis_title);
        yAxis.setLabel(y_axis_title);
        //creating the chart

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setCreateSymbols(false);
        lineChart.setTitle(titleGraph);

        //List of Serieses, initialization
        List<XYChart.Series> series_list = new ArrayList<>();
        for (int i = 0; i < (column_data_list.size() - 1); i++) { //since column_data_list[0] is Distance column
            series_list.add(new XYChart.Series());
        }
        //Set legends
        for (int i = 0; i < series_list.size(); i++) {
            series_list.get(i).setName(headings[i + 1]); //headings[i] is Distance
        }

        //populating the series with data
        int num_data_points = column_data_list.get(0).size();

        for (int series_iter = 0; series_iter < series_list.size(); series_iter++) {
            XYChart.Series series = series_list.get(series_iter);
            for (int i = 0; i < num_data_points; i++) {
                series.getData().add(new XYChart.Data(column_data_list.get(0).get(i),
                        column_data_list.get(series_iter + 1).get(i))); //[0] is distance, [series_iter] is other column
            }
        }

        Scene scene = new Scene(lineChart, 1000, 800); //Height and Width [Default values]

        lineChart.setAnimated(false);
        for (int i = 0; i < series_list.size(); i++) {
            lineChart.getData().add(series_list.get(i)); //append
        }

        stage.setScene(scene);
        saveAsPng(scene, outputFileNameToSave);
    }

*/
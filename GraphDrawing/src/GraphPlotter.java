
import java.io.File;
import java.io.IOException;
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

    public static String FILE_NAME = "Conventional.csv";

    public static void main(String[] args) {
        launch(args);
    }

    static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Read data..
        stage = primaryStage;
        System.out.println("Reading data... from csv file ...");
        Reader reader = new Reader();
        Results rs = reader.readThingsFromFile();

        System.out.println("Plotting graph ...");
        plotGraphAndSave("Avg Throughput (kBps)", "Time (hr)", "Conventional_Throughput_vs_Time.png", rs.average_throughput, rs.hour);
        plotGraphAndSave("Avg Power Consumption (W)", "Time (hr)", "Conventional_Power_vs_Time.png", rs.average_power_consumption, rs.hour);
        plotGraphAndSave("Chi (%)", "Time (hr)", "Conventional_Chi_vs_Time.png", rs.get_chi_percentage(), rs.hour);
        plotGraphAndSave("Fairness Index", "Time (hr)", "Conventional_Fairness_Index_vs_Time.png", rs.fairness_index, rs.hour);
        plotGraphAndSave("Spectral Efficiency", "Time (hr)", "Conventional_Spectral_Efficiency_vs_Time.png", rs.fairness_index, rs.hour);
        plotGraphAndSave("Cell-Edge Throughput (kBps)", "Time (hr)", "Conventional_Cell-Edge_Throughput_vs_Time.png", rs.fairness_index, rs.hour);

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

    private void plotGraphAndSave(String y_axis_label, String x_axis_label,
            String fileNameToSaveAndTitle, double[] y_axis_data, double[] x_axis_data) {

        String titleGraph = fileNameToSaveAndTitle.replace(".png", "");
        titleGraph = titleGraph.replace("_", " ");
        stage.setTitle("Graphs");
        //defining the axes

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(x_axis_label);
        yAxis.setLabel(y_axis_label);
        //creating the chart

        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle(titleGraph);
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName(titleGraph);
        //populating the series with data
        int num_data_points = x_axis_data.length;
        for (int i = 0; i < num_data_points; i++) {
            series.getData().add(new XYChart.Data(x_axis_data[i], y_axis_data[i]));
        }

        Scene scene = new Scene(lineChart, 800, 600); //Height and Width [Default values]

        lineChart.setAnimated(false);
        lineChart.getData().add(series);

        saveAsPng(scene, fileNameToSaveAndTitle);
        stage.setScene(scene);
//        saveAsPng(scene, "chart1.png");

    }
}

package prev_things;

import graphs.Results;
import graphs.GraphPlotter;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

public class PrevThings {

    private void plotSaveGraph_Conventional(String y_axis_label, String x_axis_label,
            String fileNameToSaveAndTitle, double[] y_axis_data, double[] x_axis_data) {

        String titleGraph = fileNameToSaveAndTitle.replace(".png", "");
        titleGraph = titleGraph.replace("_", " ");
        GraphPlotter.stage.setTitle("Graphs");
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
        GraphPlotter.stage.setScene(scene);
//        saveAsPng(scene, "chart1.png");

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

    private void plotForNormalConventional(Results rs) {
        plotSaveGraph_Conventional("Avg Throughput (kBps)", "Time (hr)", "Conventional_Throughput_vs_Time.png", rs.average_throughput, rs.hour);
        plotSaveGraph_Conventional("Avg Power Consumption (W)", "Time (hr)", "Conventional_Power_vs_Time.png", rs.average_power_consumption, rs.hour);
        plotSaveGraph_Conventional("Chi (%)", "Time (hr)", "Conventional_Chi_vs_Time.png", rs.get_chi_percentage(), rs.hour);
        plotSaveGraph_Conventional("Fairness Index", "Time (hr)", "Conventional_Fairness_Index_vs_Time.png", rs.fairness_index, rs.hour);
        plotSaveGraph_Conventional("Spectral Efficiency", "Time (hr)", "Conventional_Spectral_Efficiency_vs_Time.png", rs.fairness_index, rs.hour);
        plotSaveGraph_Conventional("Cell-Edge Throughput (kBps)", "Time (hr)", "Conventional_Cell-Edge_Throughput_vs_Time.png", rs.fairness_index, rs.hour);

    }
}

package graphs;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class GraphPlotter extends Application {

    public static int TASK_MODE = 2;

    public static int JT_FINAL_TASK_1 = 3;

    public static String FILE_NAME = "Conventional.csv"; //Don't remove, otherwise Reader class gives errors.
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
        if (TASK_MODE == 1) {
            RunnerTask1.plot_UE_things_vs_chi();
        } else if (TASK_MODE == 2) {
            RunnerTask2.plotForTask2();
        }
//        plot_avg_T_vs_dist();

//stage.show();
        System.out.println("After saving files .... exiting SYS.exit(0)");
        System.exit(0);
    }

    public static void saveAsPng(Scene scene, String path) {
        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
        }
    }



}

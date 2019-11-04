package comp_simulation;

import objects.BaseStation;
import java.util.List;

public class Helper {

    public static void printBaseStationLocations(List<BaseStation> baseStations) {
        //Printing locations ... correct locations
        for (BaseStation bs : baseStations) {
            System.out.println(bs.toString());
        }
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static double DEGREE_TO_RADIAN(double rad) {
        return (rad * (Math.PI / 180.0));
    }

}

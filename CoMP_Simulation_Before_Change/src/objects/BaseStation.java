package objects;

import java.util.ArrayList;
import java.util.List;

public class BaseStation {

    //Parameters
    public double x_pos;
    public double y_pos;
    public int base_station_id;
    public int tier;

    public List<User> users_of_this_baseStation = new ArrayList<>();
    
    public int num_available_slots_for_users;
    
    
    
    @Override
    public String toString() {
        return "BaseStation{" + "bs_id = " + base_station_id + ", x_pos=" + x_pos + ", y_pos=" + y_pos + ", tier=" + tier + '}';
    }

    public BaseStation(int base_station_id, double x_pos, double y_pos, int tier) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.base_station_id = base_station_id;
        this.tier = tier;
    }

    public BaseStation() {
        this.x_pos = 0;
        this.y_pos = 0;
        this.base_station_id = 0;
        this.tier = 0;
    }

    public BaseStation(double x_pos, double y_pos) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.base_station_id = 0;
        this.tier = 0;
    }

    public BaseStation(double x_pos, double y_pos, int tier) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.tier = tier;
        this.base_station_id = 0;
    }

    public static void placeBaseStations(List<BaseStation> baseStations_List, double radius, int tier) {
        if (tier < 1) {
            baseStations_List.add(new BaseStation(1, 0, 0, tier));
            return;
        }

        //Center Base Station
        int bs_id = 0;
        BaseStation center = new BaseStation(bs_id++, 0, 0, 0); //tier = 0
        baseStations_List.add(center);

        double interBS = Math.pow(3, 0.5) * radius; //root(3)/2 * cell_radius * 2 is the interCellBS
        double half_interBS = interBS * 0.5;

        double[] xshift = {0, 1.5 * radius, 1.5 * radius, 0, -1.5 * radius, -1.5 * radius};
        double[] yshift = {interBS, half_interBS, -half_interBS, -interBS, -half_interBS, half_interBS};

        int tiers_matched = 1;
        BaseStation bs;
        double ibs = 0, x = 0, y = 0;

        while (tiers_matched <= tier) {
            ibs += interBS;


            //Top Base Station
            y = ibs;
            x = 0;
            baseStations_List.add(new BaseStation(bs_id++, x, y, tiers_matched));

            //Right-Downs.
            for (int itr = 0; itr < tiers_matched; itr++) {
                x = x + (1.5 * radius);
                y = y - (half_interBS);
                baseStations_List.add(new BaseStation(bs_id++, x, y, tiers_matched));

            }
            //Just Downs
            for (int itr = 0; itr < tiers_matched; itr++) {
                //No change in x.
                y = y - (interBS);
                baseStations_List.add(new BaseStation(bs_id++, x, y, tiers_matched));

            }
            //Left-Downs.
            for (int itr = 0; itr < tiers_matched; itr++) {
                x = x - (1.5 * radius);
                y = y - (half_interBS);
                baseStations_List.add(new BaseStation(bs_id++, x, y, tiers_matched));

            }
            //Left UPS 
            for (int itr = 0; itr < tiers_matched; itr++) {
                x = x - (1.5 * radius);
                y = y + (half_interBS);
                baseStations_List.add(new BaseStation(bs_id++, x, y, tiers_matched));
            }
            //Just Ups
            for (int itr = 0; itr < tiers_matched; itr++) {
                //No change in x.
                y = y + (interBS);
                baseStations_List.add(new BaseStation(bs_id++, x, y, tiers_matched));

            }
            //Right-Ups.[ONE LESS ... since top was taken.]
            for (int itr = 0; itr < (tiers_matched - 1); itr++) {
                x = x + (1.5 * radius);
                y = y + (half_interBS);
                baseStations_List.add(new BaseStation(bs_id++, x, y, tiers_matched));
            }

            tiers_matched++; //increment the tiers.
        }

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.x_pos) ^ (Double.doubleToLongBits(this.x_pos) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.y_pos) ^ (Double.doubleToLongBits(this.y_pos) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseStation other = (BaseStation) obj;
        if (Double.doubleToLongBits(this.x_pos) != Double.doubleToLongBits(other.x_pos)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y_pos) != Double.doubleToLongBits(other.y_pos)) {
            return false;
        }
        return true;
    }

}

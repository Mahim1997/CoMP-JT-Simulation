package sim_objects;

import java.util.List;

public class BaseStation {

//Normal Parameters
    public double x_pos;
    public double y_pos;
    public int base_station_id;
    public int tier;

//For BS parameters
    public int num_available_slots;
    public int num_initial_slots;
//For UE paramaters
    public double power_received_by_user_mW;

//For DEBUG
//   public List<User> list_users = new ArrayList<>();
//Constructors
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

    public double get_current_chi() {
        double available_RB_double = (double) this.num_available_slots;
        double initial_RB_double = (double) this.num_initial_slots;
        double used_RB_double = initial_RB_double - available_RB_double;
        double current_chi = used_RB_double / initial_RB_double;
        return current_chi;
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
        System.out.println("-->>BaseStation.java : Tier = " + tier + ", No. of BSs = " + baseStations_List.size());
    }

    @Override
    public String toString() {
        return "BaseStation{" + "x_pos=" + x_pos + ", y_pos=" + y_pos + ", base_station_id=" + base_station_id + ", base_station_tier = " + tier + ", num_available_slots=" + num_available_slots + ", power_received_by_user_mW=" + power_received_by_user_mW + '}';
    }

    public static int NUMBER_OF_BASE_STATIONS(int tier) {
        return ((1 + (3 * (tier) * (tier + 1))));
    }

}

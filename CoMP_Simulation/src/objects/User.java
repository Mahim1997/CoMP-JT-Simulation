package objects;

import comp_simulation.Helper;

public class User {
    public double x_pos;
    public double y_pos;

    public User(double x_pos, double y_pos) {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }
    
    
    
    
    public double getDistanceFromBS(BaseStation bs){
        return Helper.getDistance(x_pos, y_pos, bs.x_pos, bs.y_pos);
    }
}

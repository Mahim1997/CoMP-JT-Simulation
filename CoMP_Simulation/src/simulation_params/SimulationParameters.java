package simulation_params;

import comp_simulation.Main;

public class SimulationParameters {

    /*
    These parameters are for varying chi and measuring avg UE throughput [for paper work]
     */
    public double chi_initial = 0.1; //DEBUG
    public double chi_final = 1;
    public double chi_step_size = 0.005;
//-------------------------------- FOR TASK 2 ----------------------------
    public double chi_step_size_task_2 = 0.1;

    public double chi_for_position = 0.3;

    public int JT_INITIAL = Main.JT_INITIAL; //0->Conventional, 1->DPS
    public int JT_FINAL = Main.JT_FINAL;
//-------------------------------------------------------
//TESTING BELOW
//    public double chi_initial = 0.5;
//    public double chi_final = 0.505;
//    public double chi_step_size = 0.01;
    /*
    JT = JT_VALUE    = 0 -> Conventional [only distances] {not included}
                        = 1 -> DPS
                        = 2 -> JT (2 BS give power simultaneously to the UE)
                        and so on...
     */
    public int JT_VALUE = 1; //DPS
//Task 2
    public double distance_initial;
    public double distance_final;
    public double distance_taken = distance_initial;
    public double distance_increment;
    //-----------------------------------------------------------------

    //Parameters for running the simulation.
    //Will be updated continuously.
    public String simulationType;

    //1. Normal Parameters
    public double cell_radius; //cell radius in meters
    public int tier; //How many tiers
    public double power_transmitted; //Pt: transmission power in dBm
    public double frequency_carrier; //fc: Carrier frequency (Hz)
    public double bandwidth; //BW: in Hz
    public int monte_carlo; //No. of simulations ... usually 1k or 10k

    //2. Path loss parameters
    public double path_loss_reference_distance; //d_0 : usually 0.1 km
    public double path_loss_exponent_alpha; //alpha: usually 3.574
    public double path_loss_standard_deviation; //sigma: usually 8

    public double NOISE_SPECTRAL_POWER_DENSITY; //-174 dBm/Hz

    //3. Energy Parameters
    public double power_max; //P_max = 10^(Pt/10) * 10^-3  ... to convert dBm to watts
    public double power_zeroOutput; //P_0 usually 130 W
    public double power_numberOfTransceivers; // NTRx = 1 usually
    public double power_delP; //delta power ... slope of load-dependent power consumption, usually 4.7

    public double[] chi = new double[24]; //24 hours 0 to 1 .. depends on traffic

    //4. Green energy model parameters
    public double storage_factor = 0.96;
    public double storage_max = 600;
    public double line_loss = 0.08;

    //5. Solar data.
    public double[] solar_data = new double[24];

    public void printParameters() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "SimulationParameters{" + "simulationType=" + simulationType + ", cell_radius=" + cell_radius + ", power_transmitted=" + power_transmitted + ",tier = " + tier + ", frequency_carrier=" + frequency_carrier + ", bandwidth=" + bandwidth + ", monte_carlo=" + monte_carlo + ", path_loss_reference_distance=" + path_loss_reference_distance + ", path_loss_exponent_alpha=" + path_loss_exponent_alpha + ", path_loss_standard_deviation=" + path_loss_standard_deviation + ", power_max=" + power_max + ", power_zeroOutput=" + power_zeroOutput + ", power_numberOfTransceivers=" + power_numberOfTransceivers + ", power_delP=" + power_delP + ", chi=" + getStringArray(chi) + ", storage_factor=" + storage_factor + ", storage_max=" + storage_max + ", line_loss=" + line_loss + ", solar_data=" + getStringArray(solar_data) + '}';
    }

    private String getStringArray(double[] chi) {
        String s = "";
        for (double x : chi) {
            s += (String.valueOf(x) + " ");
        }
        return s;
    }

}

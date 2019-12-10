package simulation_params;

import comp_simulation.Main;
import util_and_calculators.Helper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SimulationParameterBuilder {

    public static String inputFile_solar = "solar_2kW_annual.csv";

    public static SimulationParameters buildSimulationParameters_Urban() {
        SimulationParameters s = new SimulationParameters();

        //1. Normal parameters.
        s.cell_radius = 1000; ///1000m = 1km
        s.tier = 3;
        s.power_transmitted = 43; //dBm
        s.frequency_carrier = 2; //2 GHz
        s.bandwidth = 5 * Math.pow(10, 6); //5 Mhz
        s.monte_carlo = Main.MONTE_CARLO;

        s.NOISE_SPECTRAL_POWER_DENSITY = -174; //-174 dBm/Hz
        
        //2. Path loss params
        s.path_loss_reference_distance = 0.1; //km
        s.path_loss_exponent_alpha = 3.574;
        s.path_loss_standard_deviation = 8;

        //3. Energy Params
        s.power_max = Helper.convertToWatts_From_dBm(s.power_transmitted);
        s.power_zeroOutput = 130;
        s.power_numberOfTransceivers = 1;
        s.power_delP = 4.7;

        //Given input of chi
        double[] chi = {80, 62, 42, 28, 19, 15, 14, 18, 26, 39, 51, 59, 64, 65, 68, 72, 75, 78, 80, 85, 93, 99, 100, 94};
        for (int i = 0; i < chi.length; i++) {
            s.chi[i] = chi[i] * 0.01; // divide by 100 to get the fraction
        }

        //4. Green Energy Model Params
        s.storage_factor = 0.96;
        s.storage_max = 600;
        s.line_loss = 0.08;

        //5. Solar parameters ... read the file
        double MULTIPLY_FACTOR = 0.5; //divide by 2 since this file has 2kW power.
        double[] data_double = read_solar_data(inputFile_solar, MULTIPLY_FACTOR);
        s.solar_data = data_double.clone();
        
        return s;
    }

    private static double[] read_solar_data(String fileName, double MULTIPLY_FACTOR) {
        double[] data_double = new double[24];
        try {
            
            int cnt = 0;
            BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
            String row = "";
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // do something with the data
                if (!data[1].contains("Hourly Data")) {
                    //System.out.println(data[1]);
                    data_double[cnt++] = Double.parseDouble(data[1]) * MULTIPLY_FACTOR;
                }

            }
            csvReader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data_double;
    }



}

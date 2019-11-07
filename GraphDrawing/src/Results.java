
public class Results {

    public String fileNameToSaveInPng;
    
    public double[] hour = new double[24];
    public double[] chi = new double[24];
    public double[] average_throughput = new double[24];
    public double[] average_power_consumption = new double[24];
    public double[] fairness_index = new double[24];
    public double[] spectral_efficiency = new double[24];
    private double[] multiplied_chi = new double[24];

    public double[] get_chi_percentage() {
        double[] arr = new double[24];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = chi[i] * 100.0;
        }
        return arr;
    }

}

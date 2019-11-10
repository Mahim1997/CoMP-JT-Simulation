import java.util.ArrayList;
import java.util.List;

public class Result_T_UE_vs_Chi {
    public List<Double> chi_list;
    public List<Double> avg_UE_throughput_list;
    public String legendName;
    public int JT_num;

    public Result_T_UE_vs_Chi(String l, int jt){
        this.chi_list = new ArrayList<>();
        this.avg_UE_throughput_list = new ArrayList<>();
        this.legendName = l;
        this.JT_num = jt;
    }
    public void printResult(){
        System.out.print("Chi: ");
        printList(chi_list);
        System.out.print("UE_T_avg: ");
        printList(avg_UE_throughput_list);
    }
    
    private void printList(List<Double> list){
        list.forEach((d) -> {
            System.out.print(d + " ");
        });
        System.out.println("");
    }
}

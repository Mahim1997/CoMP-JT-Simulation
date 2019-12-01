import matplotlib.pyplot as plt
import csv
import numpy as np

print("About to print graphs ...")
print("Hello there")


class Datas_each_JT():
    def __init__(self):
        self.distance_col = []
        self.chi_0_1_col = []
        self.chi_0_2_col = []
        self.chi_0_3_col = []
        self.chi_0_4_col = []
        self.chi_0_5_col = []
        self.chi_0_6_col = []
        self.chi_0_7_col = []
        self.chi_0_8_col = []
        self.chi_0_9_col = []
        self.chi_1_col = []
    
    def read_from_csv(self, fileName):
        with open(fileName, 'r') as csvFile:
            readCSV = csv.reader(csvFile, delimiter=',')
            num_rows = -1
            for row in readCSV:
                num_rows = num_rows + 1
                if num_rows != 0:
                    self.distance_col.append(row[0])
                    self.chi_0_1_col.append(row[1])
                    self.chi_0_2_col.append(row[2])
                    self.chi_0_3_col.append(row[3])
                    self.chi_0_4_col.append(row[4])
                    self.chi_0_5_col.append(row[5])
                    self.chi_0_6_col.append(row[6])
                    self.chi_0_7_col.append(row[7])
                    self.chi_0_8_col.append(row[8])
                    self.chi_0_9_col.append(row[9])
                    self.chi_1_col.append(row[10])
    
    def print_first_elements(self):
        print("self.distance_col[0] = ", self.distance_col[0])
        print("self.chi_0_1_col[0] = ", self.chi_0_1_col[0])
        print("self.chi_0_2_col[0] = ", self.chi_0_2_col[0])
        print("self.chi_0_3_col.size = " + str(len(self.chi_0_3_col)))

ob_jt_0 = Datas_each_JT()
ob_jt_0.read_from_csv("UE_T_avg_vs_distance_BS_MC_1000_JT_0_Take_after_calcs.csv")
# ob_jt_0.print_first_elements()

ob_jt_1 = Datas_each_JT()
ob_jt_1.read_from_csv("UE_T_avg_vs_distance_BS_MC_1000_JT_1_Take_after_calcs.csv")
# ob_jt_1.print_first_elements()

ob_jt_2 = Datas_each_JT()
ob_jt_2.read_from_csv("UE_T_avg_vs_distance_BS_MC_1000_JT_2_Take_after_calcs.csv")

ob_jt_3 = Datas_each_JT()
ob_jt_3.read_from_csv("UE_T_avg_vs_distance_BS_MC_1000_JT_3_Take_after_calcs.csv")



##x = [1,2,3]
##y = [5,7,4]
##
##x2 = x
##y2 = [10, 14, 12]
##
##plt.plot(x, y, label='First_Graph')
##plt.plot(x2, y2, label='Second_Graph')
##plt.xlabel('X axis')
##plt.ylabel('Y axis')
##
##plt.title('Title\nCheck it out')
##plt.legend()
##
##
##plt.show()

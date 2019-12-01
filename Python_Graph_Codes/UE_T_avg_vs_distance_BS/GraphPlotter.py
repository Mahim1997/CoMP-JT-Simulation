import matplotlib.pyplot as plt
from matplotlib import style
import csv
import numpy as np

print("About to print graphs ...")

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


# ---------------------------- Object creations --------------------------------------
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


# -------------------------------------- DEBUGGING ----------------------------------
# print("Printing data after retrieval ... ")

# for d, c0_1, c0_2 in zip(ob_jt_0.distance_col, ob_jt_0.chi_0_1_col, ob_jt_0.chi_0_2_col):
    # print("Disatance = " + str(d) + " , Chi[0.1] T_avg = " + str(c0_1) + ", Chi[0.2] T_avg = " + str(c0_2))

# -------------------------------------- GRAPH CODE ------------------------------------

# style.use('fivethirtyeight') # add style

# fig = plt.figure() # retrieve the figure

# -------------------------------------------------------------------------------------------------


# --------------------------------------------- Change CSV File -------------------------------
def change_csv_file(dist_col, jt_0_col, jt_1_col, jt_2_col, jt_3_col, fileNameToChange):
    with open(fileNameToChange, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.write('Distance(m), JT = 0, JT = 1, JT = 2, JT = 3')
        for d, jt0, jt1, jt2, jt3 in zip(dist_col, jt_0_col, jt_1_col, jt_2_col):
            str_to_write = str(d) + ',' + str(jt0) + ',' + str(jt1) + ',' + str(jt2) + ',' + str(jt3) + '\n'
            writer.write(str_to_write)



def run_all_changes():
    change_csv_file(ob_jt_0.dist_col, ob_jt_0.chi_0_1_col, ob_jt_1.chi_0_1_col, ob_jt_2.chi_0_1_col, ob_jt_3.chi_0_1_col)


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

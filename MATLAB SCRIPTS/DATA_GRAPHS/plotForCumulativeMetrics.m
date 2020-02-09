% ------------------------ read data of 19 JT values ----------------------
% folderPath = "CUMULATIVE_DAY_THROUGHPUT_DATA";
% datas_jt_0 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_0_Take_after_calcs.csv");
% datas_jt_1 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_1_Take_after_calcs.csv");
% datas_jt_2 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_2_Take_after_calcs.csv");
% datas_jt_3 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_3_Take_after_calcs.csv");
% datas_jt_4 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_4_Take_after_calcs.csv");
% datas_jt_5 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_5_Take_after_calcs.csv");
% datas_jt_6 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_6_Take_after_calcs.csv");
% datas_jt_7 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_7_Take_after_calcs.csv");
% datas_jt_8 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_8_Take_after_calcs.csv");
% datas_jt_9 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_9_Take_after_calcs.csv");
% datas_jt_10 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_10_Take_after_calcs.csv");
% datas_jt_11 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_11_Take_after_calcs.csv");
% datas_jt_12 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_12_Take_after_calcs.csv");
% datas_jt_13 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_13_Take_after_calcs.csv");
% datas_jt_14 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_14_Take_after_calcs.csv");
% datas_jt_15 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_15_Take_after_calcs.csv");
% datas_jt_16 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_16_Take_after_calcs.csv");
% datas_jt_17 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_17_Take_after_calcs.csv");
% datas_jt_18 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_18_Take_after_calcs.csv");
% datas_jt_19 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_19_Take_after_calcs.csv");

% ----------------------------- Cuml Throughput per day ----------------------

% metrics_0 = sum(datas_jt_0(:, 2));
% metrics_1 = sum(datas_jt_1(:, 2));
% metrics_2 = sum(datas_jt_2(:, 2));
% metrics_3 = sum(datas_jt_3(:, 2));
% metrics_4 = sum(datas_jt_4(:, 2));
% metrics_5 = sum(datas_jt_5(:, 2));
% metrics_6 = sum(datas_jt_6(:, 2));
% metrics_7 = sum(datas_jt_7(:, 2));
% metrics_8 = sum(datas_jt_8(:, 2));
% metrics_9 = sum(datas_jt_9(:, 2));
% metrics_10 = sum(datas_jt_10(:, 2));
% metrics_11 = sum(datas_jt_11(:, 2));
% metrics_12 = sum(datas_jt_12(:, 2));
% metrics_13 = sum(datas_jt_13(:, 2));
% metrics_14 = sum(datas_jt_14(:, 2));
% metrics_15 = sum(datas_jt_15(:, 2));
% metrics_16 = sum(datas_jt_16(:, 2));
% metrics_17 = sum(datas_jt_17(:, 2));
% metrics_18 = sum(datas_jt_18(:, 2));
% metrics_19 = sum(datas_jt_19(:, 2));

% x_axis_data = 1:19;
% y_axis_data = [metrics_1 metrics_2 metrics_3 metrics_4 metrics_5 metrics_6 metrics_7 metrics_8 metrics_9 metrics_10 metrics_11 metrics_12 metrics_13 metrics_14 metrics_15 metrics_16 metrics_17 metrics_18 metrics_19];

% grid on;
% plot(x_axis_data, y_axis_data, '-s');
% xticks(0:1:20);
% xlabel("Number of coordinating Base Stations");
% ylabel("Cumulative Throughput per day (kBps)");
% title("Cumulative T vs No. of coordinating BSs");
% % fileToSave = "GRAPHS/CUMULATIVE_DAY_METRICS/T_Cuml_vs_No_Cordinating_BSs.emf";
% fileToSave = "GRAPHS/CUMULATIVE_DAY_METRICS/UE_dropped_Cuml_vs_No_Cordinating_BSs.emf";
% print(fileToSave, '-dtiff', '-r1440');
% ---------------------------- Cuml UE dropped per day -----------------------

metrics_0 = sum(datas_jt_0(:, 8).*25);
metrics_1 = sum(datas_jt_1(:, 8).*25);
metrics_2 = sum(datas_jt_2(:, 8).*25);
metrics_3 = sum(datas_jt_3(:, 8).*25);
metrics_4 = sum(datas_jt_4(:, 8).*25);
metrics_5 = sum(datas_jt_5(:, 8).*25);
metrics_6 = sum(datas_jt_6(:, 8).*25);
metrics_7 = sum(datas_jt_7(:, 8).*25);
metrics_8 = sum(datas_jt_8(:, 8).*25);
metrics_9 = sum(datas_jt_9(:, 8).*25);
metrics_10 = sum(datas_jt_10(:, 8).*25);
metrics_11 = sum(datas_jt_11(:, 8).*25);
metrics_12 = sum(datas_jt_12(:, 8).*25);
metrics_13 = sum(datas_jt_13(:, 8).*25);
metrics_14 = sum(datas_jt_14(:, 8).*25);
metrics_15 = sum(datas_jt_15(:, 8).*25);
metrics_16 = sum(datas_jt_16(:, 8).*25);
metrics_17 = sum(datas_jt_17(:, 8).*25);
metrics_18 = sum(datas_jt_18(:, 8).*25);
metrics_19 = sum(datas_jt_19(:, 8).*25);

x_axis_data = 1:19;
y_axis_data = [metrics_1 metrics_2 metrics_3 metrics_4 metrics_5 metrics_6 metrics_7 metrics_8 metrics_9 metrics_10 metrics_11 metrics_12 metrics_13 metrics_14 metrics_15 metrics_16 metrics_17 metrics_18 metrics_19];


grid on;
plot(x_axis_data, y_axis_data, '-s');
xticks(0:1:20);
xlabel("Number of coordinating Base Stations");
ylabel("Cumulative No. UE dropped per day");
title("Cumulative UE dropped vs No. coordinating BSs");
% fileToSave = "GRAPHS/CUMULATIVE_DAY_METRICS/T_Cuml_vs_No_Cordinating_BSs.emf";
fileToSave = "GRAPHS/CUMULATIVE_DAY_METRICS/UE_dropped_Cuml_vs_No_Cordinating_BSs.emf";
print(fileToSave, '-dtiff', '-r1440');






folderChosen = "Tier_3_Dummy_Ring_INSIDE_BSs";  % For tier = 3 BUT OUTSIDE RING IS Dummy ring so effectively tier = 2
[datas_jt_0, datas_jt_1, datas_jt_2, datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt] = read_data_from_csv(folderChosen);
folder = "GRAPHS/UPTO_JT_3_WITH_DYNAMIC_JT";
LEGENDS = {"Conventional", "DPS", "JT = 2", "JT = 3", "Dynamic JT"};
xLabel = "Chi(%)";
USE_GRID = 1; % 1 -> USE grid = true, 0 -> DO NOT USE GRID
MAX_Y_AXIS = 1800;
Y_STEP_SIZE = 50;
% ------------------------- AVG THROUGHPUT --------------------------------
% [chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, ~, y_data_5, ...
%     y_data_4] = take_column_wise_datas(datas_jt_0, datas_jt_1, datas_jt_2, ...
%     datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, 2, 1);
% yLabel = "Avg Throughput (kBps)";
% fileToSave = folder + "/T_avg_vs_chi.emf";
% TITLE = "Avg Throughput vs Chi";
% plot_for_upto_jt_3_with_dynamic(chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     LEGENDS, xLabel, yLabel, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE)
% ----------------------------------- PERCENT UE DROPPED ----------------
MAX_Y_AXIS = 100;
Y_STEP_SIZE = 2;
% [chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     y_data_4] = take_column_wise_datas(datas_jt_0, ...
%     datas_jt_1, datas_jt_2, datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, 8, 100);
% yLabel = "%UE Dropped";
% fileToSave = folder + "/Percent_UE_drop_vs_chi.emf";
% TITLE = "%UE Dropped vs Chi";
% plot_for_upto_jt_3_with_dynamic(chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     LEGENDS, xLabel, yLabel, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE)
% -------------------------- PERCENT UE active ------------------------------
% [chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     y_data_4] = take_column_wise_datas(datas_jt_0, ...
%     datas_jt_1, datas_jt_2, datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, 9, 100);
% yLabel = "%UE active";
% fileToSave = folder + "/Percent_UE_active_vs_chi.emf";
% TITLE = "%UE active vs Chi";
% plot_for_upto_jt_3_with_dynamic(chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     LEGENDS, xLabel, yLabel, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE)

% % ----------------------------------- Effective chi ----------------
[chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
    y_data_4] = take_column_wise_datas(datas_jt_0, datas_jt_1, datas_jt_2, ...
    datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, 11, 100);
yLabel = "Effective chi (%)";
fileToSave = folder + "/Effective_chi_vs_chi.emf";
TITLE = "Effective Chi vs Chi";
plot_for_upto_jt_3_with_dynamic(chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
    LEGENDS, xLabel, yLabel, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE)


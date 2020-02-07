folderChosen = "HOURLY_TRAFFIC_VARIATION";
hours = 1:24;
[datas_jt_0, datas_jt_1, datas_jt_2, datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt] = read_data_from_csv(folderChosen);

folder = "GRAPHS/HOURLY_TRAFFIC_VARIATION";
LEGENDS = {"Conventional", "DPS", "JT = 2", "JT = 3"};

USE_GRID = 1; % 1 -> USE grid = true, 0 -> DO NOT USE GRID
MAX_Y_AXIS = 1800;
Y_STEP_SIZE = 50;
MAX_X_AXIS = 25;
X_STEP_SIZE = 1;

xLabel = "Time (hrs)";
% ------------------------- AVG THROUGHPUT --------------------------------
% [chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     T_avg_dynamic] = take_column_wise_datas(datas_jt_0, datas_jt_1, datas_jt_2, ...
%     datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, 2, 1);
% yLabel = "Avg Throughput (kBps)";
% fileToSave = folder + "/T_avg_vs_chi.emf";
% TITLE = "Avg Throughput vs Chi";
% plotter_for_traffic_model(hours, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     LEGENDS, xLabel, yLabel, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE, MAX_X_AXIS, X_STEP_SIZE);

% ------------------------- Plot chi ----------------------------------

% MAX_Y_AXIS = 100;
% Y_STEP_SIZE = 2;
% g_y=[0:Y_STEP_SIZE:MAX_Y_AXIS]; % user defined grid Y [start:spaces:end]
% g_x=[0:X_STEP_SIZE:MAX_X_AXIS]; % user defined grid X [start:spaces:end]
% for i=1:length(g_x)
%    if USE_GRID == 1
%         plot([g_x(i) g_x(i)],[g_y(1) g_y(end)],'k:', 'Linewidth', 0.10); %y grid lines
%    end
%    % plot([g_x(i) g_x(i)],[g_y(1) g_y(end)],'k:'); %y grid lines 
%    hold on;
% end
% for i=1:length(g_y)
%    if USE_GRID == 1
%        plot([g_x(1) g_x(end)],[g_y(i) g_y(i)],'k:', 'Linewidth', 0.10); %x grid lines
%    end
%    % plot([g_x(1) g_x(end)],[g_y(i) g_y(i)],'k:'); %x grid lines
%    hold on;
% end
% yLabel = "Chi (%)";
% TITLE = "Chi (%) vs Time (hrs)";
% xlabel(xLabel);
% ylabel(yLabel);
% title(TITLE);
% plot(hours, chi_percentage, '-o');



% ------------------------- ACTIVE UE THROUGHPUT --------------------------------
% [chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     T_avg_dynamic] = take_column_wise_datas(datas_jt_0, datas_jt_1, datas_jt_2, ...
%     datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, 12, 1);
% yLabel = "ACTIVE UE Throughput (kBps)";
% fileToSave = folder + "/T_avg_active_UE_vs_chi.emf";
% TITLE = "Avg Throughput of Active UEs only vs Chi";
% plotter_for_traffic_model(hours, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     LEGENDS, xLabel, yLabel, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE, MAX_X_AXIS, X_STEP_SIZE);

% ----------------------------------- PERCENT UE DROPPED ----------------
MAX_Y_AXIS = 100;
Y_STEP_SIZE = 2;


% [chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     PERCENT_UE_drop_dynamic] = take_column_wise_datas(datas_jt_0, ...
%     datas_jt_1, datas_jt_2, datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, 8, 100);
% yLabel = "%UE Dropped";
% fileToSave = folder + "/Percent_UE_drop_vs_chi.emf";
% TITLE = "%UE Dropped vs Chi";
% plotter_for_traffic_model(hours, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     LEGENDS, xLabel, yLabel, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE, MAX_X_AXIS, X_STEP_SIZE);
% -------------------------- PERCENT UE active ------------------------------
% [chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     PERCENT_UE_active_dynamic] = take_column_wise_datas(datas_jt_0, ...
%     datas_jt_1, datas_jt_2, datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, 9, 100);
% yLabel = "%UE active";
% fileToSave = folder + "/Percent_UE_active_vs_chi.emf";
% TITLE = "%UE active vs Chi";
% plotter_for_traffic_model(hours, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
%     LEGENDS, xLabel, yLabel, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE, MAX_X_AXIS, X_STEP_SIZE);

% ----------------------------------- Effective chi ----------------
[chi_percentage, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
    EFFECTIVE_chi_dynamic] = take_column_wise_datas(datas_jt_0, datas_jt_1, datas_jt_2, ...
    datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, 11, 100);
yLabel = "Effective chi (%)";
fileToSave = folder + "/Effective_chi_vs_chi.emf";
TITLE = "Effective Chi vs Chi";
plotter_for_traffic_model(hours, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
    LEGENDS, xLabel, yLabel, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE, MAX_X_AXIS, X_STEP_SIZE);


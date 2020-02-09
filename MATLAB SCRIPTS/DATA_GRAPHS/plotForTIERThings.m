% folder1 = "FOR_TIER_THINGS/DUMMY_TIER_USED";
% folder2 = "FOR_TIER_THINGS/NO_DUMMY_TIER";
% 
% NUM_TIERS_USED = 14; % 14 - 1 + 1 = 14
% datas_tier_1 = xlsread(folder1 + "/Avg_Throughput_vs_chi_MC_1000_JT_2_tier_2_Take_after_calcs.csv");
% 
% [r, c] = size(datas_tier_1);
% 
% with_dummy_arr_data = zeros(NUM_TIERS_USED*r, c);
% without_dummy_arr_data = zeros(NUM_TIERS_USED*r, c);
% for i = 1:14
%    file = folder1 + "/Avg_Throughput_vs_chi_MC_1000_JT_2_tier_" + int2str(i+1) + "_Take_after_calcs.csv";
%    with_dummy_arr_data(i, :) = xlsread(file);
%    file = folder2 + "/Avg_Throughput_vs_chi_MC_1000_JT_2_tier_" + int2str(i) + "_Take_after_calcs.csv";   
%    without_dummy_arr_data(i, :) = xlsread(file);
% end
% 

% --------------------- THROUGHPUT ----------------------

% grid on;
% x_data = 1:14; 
% y_data = with_dummy_arr_data(:, 2);
% h1 = plot(x_data, y_data, '-s');
% hold on;
% y2 = without_dummy_arr_data(:, 2);
% h2 = plot(x_data, y2, '-o');
% hold off;
% xticks(0:1:15);
% legend([h1, h2], {"With Dummy Ring", "Without Dummy Ring"});
% xlabel("Effective Tier");
% ylabel("Avg Throughput (kBps)");
% title("Effectiveness of Dummy Ring");
% fileToSave = "GRAPHS/TIER_DUMMY_VS_NOT_DUMMY/T_avg_vs_Tier_Dummy_vs_NO_Dummy.emf";
% print(fileToSave, '-dtiff', '-r1440');


% ----------------- Percent UE dropped ----------------------


grid on;
x_data = 1:14; 
y_data = with_dummy_arr_data(:, 8).*100;
h1 = plot(x_data, y_data, '-s');
hold on;
y2 = without_dummy_arr_data(:, 8).*100;
h2 = plot(x_data, y2, '-o');
hold off;
xticks(0:1:15);
legend([h1, h2], {"With Dummy Ring", "Without Dummy Ring"});
xlabel("Effective Tier");
ylabel("UE dropped (%)");
title("Effectiveness of Dummy Ring");
fileToSave = "GRAPHS/TIER_DUMMY_VS_NOT_DUMMY/UE_drop_vs_Tier_Dummy_vs_NO_Dummy.emf";
print(fileToSave, '-dtiff', '-r1440');



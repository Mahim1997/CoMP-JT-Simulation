% % read from files
% datas_jt_0 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_0_Take_after_calcs.csv');
% datas_jt_1 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_1_Take_after_calcs.csv');
% datas_jt_2 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_2_Take_after_calcs.csv');
% datas_jt_3 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_3_Take_after_calcs.csv');
% datas_jt_4 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_4_Take_after_calcs.csv');
% datas_jt_5 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_5_Take_after_calcs.csv');
% 
% % Chi percentage read
% chi = datas_jt_0(:, 1);
% chi_percentage = chi.*100;
% % display(chi_percentage);
% 
% % Avg UE throughput reads
% T_avg_0 = datas_jt_0(:, 2);
% T_avg_1 = datas_jt_1(:, 2);
% T_avg_2 = datas_jt_2(:, 2);
% T_avg_3 = datas_jt_3(:, 2);
% T_avg_4 = datas_jt_4(:, 2);
% T_avg_5 = datas_jt_5(:, 2);
% CTRL+T to uncomment

yticks(0:100:1500);

% plot datas only of Avg UE throughput vs chi
grid on;
title('Avg Throughput (kBps) vs Chi (%)');
xlabel('Chi(%)');
ylabel('Avg Throughput (kBps)');
h0 = plot(chi_percentage, T_avg_0);
hold on;
h1 = plot(chi_percentage, T_avg_1);
h2 = plot(chi_percentage, T_avg_2);
h3 = plot(chi_percentage, T_avg_3);
% plot(chi_percentage, T_avg_4);
% plot(chi_percentage, T_avg_5);
% legend('Conventional', 'DPS', 'JT = 2', 'JT = 3', 'JT = 4', 'JT = 5');
% legend('Conventional', 'DPS', 'JT = 2', 'JT = 3');
g_y=[0:50:1500]; % user defined grid Y [start:spaces:end]
g_x=[0:1:100]; % user defined grid X [start:spaces:end]
for i=1:length(g_x)
   plot([g_x(i) g_x(i)],[g_y(1) g_y(end)],'k:'); %y grid lines   
   % plot([g_x(i) g_x(i)],[g_y(1) g_y(end)],'k:'); %y grid lines 
   hold on;
end
for i=1:length(g_y)
   plot([g_x(1) g_x(end)],[g_y(i) g_y(i)],'k:'); %x grid lines
   % plot([g_x(1) g_x(end)],[g_y(i) g_y(i)],'Linewidth', 0.25); %x grid lines
   hold on;
end
legend([h0, h1, h2, h3],{'Conventional', 'DPS', 'JT = 2', 'JT = 3'});
hold off;














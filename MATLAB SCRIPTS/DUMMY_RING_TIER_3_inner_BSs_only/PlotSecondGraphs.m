% plot datas only of Avg UE throughput vs chi
grid on;
title('Avg Throughput (kBps) vs Chi (%)');
xlabel('Chi(%)');
ylabel('Avg Throughput (kBps)');
% h0 = plot(chi_percentage, T_avg_0);
% h1 = plot(chi_percentage, T_avg_1);
h2 = plot(chi_percentage, T_avg_2);

% h3 = plot(chi_percentage, T_avg_3);
hold on;
h4 = plot(chi_percentage, T_avg_dynamic);

g_y=[0:50:1500]; % user defined grid Y [start:spaces:end]
g_x=[0:1:100]; % user defined grid X [start:spaces:end]
% for i=1:length(g_x)
%    plot([g_x(i) g_x(i)],[g_y(1) g_y(end)],'k:', 'Linewidth', 0.10); %y grid lines   
%    % plot([g_x(i) g_x(i)],[g_y(1) g_y(end)],'k:'); %y grid lines 
%    hold on;
% end
% for i=1:length(g_y)
%    plot([g_x(1) g_x(end)],[g_y(i) g_y(i)],'k:', 'Linewidth', 0.10); %x grid lines
%    % plot([g_x(1) g_x(end)],[g_y(i) g_y(i)],'k:'); %x grid lines
%    hold on;
% end
critical_chi_1 = 27.5;
critical_chi_2 = 44;
y_values = [0:1:1500];
critical_chi_1 = critical_chi_1.*ones(length(y_values),1);
critical_chi_2 = critical_chi_2.*ones(length(y_values),1);
plot(critical_chi_1, y_values, '--k');
plot(critical_chi_2, y_values, '--k');

legend([h2, h4],{'JT 2', 'Dynamic JT'});
hold off;

% saveas(gcf, 'T_avg_vs_chi.png');
% print('Plot1.png', '-dtiff', '-r1440');
print('DummyRing_Tier3_With_Dynamic_JT_vs_JT_2.png', '-dtiff', '-r1440');

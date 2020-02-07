function [] = plotter_for_traffic_model(x_data, y_data_0, y_data_1, y_data_2, y_data_3, y_data_4, y_data_5, ...
    LEGENDS, x_label, y_label, TITLE, fileToSave, USE_GRID, MAX_Y_AXIS, Y_STEP_SIZE, MAX_X_AXIS, X_STEP_SIZE)
    grid on;
    grid;
    h0 = plot(x_data, y_data_0, '-o');
    title(TITLE);
    xlabel(x_label);
    ylabel(y_label);
    hold on;
    h1 = plot(x_data, y_data_1, '-o');
    h2 = plot(x_data, y_data_2, '-o');
    h3 = plot(x_data, y_data_3, '-o');
    h4 = plot(x_data, y_data_4, '--o');
    g_y=[0:Y_STEP_SIZE:MAX_Y_AXIS]; % user defined grid Y [start:spaces:end]
    g_x=[0:X_STEP_SIZE:MAX_X_AXIS]; % user defined grid X [start:spaces:end]
    for i=1:length(g_x)
       if USE_GRID == 1
            plot([g_x(i) g_x(i)],[g_y(1) g_y(end)],'k:', 'Linewidth', 0.10); %y grid lines
       end
       % plot([g_x(i) g_x(i)],[g_y(1) g_y(end)],'k:'); %y grid lines 
       hold on;
    end
    for i=1:length(g_y)
       if USE_GRID == 1
           plot([g_x(1) g_x(end)],[g_y(i) g_y(i)],'k:', 'Linewidth', 0.10); %x grid lines
       end
       % plot([g_x(1) g_x(end)],[g_y(i) g_y(i)],'k:'); %x grid lines
       hold on;
    end
%     legend([h0, h1, h2, h3],LEGENDS);
    legend([h0, h1, h2, h3, h4],LEGENDS);
%     print(fileToSave, '-dtiff', '-r1440');
    % saveas(gcf, fullfile(folderToSave, sprintf('FIGURE.emf')));
    hold off;
    fprintf("-->>NOT Saving to file name = %s\n", fileToSave);
end
function [chi_percentage, y_datas_0, y_datas_1, y_datas_2, y_datas_3, y_datas_4, y_datas_5, ...
    y_datas_dynamic] = take_column_wise_datas(datas_jt_0, datas_jt_1, datas_jt_2, ...
    datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt, COL_IDX, MULTIPLIER)
    % % Chi percentage read
    chi = datas_jt_0(:, 1);
    chi_percentage = chi.*100;
    % % Avg UE throughput reads
    y_datas_0 = datas_jt_0(:, COL_IDX).*MULTIPLIER;
    y_datas_1 = datas_jt_1(:, COL_IDX).*MULTIPLIER;
    y_datas_2 = datas_jt_2(:, COL_IDX).*MULTIPLIER;
    y_datas_3 = datas_jt_3(:, COL_IDX).*MULTIPLIER;
    y_datas_4 = datas_jt_4(:, COL_IDX).*MULTIPLIER;
    y_datas_5 = datas_jt_5(:, COL_IDX).*MULTIPLIER;
    y_datas_dynamic = datas_dynamic_jt(:, COL_IDX).*MULTIPLIER;
% CTRL+T to uncomment
end
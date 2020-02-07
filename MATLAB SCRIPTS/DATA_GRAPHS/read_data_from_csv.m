function [datas_jt_0, datas_jt_1, datas_jt_2, datas_jt_3, datas_jt_4, datas_jt_5, datas_dynamic_jt] = read_data_from_csv(folderPath)
    % read from files
    datas_jt_0 = xlsread(folderPath + "/Avg_Throughput_vs_chi_MC_1000_JT_0_Take_after_calcs.csv");
    datas_jt_1 = xlsread(folderPath + '/Avg_Throughput_vs_chi_MC_1000_JT_1_Take_after_calcs.csv');
    datas_jt_2 = xlsread(folderPath + '/Avg_Throughput_vs_chi_MC_1000_JT_2_Take_after_calcs.csv');
    datas_jt_3 = xlsread(folderPath + '/Avg_Throughput_vs_chi_MC_1000_JT_3_Take_after_calcs.csv');
    datas_jt_4 = xlsread(folderPath + '/Avg_Throughput_vs_chi_MC_1000_JT_4_Take_after_calcs.csv');
    datas_jt_5 = xlsread(folderPath + '/Avg_Throughput_vs_chi_MC_1000_JT_5_Take_after_calcs.csv');
    datas_dynamic_jt = xlsread(folderPath + '/Avg_Throughput_vs_chi_MC_1000_JT_6_Take_after_calcs.csv');
    %     datas_dynamic_jt = xlsread('Avg_Throughput_vs_chi_MC_1000_DYNAMIC_JT_Take_after_calcs.csv')
%     datas_jt_0 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_0_Take_after_calcs.csv');
%     datas_jt_1 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_1_Take_after_calcs.csv');
%     datas_jt_2 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_2_Take_after_calcs.csv');
%     datas_jt_3 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_3_Take_after_calcs.csv');
%     datas_jt_4 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_4_Take_after_calcs.csv');
%     datas_jt_5 = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_5_Take_after_calcs.csv');
%     datas_dynamic_jt = xlsread('Avg_Throughput_vs_chi_MC_1000_JT_6_Take_after_calcs.csv');
end
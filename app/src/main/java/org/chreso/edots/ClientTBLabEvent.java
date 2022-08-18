package org.chreso.edots;

import java.sql.Date;

public class ClientTBLabEvent {
    private String client_tb_lab_uuid;
    private String client_tb_lab_date;
    private String client_uuid;
    private String level_of_treatment_for_lab_examination;
    private String lab_test_type;
    private String lab_result;

    public String getLevel_of_treatment_for_lab_examination() {
        return level_of_treatment_for_lab_examination;
    }

    public void setLevel_of_treatment_for_lab_examination(String level_of_treatment_for_lab_examination) {
        this.level_of_treatment_for_lab_examination = level_of_treatment_for_lab_examination;
    }

    public String getLab_test_type() {
        return lab_test_type;
    }

    public void setLab_test_type(String lab_test_type) {
        this.lab_test_type = lab_test_type;
    }



    public String getClient_tb_lab_uuid() {
        return client_tb_lab_uuid;
    }

    public void setClient_tb_lab_uuid(String client_tb_lab_uuid) {
        this.client_tb_lab_uuid = client_tb_lab_uuid;
    }

    public String getClient_tb_lab_date() {
        return client_tb_lab_date;
    }

    public void setClient_tb_lab_date(Date client_tb_lab_date) {
        this.client_tb_lab_date = Utils.getFormattedDate(client_tb_lab_date);
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public String getLab_result() {
        return lab_result;
    }

    public void setLab_result(String sputum_smear_or_sputum_culture_result) {
        this.lab_result = sputum_smear_or_sputum_culture_result;
    }


}

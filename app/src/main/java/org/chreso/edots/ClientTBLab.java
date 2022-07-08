package org.chreso.edots;

import java.sql.Date;

public class ClientTBLab {
    private String client_tb_lab_uuid;
    private Date client_tb_lab_date;
    private String client_uuid;
    private String sputum_smear_or_sputum_culture_result;

    public ClientTBLab(String client_tb_lab_uuid, Date client_tb_lab_date, String client_uuid, String sputum_smear_or_sputum_culture_result) {
        this.client_tb_lab_uuid = client_tb_lab_uuid;
        this.client_tb_lab_date = client_tb_lab_date;
        this.client_uuid = client_uuid;
        this.sputum_smear_or_sputum_culture_result = sputum_smear_or_sputum_culture_result;
    }

    public String getClient_tb_lab_uuid() {
        return client_tb_lab_uuid;
    }

    public void setClient_tb_lab_uuid(String client_tb_lab_uuid) {
        this.client_tb_lab_uuid = client_tb_lab_uuid;
    }

    public Date getClient_tb_lab_date() {
        return client_tb_lab_date;
    }

    public void setClient_tb_lab_date(Date client_tb_lab_date) {
        this.client_tb_lab_date = client_tb_lab_date;
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public String getSputum_smear_or_sputum_culture_result() {
        return sputum_smear_or_sputum_culture_result;
    }

    public void setSputum_smear_or_sputum_culture_result(String sputum_smear_or_sputum_culture_result) {
        this.sputum_smear_or_sputum_culture_result = sputum_smear_or_sputum_culture_result;
    }




}

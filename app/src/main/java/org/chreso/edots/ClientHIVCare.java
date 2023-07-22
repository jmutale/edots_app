package org.chreso.edots;

import java.sql.Date;

public class ClientHIVCare {
    private String hiv_care_uuid;
    private String client_uuid;
    private Date cpt_date_start;
    private String hiv_care_reg_no;
    private Date hiv_care_date;
    private String arv_eligible;
    private Date arv_start_date;

    public ClientHIVCare(String hiv_care_uuid, String client_uuid, Date cpt_date_start, String hiv_care_reg_no, Date hiv_care_date, String arv_eligible, Date arv_start_date) {
        this.hiv_care_uuid = hiv_care_uuid;
        this.client_uuid = client_uuid;
        this.cpt_date_start = cpt_date_start;
        this.hiv_care_reg_no = hiv_care_reg_no;
        this.hiv_care_date = hiv_care_date;
        this.arv_eligible = arv_eligible;
        this.arv_start_date = arv_start_date;
    }

    public String getHiv_care_uuid() {
        return hiv_care_uuid;
    }

    public void setHiv_care_uuid(String hiv_care_uuid) {
        this.hiv_care_uuid = hiv_care_uuid;
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public Date getCpt_date_start() {
        return cpt_date_start;
    }

    public void setCpt_date_start(Date cpt_date_start) {
        this.cpt_date_start = cpt_date_start;
    }

    public String getHiv_care_reg_no() {
        return hiv_care_reg_no;
    }

    public void setHiv_care_reg_no(String hiv_care_reg_no) {
        this.hiv_care_reg_no = hiv_care_reg_no;
    }

    public Date getHiv_care_date() {
        return hiv_care_date;
    }

    public void setHiv_care_date(Date hiv_care_date) {
        this.hiv_care_date = hiv_care_date;
    }

    public String getArv_eligible() {
        return arv_eligible;
    }

    public void setArv_eligible(String arv_eligible) {
        this.arv_eligible = arv_eligible;
    }

    public Date getArv_start_date() {
        return arv_start_date;
    }

    public void setArv_start_date(Date arv_start_date) {
        this.arv_start_date = arv_start_date;
    }

}

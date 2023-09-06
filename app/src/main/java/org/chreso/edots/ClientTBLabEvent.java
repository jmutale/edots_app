package org.chreso.edots;

import java.sql.Date;

public class ClientTBLabEvent {
    private String client_tb_lab_uuid;
    private String client_tb_lab_date;
    private String client_uuid;
    private String level_of_treatment_for_lab_examination;
    private String lab_test_type;
    private String lab_result;
    private String treatment_failure;
    private String x_ray_done;
    private String x_ray_date;
    private String x_ray_results;
    private String covid_19_vaccination_done;
    private String covid_19_vaccination_date;
    private String covid_19_vaccine;
    private String covid_19_booster_done;
    private String covid_19_booster_date;
    private String covid_19_booster_vaccine;

    public String getCovid_19_vaccination_date() {
        return covid_19_vaccination_date;
    }

    public void setCovid_19_vaccination_date(Date covid_19_vaccination_date) {
        this.covid_19_vaccination_date = Utils.getFormattedDate(covid_19_vaccination_date);
    }

    public String getCovid_19_booster_date() {
        return covid_19_booster_date;
    }

    public void setCovid_19_booster_date(Date covid_19_booster_date) {
        this.covid_19_booster_date = Utils.getFormattedDate(covid_19_booster_date);
    }

    public String getCovid_19_vaccination_done() {
        return covid_19_vaccination_done;
    }

    public void setCovid_19_vaccination_done(String covid_19_vaccination_done) {
        this.covid_19_vaccination_done = covid_19_vaccination_done;
    }

    public String getCovid_19_vaccine() {
        return covid_19_vaccine;
    }

    public void setCovid_19_vaccine(String covid_19_vaccine) {
        this.covid_19_vaccine = covid_19_vaccine;
    }

    public String getCovid_19_booster_done() {
        return covid_19_booster_done;
    }

    public void setCovid_19_booster_done(String covid_19_booster_done) {
        this.covid_19_booster_done = covid_19_booster_done;
    }

    public String getCovid_19_booster_vaccine() {
        return covid_19_booster_vaccine;
    }

    public void setCovid_19_booster_vaccine(String covid_19_booster_vaccine) {
        this.covid_19_booster_vaccine = covid_19_booster_vaccine;
    }

    public String getTreatment_failure() {
        return treatment_failure;
    }

    public void setTreatment_failure(String treatment_failure) {
        this.treatment_failure = treatment_failure;
    }

    public String getX_ray_done() {
        return x_ray_done;
    }

    public void setX_ray_done(String x_ray_done) {
        this.x_ray_done = x_ray_done;
    }

    public String getX_ray_date() {
        return x_ray_date;
    }

    public void setX_ray_date(Date x_ray_date) {
        this.x_ray_date = Utils.getFormattedDate(x_ray_date);
    }

    public String getX_ray_results() {
        return x_ray_results;
    }

    public void setX_ray_results(String x_ray_results) {
        this.x_ray_results = x_ray_results;
    }

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

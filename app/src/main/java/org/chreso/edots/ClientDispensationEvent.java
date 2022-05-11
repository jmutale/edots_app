package org.chreso.edots;

import java.sql.Date;

public class ClientDispensationEvent {
    private String med_drug_uuid;
    private String client_uuid;
    private String dispensation_date;
    private String dose;
    private String items_per_dose;
    private String frequency;
    private String refill_date;
    private String video_path;

    public String getMed_drug_uuid() {
        return med_drug_uuid;
    }

    public void setMed_drug_uuid(String med_drug_uuid) {
        this.med_drug_uuid = med_drug_uuid;
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public String getDispensation_date() {
        return dispensation_date;
    }

    public void setDispensation_date(Date dispensation_date) {
        this.dispensation_date =  Utils.getFormattedDate(dispensation_date);
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getItems_per_dose() {
        return items_per_dose;
    }

    public void setItems_per_dose(String items_per_dose) {
        this.items_per_dose = items_per_dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRefill_date() {
        return refill_date;
    }

    public void setRefill_date(Date refill_date) {
        this.refill_date = Utils.getFormattedDate(refill_date);
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }


}

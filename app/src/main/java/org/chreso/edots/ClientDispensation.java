package org.chreso.edots;

import android.content.Context;

import java.sql.Date;

public class ClientDispensation {
    private String med_drug_uuid;
    private DBHandler dbHandler;

    public ClientDispensation(String med_drug_uuid, String client_uuid, Date dispensation_date, String dose, String items_per_dose, String frequency, Date refill_date, String video_path) {
        this.med_drug_uuid = med_drug_uuid;
        this.client_uuid = client_uuid;
        this.dispensation_date = dispensation_date;
        this.dose = dose;
        this.items_per_dose = items_per_dose;
        this.frequency = frequency;
        this.refill_date = refill_date;
        this.video_path = video_path;
    }

    private String client_uuid;
    private Date dispensation_date;
    private String dose;
    private String items_per_dose;
    private String frequency;

    private Date refill_date;
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

    public Date getDispensation_date() {
        return dispensation_date;
    }

    public void setDispensation_date(Date dispensation_date) {
        this.dispensation_date = dispensation_date;
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

    public Date getRefill_date() {
        return refill_date;
    }

    public void setRefill_date(Date refill_date) {
        this.refill_date = refill_date;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }


    public String getMedDrugName(String med_drug_uuid, Context context) {
        dbHandler = new DBHandler(context);
        String drugName = dbHandler.getDrugNameFromDatabase(med_drug_uuid);
        return drugName;
    }

    public String getClientNrcFromUuid(String client_uuid, Context context){
        dbHandler = new DBHandler(context);
        String clientNrc = dbHandler.getNrcFromClientUuid(client_uuid);
        return clientNrc;
    }
}

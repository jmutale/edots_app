package org.chreso.edots;

import java.io.File;
import java.sql.Date;
import java.sql.Time;

public class ClientDispensationEvent {

    private String dispensation_uuid;
    private String med_drug_uuid;
    private String client_uuid;
    private String chw;
    private String dispensation_date;
    private String dose;
    private String items_per_dose;
    private String frequency;
    private String refill_date;
    private String video;
    private String next_clinic_appointment_date;
    private String refill_date_time;

    public String getChw() {
        return chw;
    }

    public void setChw(String chw) {
        this.chw = chw;
    }

    public String getRefill_date_time() {
        return refill_date_time;
    }

    public void setRefill_date_time(Time refill_date_time) {
        this.refill_date_time = Utils.getFormattedTime(refill_date_time);
    }



    public String getDispensation_uuid() {
        return dispensation_uuid;
    }

    public void setDispensation_uuid(String dispensation_uuid) {
        this.dispensation_uuid = dispensation_uuid;
    }

    public void setDispensation_date(String dispensation_date) {
        this.dispensation_date = dispensation_date;
    }

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

    public String getNext_clinic_appointment_date(){
        return next_clinic_appointment_date;
    }

    public void setNext_clinic_appointment_date(Date next_clinic_appointment_date){
        this.next_clinic_appointment_date = Utils.getFormattedDate(next_clinic_appointment_date);
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

    public String getVideo() {
        return video;
    }

    public void setFile(String video) {
        this.video = video;
    }


}

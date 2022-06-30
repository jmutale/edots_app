package org.chreso.edots;

import java.sql.Date;

public class ClientStatusEvent {
    private String client_status_uuid;
    private String reporting_facility;
    private String client_uuid;
    private String status_date;
    private String client_died;
    private String client_died_date;
    private String cause_of_death;
    private String client_refuses_to_continue_treatment;
    private String client_is_lost_to_follow_up;
    private String client_transferred_out;
    private String client_transferred_out_date;
    private String facility_transferred_to;

    public String getClient_refuses_to_continue_treatment() {
        return client_refuses_to_continue_treatment;
    }

    public void setClient_refuses_to_continue_treatment(String client_refuses_to_continue_treatment) {
        this.client_refuses_to_continue_treatment = client_refuses_to_continue_treatment;
    }

    public String getClient_is_lost_to_follow_up() {
        return client_is_lost_to_follow_up;
    }

    public void setClient_is_lost_to_follow_up(String client_is_lost_to_follow_up) {
        this.client_is_lost_to_follow_up = client_is_lost_to_follow_up;
    }



    public String getClient_status_uuid() {
        return client_status_uuid;
    }

    public void setClient_status_uuid(String client_status_uuid) {
        this.client_status_uuid = client_status_uuid;
    }

    public String getReporting_facility() {
        return reporting_facility;
    }

    public void setReporting_facility(String reporting_facility) {
        this.reporting_facility = reporting_facility;
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public String getStatus_date() {
        return status_date;
    }

    public void setStatus_date(Date status_date) {
        this.status_date = Utils.getFormattedDate(status_date);
    }

    public String getClient_died() {
        return client_died;
    }

    public void setClient_died(String client_died) {
        this.client_died = client_died;
    }

    public String getClient_died_date() {
        return client_died_date;
    }

    public void setClient_died_date(Date client_died_date) {
        this.client_died_date = Utils.getFormattedDate(client_died_date);
    }

    public String getCause_of_death() {
        return cause_of_death;
    }

    public void setCause_of_death(String cause_of_death) {
        this.cause_of_death = cause_of_death;
    }

    public String getClient_transferred_out() {
        return client_transferred_out;
    }

    public void setClient_transferred_out(String client_transferred_out) {
        this.client_transferred_out = client_transferred_out;
    }

    public String getClient_transferred_out_date() {
        return client_transferred_out_date;
    }

    public void setClient_transferred_out_date(Date client_transferred_out_date) {
        this.client_transferred_out_date = Utils.getFormattedDate(client_transferred_out_date);
    }

    public String getFacility_transferred_to() {
        return facility_transferred_to;
    }

    public void setFacility_transferred_to(String facility_transferred_to) {
        this.facility_transferred_to = facility_transferred_to;
    }


}

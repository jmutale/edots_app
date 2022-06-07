package org.chreso.edots;

import java.sql.Date;

public class ClientStatus {
    private String client_status_uuid;
    private String reporting_facility;
    private String client_uuid;
    private Date status_date;
    private String client_died;
    private Date client_died_date;
    private String cause_of_death;
    private String client_transferred_out;
    private Date client_transferred_out_date;
    private String facility_transferred_to;

    public ClientStatus(String client_status_uuid, String reporting_facility, String client_uuid, Date status_date, String client_died, Date client_died_date, String cause_of_death, String client_transferred_out, Date client_transferred_out_date, String facility_transferred_to) {
        this.client_status_uuid = client_status_uuid;
        this.reporting_facility = reporting_facility;
        this.client_uuid = client_uuid;
        this.status_date = status_date;
        this.client_died = client_died;
        this.client_died_date = client_died_date;
        this.cause_of_death = cause_of_death;
        this.client_transferred_out = client_transferred_out;
        this.client_transferred_out_date = client_transferred_out_date;
        this.facility_transferred_to = facility_transferred_to;
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

    public Date getStatus_date() {
        return status_date;
    }

    public void setStatus_date(Date status_date) {
        this.status_date = status_date;
    }

    public String getClient_died() {
        return client_died;
    }

    public void setClient_died(String client_died) {
        this.client_died = client_died;
    }

    public Date getClient_died_date() {
        return client_died_date;
    }

    public void setClient_died_date(Date client_died_date) {
        this.client_died_date = client_died_date;
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

    public Date getClient_transferred_out_date() {
        return client_transferred_out_date;
    }

    public void setClient_transferred_out_date(Date client_transferred_out_date) {
        this.client_transferred_out_date = client_transferred_out_date;
    }

    public String getFacility_transferred_to() {
        return facility_transferred_to;
    }

    public void setFacility_transferred_to(String facility_transferred_to) {
        this.facility_transferred_to = facility_transferred_to;
    }
}

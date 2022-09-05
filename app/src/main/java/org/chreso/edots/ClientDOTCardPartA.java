package org.chreso.edots;

import java.sql.Date;

public class ClientDOTCardPartA {
    private String dot_card_uuid;
    private String client_uuid;
    private String type_of_tuberculosis;
    private String treatment_outcome;
    private Date date_of_decision;
    private String type_of_regimen;
    private String disease_site;

    public ClientDOTCardPartA(String dot_card_uuid, String client_uuid, String type_of_tuberculosis, String treatment_outcome, Date date_of_decision, String type_of_regimen, String disease_site) {
        this.dot_card_uuid = dot_card_uuid;
        this.client_uuid = client_uuid;
        this.type_of_tuberculosis = type_of_tuberculosis;
        this.treatment_outcome = treatment_outcome;
        this.date_of_decision = date_of_decision;
        this.type_of_regimen = type_of_regimen;
        this.disease_site = disease_site;
    }


    public String getDot_card_uuid() {
        return dot_card_uuid;
    }

    public void setDot_card_uuid(String dot_card_uuid) {
        this.dot_card_uuid = dot_card_uuid;
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public String getType_of_tuberculosis() {
        return type_of_tuberculosis;
    }

    public void setType_of_tuberculosis(String type_of_tuberculosis) {
        this.type_of_tuberculosis = type_of_tuberculosis;
    }

    public String getTreatment_outcome() {
        return treatment_outcome;
    }

    public void setTreatment_outcome(String treatment_outcome) {
        this.treatment_outcome = treatment_outcome;
    }

    public Date getDate_of_decision() {
        return date_of_decision;
    }

    public void setDate_of_decision(Date date_of_decision) {
        this.date_of_decision = date_of_decision;
    }

    public String getType_of_regimen() {
        return type_of_regimen;
    }

    public void setType_of_regimen(String type_of_regimen) {
        this.type_of_regimen = type_of_regimen;
    }

    public String getDisease_site() {
        return disease_site;
    }

    public void setDisease_site(String disease_site) {
        this.disease_site = disease_site;
    }



}

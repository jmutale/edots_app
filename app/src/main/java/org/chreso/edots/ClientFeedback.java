package org.chreso.edots;

import java.sql.Date;

public class ClientFeedback {
    private String client_feedback_uuid;
    private Date client_feedback_date;
    private String client_uuid;
    private String client_adverse_reaction;
    private String client_concerns;
    private String advice_given_to_client;

    public ClientFeedback(String client_feedback_uuid, Date client_feedback_date, String client_uuid, String client_adverse_reaction, String client_concerns, String advice_given_to_client) {
        this.client_feedback_uuid = client_feedback_uuid;
        this.client_feedback_date = client_feedback_date;
        this.client_uuid = client_uuid;
        this.client_adverse_reaction = client_adverse_reaction;
        this.client_concerns = client_concerns;
        this.advice_given_to_client = advice_given_to_client;
    }

    public String getClient_feedback_uuid() {
        return client_feedback_uuid;
    }

    public void setClient_feedback_uuid(String client_feedback_uuid) {
        this.client_feedback_uuid = client_feedback_uuid;
    }

    public Date getClient_feedback_date() {
        return client_feedback_date;
    }

    public void setClient_feedback_date(Date client_feedback_date) {
        this.client_feedback_date = client_feedback_date;
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public String getClient_adverse_reaction() {
        return client_adverse_reaction;
    }

    public void setClient_adverse_reaction(String client_adverse_reaction) {
        this.client_adverse_reaction = client_adverse_reaction;
    }

    public String getClient_concerns() {
        return client_concerns;
    }

    public void setClient_concerns(String client_concerns) {
        this.client_concerns = client_concerns;
    }

    public String getAdvice_given_to_client() {
        return advice_given_to_client;
    }

    public void setAdvice_given_to_client(String advice_given_to_client) {
        this.advice_given_to_client = advice_given_to_client;
    }
}

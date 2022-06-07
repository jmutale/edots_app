package org.chreso.edots;

import java.sql.Date;

public class ClientEDOTSurvey {
    private String edot_survey_uuid;
    private Date edot_survey_date;
    private String client_uuid;
    private String is_patient_satisfied_with_edot;
    private String reasons_satisfied_or_not;
    private String would_client_like_to_continue_with_edot;
    private String reasons_client_will_continue_with_edot_or_not;

    public ClientEDOTSurvey(String edot_survey_uuid, Date edot_survey_date, String client_uuid, String is_patient_satisfied_with_edot, String reasons_satisfied_or_not, String would_client_like_to_continue_with_edot, String reasons_client_will_continue_with_edot_or_not) {
        this.edot_survey_uuid = edot_survey_uuid;
        this.edot_survey_date = edot_survey_date;
        this.client_uuid = client_uuid;
        this.is_patient_satisfied_with_edot = is_patient_satisfied_with_edot;
        this.reasons_satisfied_or_not = reasons_satisfied_or_not;
        this.would_client_like_to_continue_with_edot = would_client_like_to_continue_with_edot;
        this.reasons_client_will_continue_with_edot_or_not = reasons_client_will_continue_with_edot_or_not;
    }

    public String getEdot_survey_uuid() {
        return edot_survey_uuid;
    }

    public void setEdot_survey_uuid(String edot_survey_uuid) {
        this.edot_survey_uuid = edot_survey_uuid;
    }

    public Date getEdot_survey_date() {
        return edot_survey_date;
    }

    public void setEdot_survey_date(Date edot_survey_date) {
        this.edot_survey_date = edot_survey_date;
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public String getIs_patient_satisfied_with_edot() {
        return is_patient_satisfied_with_edot;
    }

    public void setIs_patient_satisfied_with_edot(String is_patient_satisfied_with_edot) {
        this.is_patient_satisfied_with_edot = is_patient_satisfied_with_edot;
    }

    public String getReasons_satisfied_or_not() {
        return reasons_satisfied_or_not;
    }

    public void setReasons_satisfied_or_not(String reasons_satisfied_or_not) {
        this.reasons_satisfied_or_not = reasons_satisfied_or_not;
    }

    public String getWould_client_like_to_continue_with_edot() {
        return would_client_like_to_continue_with_edot;
    }

    public void setWould_client_like_to_continue_with_edot(String would_client_like_to_continue_with_edot) {
        this.would_client_like_to_continue_with_edot = would_client_like_to_continue_with_edot;
    }

    public String getReasons_client_will_continue_with_edot_or_not() {
        return reasons_client_will_continue_with_edot_or_not;
    }

    public void setReasons_client_will_continue_with_edot_or_not(String reasons_client_will_continue_with_edot_or_not) {
        this.reasons_client_will_continue_with_edot_or_not = reasons_client_will_continue_with_edot_or_not;
    }
}

package org.chreso.edots;

import java.sql.Date;

public class ClientDOTCardPartBEvent {
    private String dot_card_uuid;
    private String client_uuid;
    private String initial_phase_start_date;
    private String observer;
    private String dot_plan;
    private String start_weight;
    private String dot_plan_initiation;
    private String continuation_phase_start_date;
    private String dot_plan_continuation_month_1;
    private String dot_plan_continuation_month_2;
    private String dot_plan_continuation_month_3;
    private String dot_plan_continuation_month_4;

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

    public String getInitial_phase_start_date() {
        return initial_phase_start_date;
    }

    public void setInitial_phase_start_date(Date initial_phase_start_date) {
        this.initial_phase_start_date = Utils.getFormattedDate(initial_phase_start_date);
    }

    public String getObserver() {
        return observer;
    }

    public void setObserver(String observer) {
        this.observer = observer;
    }

    public String getDot_plan() {
        return dot_plan;
    }

    public void setDot_plan(String dot_plan) {
        this.dot_plan = dot_plan;
    }

    public String getStart_weight() {
        return start_weight;
    }

    public void setStart_weight(String start_weight) {
        this.start_weight = start_weight;
    }

    public String getDot_plan_initiation() {
        return dot_plan_initiation;
    }

    public void setDot_plan_initiation(String dot_plan_initiation) {
        this.dot_plan_initiation = dot_plan_initiation;
    }

    public String getContinuation_phase_start_date() {
        return continuation_phase_start_date;
    }

    public void setContinuation_phase_start_date(Date continuation_phase_start_date) {
        this.continuation_phase_start_date = Utils.getFormattedDate(continuation_phase_start_date);
    }

    public String getDot_plan_continuation_month_1() {
        return dot_plan_continuation_month_1;
    }

    public void setDot_plan_continuation_month_1(String dot_plan_continuation_month_1) {
        this.dot_plan_continuation_month_1 = dot_plan_continuation_month_1;
    }

    public String getDot_plan_continuation_month_2() {
        return dot_plan_continuation_month_2;
    }

    public void setDot_plan_continuation_month_2(String dot_plan_continuation_month_2) {
        this.dot_plan_continuation_month_2 = dot_plan_continuation_month_2;
    }

    public String getDot_plan_continuation_month_3() {
        return dot_plan_continuation_month_3;
    }

    public void setDot_plan_continuation_month_3(String dot_plan_continuation_month_3) {
        this.dot_plan_continuation_month_3 = dot_plan_continuation_month_3;
    }

    public String getDot_plan_continuation_month_4() {
        return dot_plan_continuation_month_4;
    }

    public void setDot_plan_continuation_month_4(String dot_plan_continuation_month_4) {
        this.dot_plan_continuation_month_4 = dot_plan_continuation_month_4;
    }



}

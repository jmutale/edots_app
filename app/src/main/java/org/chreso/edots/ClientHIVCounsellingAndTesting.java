package org.chreso.edots;

public class ClientHIVCounsellingAndTesting {
    private String hiv_counselling_and_testing_uuid;
    private String client_uuid;
    private String accepted_testing;
    private String if_no_accepted_during_intensive_phase;
    private String result_intensive;
    private String place_of_test;
    private String date_of_test;
    private String results;
    private String rest_test_counselling;
    private String if_no_accepted_during_continuation_phase;
    private String result_continuation;

    public ClientHIVCounsellingAndTesting(String hiv_counselling_and_testing_uuid, String client_uuid, String accepted_testing, String if_no_accepted_during_intensive_phase, String result_intensive, String place_of_test, String date_of_test, String results, String rest_test_counselling, String if_no_accepted_during_continuation_phase, String result_continuation) {
        this.hiv_counselling_and_testing_uuid = hiv_counselling_and_testing_uuid;
        this.client_uuid = client_uuid;
        this.accepted_testing = accepted_testing;
        this.if_no_accepted_during_intensive_phase = if_no_accepted_during_intensive_phase;
        this.result_intensive = result_intensive;
        this.place_of_test = place_of_test;
        this.date_of_test = date_of_test;
        this.results = results;
        this.rest_test_counselling = rest_test_counselling;
        this.if_no_accepted_during_continuation_phase = if_no_accepted_during_continuation_phase;
        this.result_continuation = result_continuation;
    }

    public String getHiv_counselling_and_testing_uuid() {
        return hiv_counselling_and_testing_uuid;
    }

    public void setHiv_counselling_and_testing_uuid(String hiv_counselling_and_testing_uuid) {
        this.hiv_counselling_and_testing_uuid = hiv_counselling_and_testing_uuid;
    }

    public String getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(String client_uuid) {
        this.client_uuid = client_uuid;
    }

    public String getAccepted_testing() {
        return accepted_testing;
    }

    public void setAccepted_testing(String accepted_testing) {
        this.accepted_testing = accepted_testing;
    }

    public String getIf_no_accepted_during_intensive_phase() {
        return if_no_accepted_during_intensive_phase;
    }

    public void setIf_no_accepted_during_intensive_phase(String if_no_accepted_during_intensive_phase) {
        this.if_no_accepted_during_intensive_phase = if_no_accepted_during_intensive_phase;
    }

    public String getResult_intensive() {
        return result_intensive;
    }

    public void setResult_intensive(String result_intensive) {
        this.result_intensive = result_intensive;
    }

    public String getPlace_of_test() {
        return place_of_test;
    }

    public void setPlace_of_test(String place_of_test) {
        this.place_of_test = place_of_test;
    }

    public String getDate_of_test() {
        return date_of_test;
    }

    public void setDate_of_test(String date_of_test) {
        this.date_of_test = date_of_test;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getRest_test_counselling() {
        return rest_test_counselling;
    }

    public void setRest_test_counselling(String rest_test_counselling) {
        this.rest_test_counselling = rest_test_counselling;
    }

    public String getIf_no_accepted_during_continuation_phase() {
        return if_no_accepted_during_continuation_phase;
    }

    public void setIf_no_accepted_during_continuation_phase(String if_no_accepted_during_continuation_phase) {
        this.if_no_accepted_during_continuation_phase = if_no_accepted_during_continuation_phase;
    }

    public String getResult_continuation() {
        return result_continuation;
    }

    public void setResult_continuation(String result_continuation) {
        this.result_continuation = result_continuation;
    }
}

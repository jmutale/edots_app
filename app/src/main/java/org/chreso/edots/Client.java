package org.chreso.edots;

public class Client {


    private String uuid;
    private String chreso_id;
    private String nrc_number;
    private String art_number;
    private String first_name;
    private String last_name;
    private String date_of_birth;
    private String how_many_individuals_are_in_the_same_household;
    private String are_individuals_in_household_on_ipt;
    private String why_are_individuals_not_on_ipt;
    private String sex;
    private String mobile_phone_number;
    private String facility;
    private Boolean is_client_on_server;

    public Client(String uuid, String nrc_number,String chreso_id, String art_number, String first_name, String last_name, String date_of_birth, String how_many_individuals_are_in_the_same_household, String are_individuals_in_household_on_ipt, String why_are_individuals_not_on_ipt, String sex, String mobile_phone_number, String facility_id, Boolean is_client_on_server) {
        this.uuid = uuid;
        this.chreso_id = chreso_id;
        this.nrc_number = nrc_number;
        this.art_number = art_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.how_many_individuals_are_in_the_same_household = how_many_individuals_are_in_the_same_household;
        this.are_individuals_in_household_on_ipt = are_individuals_in_household_on_ipt;
        this.why_are_individuals_not_on_ipt = why_are_individuals_not_on_ipt;
        this.sex = sex;
        this.mobile_phone_number = mobile_phone_number;
        this.facility = facility_id;
        this.is_client_on_server = is_client_on_server;
    }

    public Client(String uuid, String nrc_number,String chreso_id, String art_number, String first_name, String last_name, String date_of_birth, String how_many_individuals_are_in_the_same_household, String are_individuals_in_household_on_ipt, String why_are_individuals_not_on_ipt, String sex, String mobile_phone_number, String facility_id) {
        this.uuid = uuid;
        this.chreso_id = chreso_id;
        this.nrc_number = nrc_number;
        this.art_number = art_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.how_many_individuals_are_in_the_same_household = how_many_individuals_are_in_the_same_household;
        this.are_individuals_in_household_on_ipt = are_individuals_in_household_on_ipt;
        this.why_are_individuals_not_on_ipt = why_are_individuals_not_on_ipt;
        this.sex = sex;
        this.mobile_phone_number = mobile_phone_number;
        this.facility = facility_id;

    }


    public String getHow_many_individuals_are_in_the_same_household() {
        return how_many_individuals_are_in_the_same_household;
    }

    public void setHow_many_individuals_are_in_the_same_household(String how_many_individuals_are_in_the_same_household) {
        this.how_many_individuals_are_in_the_same_household = how_many_individuals_are_in_the_same_household;
    }

    public String getAre_individuals_in_household_on_ipt() {
        return are_individuals_in_household_on_ipt;
    }

    public void setAre_individuals_in_household_on_ipt(String are_individuals_in_household_on_ipt) {
        this.are_individuals_in_household_on_ipt = are_individuals_in_household_on_ipt;
    }

    public String getWhy_are_individuals_not_on_ipt() {
        return why_are_individuals_not_on_ipt;
    }

    public void setWhy_are_individuals_not_on_ipt(String why_are_individuals_not_on_ipt) {
        this.why_are_individuals_not_on_ipt = why_are_individuals_not_on_ipt;
    }


    public Boolean getIs_client_on_server() {
        return is_client_on_server;
    }

    public void setIs_client_on_server(Boolean is_client_on_server) {
        this.is_client_on_server = is_client_on_server;
    }



    public String getChreso_id(){return  chreso_id;}

    public void setChreso_id(String chreso_id){this.chreso_id=chreso_id;}

    public String getNrc_number() {
        return nrc_number;
    }

    public void setNrc_number(String nrc_number) {
        this.nrc_number = nrc_number;
    }

    public String getArt_number() {
        return art_number;
    }

    public void setArt_number(String art_number) {
        this.art_number = art_number;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getMobile_phone_number() {
        return mobile_phone_number;
    }

    public void setMobile_phone_number(String mobile_phone_number) {
        this.mobile_phone_number = mobile_phone_number;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

}

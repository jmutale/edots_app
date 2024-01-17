package org.chreso.edots;

import java.sql.Date;

public class ClientEvent {


    private String uuid;
    private String chreso_id;
    private String tb_id_number;
    private String address;
    private String type_of_client;
    private String type_of_client_other;
    private String nrc_number;
    private String art_number;
    private String first_name;
    private String last_name;
    private String age;
    private String date_of_birth;
    private String registration_date;
    private String how_many_individuals_are_in_the_same_household;
    private String are_individuals_in_household_on_ipt;
    private String why_are_individuals_not_on_ipt;
    private String sex;
    private String mobile_phone_number;
    private String district;
    private String facility;


    public ClientEvent(String uuid, String nrc_number, String chreso_id, String tb_id_number, String address, String type_of_client, String type_of_client_other, String art_number, String first_name, String last_name, String age, String date_of_birth, String registration_date, String how_many_individuals_are_in_the_same_household, String are_individuals_in_household_on_ipt, String why_are_individuals_not_on_ipt, String sex, String mobile_phone_number, String district, String facility_id) {
        this.uuid = uuid;
        this.chreso_id = chreso_id;
        this.tb_id_number = tb_id_number;
        this.address = address;
        this.type_of_client = type_of_client;
        this.type_of_client_other = type_of_client_other;
        this.nrc_number = nrc_number;
        this.art_number = art_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.date_of_birth = date_of_birth;
        this.registration_date = registration_date;
        this.how_many_individuals_are_in_the_same_household = how_many_individuals_are_in_the_same_household;
        this.are_individuals_in_household_on_ipt = are_individuals_in_household_on_ipt;
        this.why_are_individuals_not_on_ipt = why_are_individuals_not_on_ipt;
        this.sex = sex;
        this.mobile_phone_number = mobile_phone_number;
        this.district = district;
        this.facility = facility_id;

    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTb_id_number() {
        return tb_id_number;
    }

    public void setTb_id_number(String tb_id_number) {
        this.tb_id_number = tb_id_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType_of_client() {
        return type_of_client;
    }

    public void setType_of_client(String type_of_client) {
        this.type_of_client = type_of_client;
    }

    public String getType_of_client_other() {
        return type_of_client_other;
    }

    public void setType_of_client_other(String type_of_client_other) {
        this.type_of_client_other = type_of_client_other;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = Utils.getFormattedDate(registration_date);
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

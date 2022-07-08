package org.chreso.edots;

public class ClientEvent {


    private String uuid;
    private String chreso_id;
    private String nrc_number;
    private String art_number;
    private String first_name;
    private String last_name;
    private String date_of_birth;
    private String sex;
    private String mobile_phone_number;
    private String facility;


    public ClientEvent(String uuid, String nrc_number, String chreso_id, String art_number, String first_name, String last_name, String date_of_birth, String sex, String mobile_phone_number, String facility_id) {
        this.uuid = uuid;
        this.chreso_id = chreso_id;
        this.nrc_number = nrc_number;
        this.art_number = art_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
        this.mobile_phone_number = mobile_phone_number;
        this.facility = facility_id;

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

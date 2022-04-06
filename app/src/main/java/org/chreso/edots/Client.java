package org.chreso.edots;

import java.sql.Date;

public class Client {
    private String first_name;
    private String last_name;
    private Date date_of_birth;
    private String sex;
    private String mobile_phone_number;

    public Client(String first_name, String last_name, Date date_of_birth,String sex, String mobile_phone_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
        this.mobile_phone_number = mobile_phone_number;
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

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getMobile_phone_number() {
        return mobile_phone_number;
    }

    public void setMobile_phone_number(String mobile_phone_number) {
        this.mobile_phone_number = mobile_phone_number;
    }
}

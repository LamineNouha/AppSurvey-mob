package com.vayetek.vayesurvey.models;

/**
 * Created by nouha on 9/30/17.
 */

public class Citizen {

    private String sex;
    private  int age;
    private String socLevel;
    private String educLevel;
    private String profession;
    private String region;
    private String locality;

    public Citizen(String sex, int age, String socLevel, String educLevel, String profession, String region, String locality) {
        this.sex = sex;
        this.age = age;
        this.socLevel = socLevel;
        this.educLevel = educLevel;
        this.profession = profession;
        this.region = region;
        this.locality = locality;
    }

    public Citizen() {

    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSocLevel() {
        return socLevel;
    }

    public void setSocLevel(String socLevel) {
        this.socLevel = socLevel;
    }

    public String getEducLevel() {
        return educLevel;
    }

    public void setEducLevel(String educLevel) {
        this.educLevel = educLevel;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}

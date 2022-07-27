package com.example.easycare.dataclasses;

public class Doctors {
    private int id;
    private String doc_name;
    private String doc_address;
    private int doc_mobile;
    private String doc_speciality;
    private String doc_experience;
    private String doc_degree;
    private String doc_location;
    private Boolean is_male;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_address() {
        return doc_address;
    }

    public void setDoc_address(String doc_address) {
        this.doc_address = doc_address;
    }

    public int getDoc_mobile() {
        return doc_mobile;
    }

    public void setDoc_mobile(int doc_mobile) {
        this.doc_mobile = doc_mobile;
    }

    public String getDoc_speciality() {
        return doc_speciality;
    }

    public void setDoc_speciality(String doc_speciality) {
        this.doc_speciality = doc_speciality;
    }

    public String getDoc_experience() {
        return doc_experience;
    }

    public void setDoc_experience(String doc_experience) {
        this.doc_experience = doc_experience;
    }

    public String getDoc_degree() {
        return doc_degree;
    }

    public void setDoc_degree(String doc_degree) {
        this.doc_degree = doc_degree;
    }

    public String getDoc_location() {
        return doc_location;
    }

    public void setDoc_location(String doc_location) {
        this.doc_location = doc_location;
    }

    public Boolean getIs_male() {
        return is_male;
    }

    public void setIs_male(Boolean is_male) {
        this.is_male = is_male;
    }
}

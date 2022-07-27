package com.example.easycare.dataclasses;

public class Medicines {
    private int id;
    private String med_name;
    private String med_brand;
    private String med_disaese;
    private String med_dose;
    private String is_syrup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getMed_brand() {
        return med_brand;
    }

    public void setMed_brand(String med_brand) {
        this.med_brand = med_brand;
    }

    public String getMed_disaese() {
        return med_disaese;
    }

    public void setMed_disaese(String med_disaese) {
        this.med_disaese = med_disaese;
    }

    public String getMed_dose() {
        return med_dose;
    }

    public void setMed_dose(String med_dose) {
        this.med_dose = med_dose;
    }

    public String getIs_syrup() {
        return is_syrup;
    }

    public void setIs_syrup(String is_syrup) {
        this.is_syrup = is_syrup;
    }
}

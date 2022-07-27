package com.example.easycare.dataclasses;

import java.util.List;

public class Profile {
    boolean is_doctor;
    String tag;
    String photo_url;
    List<String> user_detail;

    public boolean isIs_doctor() {
        return is_doctor;
    }

    public void setIs_doctor(boolean is_doctor) {
        this.is_doctor = is_doctor;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public List<String> getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(List<String> user_detail) {
        this.user_detail = user_detail;
    }
}

package com.example.gosnow_glencoe;

import android.widget.ImageView;

public class Friends {

    private String name, status, profileImage;

    public Friends() {
    }

    public Friends(String name, String status, String profileImage) {
        this.name = name;
        this.status = status;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
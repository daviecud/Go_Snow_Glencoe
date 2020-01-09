package com.example.gosnow_glencoe;

public class BusinessDetails {

    private int id;
    private String bus_name, bus_address, bus_tel, bus_web, bus_summary;
    private int bus_image;

    public BusinessDetails() {
    }

    public BusinessDetails(int id, String bus_name, String bus_address, String bus_tel, String bus_web, String bus_summary, int bus_image) {
        this.id = id;
        this.bus_name = bus_name;
        this.bus_address = bus_address;
        this.bus_tel = bus_tel;
        this.bus_web = bus_web;
        this.bus_summary = bus_summary;
        this.bus_image = bus_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getBus_address() {
        return bus_address;
    }

    public void setBus_address(String bus_address) {
        this.bus_address = bus_address;
    }

    public String getBus_tel() {
        return bus_tel;
    }

    public void setBus_tel(String bus_tel) {
        this.bus_tel = bus_tel;
    }

    public String getBus_web() {
        return bus_web;
    }

    public void setBus_web(String bus_web) {
        this.bus_web = bus_web;
    }

    public String getBus_summary() {
        return bus_summary;
    }

    public void setBus_summary(String bus_summary) {
        this.bus_summary = bus_summary;
    }

    public int getBus_image() {
        return bus_image;
    }

    public void setBus_image(int bus_image) {
        this.bus_image = bus_image;
    }
}


package com.example.hasanmdmehadi.jamattimee;


public class MasjidModel {
    String masjid_name;
    String lat;
    String lon;
    String district = "";
    String masjid_id;
    String fajr;
    String zahor;
    String asr;
    String magrib;
    String isha;


    public String getMasjid_name() {
        return masjid_name;
    }

    public void setMasjid_name(String masjid_name) {
        this.masjid_name = masjid_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMasjid_id() {
        return masjid_id;
    }

    public void setMasjid_id(String masjid_id) {
        this.masjid_id = masjid_id;
    }

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getZahor() {
        return zahor;
    }

    public void setZahor(String zahor) {
        this.zahor = zahor;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMagrib() {
        return magrib;
    }

    public void setMagrib(String magrib) {
        this.magrib = magrib;
    }

    public String getIsha() {
        return isha;
    }

    public void setIsha(String isha) {
        this.isha = isha;
    }
}

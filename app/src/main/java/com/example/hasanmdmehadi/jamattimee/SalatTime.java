package com.example.hasanmdmehadi.jamattimee;

public class SalatTime {
    private String salatname;
    private String salattime;

    public SalatTime(String salatname, String salattime){
        this.salatname = salatname;
        this.salattime = salattime;
    }

    public String getSalatname() {
        return salatname;
    }

    public String getSalattime() {
        return salattime;
    }

    public void setSalatname(String salatname) {
        this.salatname = salatname;
    }

    public void setSalattime(String salattime) {
        this.salattime = salattime;
    }
}

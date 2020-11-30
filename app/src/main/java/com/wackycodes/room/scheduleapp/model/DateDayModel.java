package com.wackycodes.room.scheduleapp.model;

/**
 * Created by Shailendra (WackyCodes) on 30/11/2020 22:50
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */

public class DateDayModel {
    private int date;
    private String day;

    public DateDayModel() {
    }

    public DateDayModel(int date, String day) {
        this.date = date;
        this.day = day;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

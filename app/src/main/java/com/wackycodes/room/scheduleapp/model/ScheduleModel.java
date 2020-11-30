package com.wackycodes.room.scheduleapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.wackycodes.room.scheduleapp.helper.DateConverter;

import java.util.Date;

/**
 * Created by Shailendra (WackyCodes) on 30/11/2020 20:57
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */


@Entity(tableName = "schedule_model")
public class ScheduleModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "schedule_data")
    private String scheduleData;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo ( name = "stringDate")
    private String stringDate;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "time")
    private Date time;

    @ColumnInfo(name = "completed")
    private boolean completed;


    public ScheduleModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScheduleData() {
        return scheduleData;
    }

    public void setScheduleData(String scheduleData) {
        this.scheduleData = scheduleData;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

package com.wackycodes.room.scheduleapp.helper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wackycodes.room.scheduleapp.model.ScheduleModel;

/**
 * Created by Shailendra (WackyCodes) on 30/11/2020 19:21
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */


@Database(entities = {ScheduleModel.class},version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {


    public abstract MyDaoInterface myDaoInterface();
    public static MyRoomDatabase mInstance;

    public static MyRoomDatabase getInstance(Context context){
        if (mInstance == null){
            mInstance = Room.databaseBuilder(context,MyRoomDatabase.class,"MyDatabaseName")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return mInstance;
    }
}

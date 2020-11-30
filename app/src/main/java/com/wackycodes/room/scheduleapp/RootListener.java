package com.wackycodes.room.scheduleapp;

import com.wackycodes.room.scheduleapp.model.ScheduleModel;


/**
 * Created by Shailendra (WackyCodes) on 30/11/2020 19:51
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public interface RootListener {

    void showToast( String msg );

    void showDialog();

    void dismissDialog();

    interface WeekActionListener{
        void onWeekClicked(int weekNo);
        void onDaysClicked( int date );
    }

    interface ScheduleActionListener{
        void onAddNewSchedule( ScheduleModel model);
    }

}

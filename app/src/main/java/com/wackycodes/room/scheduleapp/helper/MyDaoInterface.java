package com.wackycodes.room.scheduleapp.helper;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wackycodes.room.scheduleapp.model.ScheduleModel;

import java.util.List;

/**
 * Created by Shailendra (WackyCodes) on 30/11/2020 19:18
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */

@Dao
public interface MyDaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ScheduleModel scheduleModel);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ScheduleModel scheduleModel);
    @Delete()
    void delete(ScheduleModel scheduleModel);
    @Query("SELECT * FROM schedule_model")
    List <ScheduleModel> collectItems();

    @Query( "SELECT * FROM schedule_model where stringDate = :tDate" )
    List <ScheduleModel> getSortedItems(String tDate);

    @Query( "UPDATE schedule_model SET completed = :isComplete WHERE id = :id" )
    void updateCompleted(boolean isComplete, int id);

}

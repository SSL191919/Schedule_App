package com.wackycodes.room.scheduleapp.helper;

import android.util.Log;

import com.wackycodes.room.scheduleapp.MainFragment;
import com.wackycodes.room.scheduleapp.model.DateDayModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shailendra (WackyCodes) on 30/11/2020 22:03
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class StaticMethods {

    public static String[] dayName = new String[]{"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

    public static String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                        "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static boolean isMatchedDate(Date date1, Date date2){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        String str1 = format.format( date1 ).toUpperCase();
        String str2 = format.format( date2 ).toUpperCase();
        Log.e( "CHECK", str1 + " : "+ str2 + str1.equals( str2 ) );
        return str1.equals( str2 );
    }

    public static boolean isLeapYear(int year){
        boolean leap = false;
        if (year % 4 == 0) {
            if (year % 100 == 0) { // If century year
                if (year % 400 == 0){
                    leap = true;
                }
            }
            else // If year not century
                leap = true;
        }
//        else{
//            leap = false;
//        }
        return leap;
    }

    public static int getMaxDayOfMonth(int month, int year){
        month = month +1;
        int max = 30;
        switch (month){
            case 2:
                if (isLeapYear( year )){
                    max = 29;
                }else {
                    max = 28;
                }
                break;

            case 4:
            case 6:
            case 9:
            case 11:
                max = 30;
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                max = 31;
                break;
        }
        return max;
    }

    // Month List...
    public static List<List<DateDayModel>> dateDaysList = new ArrayList <>();

    public static String getDay( int date, int month, int year){
        SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.ENGLISH);
        return format.format( getDate(date, month, year)).toUpperCase();
    }

    public static String getDateStringForm(  int date, int month, int year, String strFormat ){
        SimpleDateFormat format = new SimpleDateFormat( strFormat, Locale.ENGLISH);
        return format.format( getDate(date, month, year)).toUpperCase();
    }

    public static Date getDate( int date, int month, int year){
        final Calendar c = Calendar.getInstance( Locale.ENGLISH);
        c.set( Calendar.YEAR, year);
        c.set( Calendar.MONTH, month);
        c.set( Calendar.DAY_OF_MONTH, date);
        return c.getTime();
    }

    public static List<List<DateDayModel>> getDateDaysList(String crrDay, int maxDate){
//        String crrDay = "SUN";
        int crrDate = 1;
//        int maxDate = 30;
        List<List<DateDayModel>> dateDaysList = new ArrayList <>();

        // Index of Current Day...
        int tempIndex = 0;
        for (String d : dayName){
            if ( d.equals( crrDay )){
                break;
            }else{
                tempIndex++;
            }
        }


        // Set List Data...
        for (int i = 0;(crrDate <= maxDate); i++){ // No of week
            dateDaysList.add( new ArrayList <>() ); // Add a week index...

            for ( int j = tempIndex; (crrDate <= maxDate) && (j < dayName.length); j++, tempIndex++){

                DateDayModel dateDayModel = new DateDayModel();
                dateDayModel.setDate( crrDate );
                dateDayModel.setDay( dayName[j] );

                dateDaysList.get( i ).add( dateDayModel );

                if (MainFragment.currentDate == crrDate){
                    MainFragment.currentWeek = i;
                }
                // Change Date and day...
                crrDate = crrDate +1;
            }

            // Change TempIndex
            if (tempIndex >= dayName.length){
                tempIndex = 0;
            }

        }

        return dateDaysList;
    }



}

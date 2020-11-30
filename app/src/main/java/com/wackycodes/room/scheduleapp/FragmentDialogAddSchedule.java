package com.wackycodes.room.scheduleapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.wackycodes.room.scheduleapp.helper.MyRoomDatabase;
import com.wackycodes.room.scheduleapp.model.ScheduleModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FragmentDialogAddSchedule extends DialogFragment {

    public FragmentDialogAddSchedule(RootListener rootListener,RootListener.ScheduleActionListener scheduleActionListener, Context context) {
        // Required empty public constructor
        this.rootListener = rootListener;
        this.scheduleActionListener = scheduleActionListener;
        this.context = context;
    }

    private Context context;
    private RootListener rootListener;
    private RootListener.ScheduleActionListener scheduleActionListener;

    final ScheduleModel scheduleModel = new ScheduleModel();
    private MyRoomDatabase myRoomDatabase;

    private Dialog scheduleDialog;

    private TextView tvDateSelector;
    private EditText editTextSchedule;
    private TextView tvAddScheduleBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_dialog_add_schedule, container, false );

        tvDateSelector = view.findViewById( R.id.text_schedule_date_selector );
        editTextSchedule = view.findViewById( R.id.edit_text_schedule );
        tvAddScheduleBtn = view.findViewById( R.id.text_add_schedule_btn );

        // Dialog...
        scheduleDialog = getDialog();
        scheduleDialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        scheduleDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
//        getDialog().setCancelable( true );

        myRoomDatabase = MyRoomDatabase.getInstance( context );

        onButtonAction();
        return view;
    }

    private void onButtonAction(){
        tvDateSelector.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        } );


        tvAddScheduleBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootListener.showDialog();
                if (isCorrectInfo()){
                    saveSchedule( editTextSchedule.getText().toString() );
                }else{
                  rootListener.dismissDialog();
                }
            }
        } );

    }

    private boolean isCorrectInfo(){
        String schedule = editTextSchedule.getText().toString();
        if (TextUtils.isEmpty( schedule )){
            editTextSchedule.setError( "Required!" );
            return false;
        }

        if (scheduleModel.getDate() == null){
            rootListener.showToast( "Please select Date!" );
           return false;
        }
        return true;
    }

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get( Calendar.YEAR);
        int month = calendar.get( Calendar.MONTH);
        int day = calendar.get( Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog( context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final Calendar c = Calendar.getInstance( Locale.ENGLISH);
                c.set( Calendar.YEAR, year);
                c.set( Calendar.MONTH, month);
                c.set( Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date selectedData = c.getTime();
                final String strDate = format.format(selectedData);
                tvDateSelector.setText(strDate);
                scheduleModel.setDate( selectedData );
                scheduleModel.setStringDate( strDate );
            }
        }, year, month, day);
        datePickerDialog.show();

    }

    private void saveSchedule(String data){
        scheduleModel.setScheduleData( data );
        scheduleModel.setCompleted(false);
        myRoomDatabase.myDaoInterface().insert(scheduleModel);
        scheduleDialog.dismiss();
        scheduleActionListener.onAddNewSchedule( scheduleModel );
        rootListener.dismissDialog();
    }


}
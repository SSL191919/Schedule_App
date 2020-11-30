package com.wackycodes.room.scheduleapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wackycodes.room.scheduleapp.RootListener.ScheduleActionListener;
import com.wackycodes.room.scheduleapp.adapter.DaysAdapter;
import com.wackycodes.room.scheduleapp.adapter.SchedulerAdapter;
import com.wackycodes.room.scheduleapp.adapter.WeeksAdaptor;
import com.wackycodes.room.scheduleapp.helper.MyRoomDatabase;
import com.wackycodes.room.scheduleapp.helper.StaticMethods;
import com.wackycodes.room.scheduleapp.model.DateDayModel;
import com.wackycodes.room.scheduleapp.model.ScheduleModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.wackycodes.room.scheduleapp.helper.StaticMethods.dateDaysList;
import static com.wackycodes.room.scheduleapp.helper.StaticMethods.isMatchedDate;

public class MainFragment extends Fragment implements RootListener, RootListener.WeekActionListener, ScheduleActionListener {


    public MainFragment() {
        // Required empty public constructor
    }

    public ProgressDialog dialog;

    //------------
    public static int currentDate;
    public static int currentMonth;
    public static int currentYear;
    public static String currentDayName;
    public static String currentMonthName;

    public static int currentWeek = 0;

    // ---
    private List<DateDayModel> currentWeekList = new ArrayList <>();
    private List<String> weekList = new ArrayList <>();

    private List<ScheduleModel> scheduleModelList = new ArrayList <>();
    private MyRoomDatabase myRoomDatabase;

    private WeeksAdaptor weeksAdaptor;
    private DaysAdapter daysAdapter;
    public static SchedulerAdapter schedulerAdapter;
    //-------------

    // Variables...

    private TextView textMonthYear;
    private ImageView btnBackYear;
    private ImageView btnNextYear;

    private RecyclerView recyclerViewWeeks;
    private RecyclerView recyclerViewDays;

    private RecyclerView recyclerViewSchedule;

    private FloatingActionButton floatBtnAddSchedule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_main, container, false );
        dialog = new ProgressDialog( getContext() );
        dialog.setTitle( "Please wait..." );
        dialog.setCancelable( false );

        textMonthYear = view.findViewById( R.id.text_month_year );
        btnBackYear = view.findViewById( R.id.iv_back_year_btn );
        btnNextYear = view.findViewById( R.id.iv_next_year_btn );

        recyclerViewWeeks = view.findViewById( R.id.recycler_week );
        recyclerViewDays = view.findViewById( R.id.recycler_days );
        recyclerViewSchedule = view.findViewById( R.id.recycler_schedule );

        floatBtnAddSchedule = view.findViewById( R.id.floating_add_scedule_btn );

        // Set Layout...
        LinearLayoutManager lmWeeks = new LinearLayoutManager( getContext() );
        lmWeeks.setOrientation( RecyclerView.HORIZONTAL );
        recyclerViewWeeks.setLayoutManager( lmWeeks );
        LinearLayoutManager lmDays = new LinearLayoutManager( getContext() );
        lmDays.setOrientation( RecyclerView.HORIZONTAL );
        recyclerViewDays.setLayoutManager( lmDays );
        LinearLayoutManager lmSchedule = new LinearLayoutManager( getContext() );
        lmSchedule.setOrientation( RecyclerView.VERTICAL );
        recyclerViewSchedule.setLayoutManager( lmSchedule );

        setDefaultData();
        onButtonAction();
        // Schedule...
        collectItems();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (schedulerAdapter != null){
            schedulerAdapter.notifyDataSetChanged();
        }else{
            collectItems();
        }
    }

    public void onButtonAction(){
        btnBackYear.setOnClickListener( v -> {
            if (currentMonth > 0){
                currentMonth = currentMonth - 1;
            }else{
                currentYear = currentYear - 1;
                currentMonth = 11;
            }
            currentMonthName = StaticMethods.monthName[currentMonth];

            textMonthYear.setText( currentMonthName + " " + currentYear );
            // Changed the month days...
            assignCurrentMonth();
        } );
        btnNextYear.setOnClickListener( v -> {
            if (currentMonth < 11){
                currentMonth = currentMonth + 1;
            }else{
                currentYear = currentYear + 1;
                currentMonth = 0;
            }
            currentMonthName = StaticMethods.monthName[currentMonth];
            textMonthYear.setText( currentMonthName + " " + currentYear );
            // Changed the month days...
            assignCurrentMonth();
        } );
        // Float Action
        floatBtnAddSchedule.setOnClickListener( v -> {
            showAddScheduleDialog();
        } );
    }

    private void setDefaultData(){
        showDialog();
        weeksAdaptor = new WeeksAdaptor( weekList, this );
        daysAdapter = new DaysAdapter( currentWeekList, this  );
        // Set Adapter...
        recyclerViewWeeks.setAdapter( weeksAdaptor );
        recyclerViewDays.setAdapter( daysAdapter );
        setScheduleAdaptor();

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get( Calendar.YEAR);
        currentMonth = calendar.get( Calendar.MONTH);
        currentDate = calendar.get( Calendar.DAY_OF_MONTH);
        SimpleDateFormat format = new SimpleDateFormat("EEE,MMM", Locale.ENGLISH);
        final String strDate = format.format(calendar.getTime());

        currentDayName = strDate.substring( 0, 3 ).toUpperCase();
        currentMonthName = strDate.substring( 4 );

        myRoomDatabase = MyRoomDatabase.getInstance( getContext() );

        // Default...
        assignCurrentMonth(  );
        // Set Year and Month
        textMonthYear.setText( currentMonthName + " " + currentYear );
        dismissDialog();
    }

    private void assignCurrentMonth( ){
        currentWeek = 0;
        // Assign Month List...
        dateDaysList
                = StaticMethods.getDateDaysList( StaticMethods.getDateStringForm( 1, currentMonth, currentYear, "EEE" ),
                StaticMethods.getMaxDayOfMonth( currentMonth, currentYear ) );
        // Set Week...
        setCurrentWeek();
        // Set Weeks Days..
        setCurrentWeekList();

        // Set The List Data...
        setRecyclerViewSchedule( currentYear, currentMonth, currentDate );
    }

    private void setCurrentWeek(){
        weekList.clear();
        // Assign No. Of Week List...
        for (int i = 0; i < dateDaysList.size(); i++){
            weekList.add( "Week "+ (i+1) );
        }

        // Adapter...
        if (weeksAdaptor == null){
            weeksAdaptor = new WeeksAdaptor( weekList , this );
        }
        weeksAdaptor.notifyDataSetChanged();
    }

    private void setCurrentWeekList( ){
        currentWeekList.clear();
        // Adding All the Week List...
        currentWeekList.addAll( dateDaysList.get( currentWeek ) );
        // Adaptor..
        if (daysAdapter == null){
            daysAdapter = new DaysAdapter( currentWeekList , this );
        }
        daysAdapter.notifyDataSetChanged();
    }

    private void showAddScheduleDialog(){
        FragmentDialogAddSchedule dialogFragment = new FragmentDialogAddSchedule( this, this, getContext());
        FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        dialogFragment.show(ft, "dialog");
    }

    private void collectItems(){
        setRecyclerViewSchedule( currentYear, currentMonth, currentDate);
    }

    private void setRecyclerViewSchedule( int year, int month, int date ){
        scheduleModelList.clear();
        String newDate = StaticMethods.getDateStringForm( date, month, year, "yyyy-MM-dd" );
        scheduleModelList.addAll(  myRoomDatabase.myDaoInterface().getSortedItems( newDate ) );
        if (schedulerAdapter != null){
            schedulerAdapter.notifyDataSetChanged();
        }else{
            setScheduleAdaptor();
        }
    }
    
    private void setScheduleAdaptor( ){
        schedulerAdapter = new SchedulerAdapter( scheduleModelList, myRoomDatabase);
        recyclerViewSchedule.setAdapter( schedulerAdapter );
        schedulerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onWeekClicked(int weekNo) {
        currentWeek = weekNo;
        setCurrentWeekList();
    }

    @Override
    public void onDaysClicked(int date) {
        currentDate = date;
        setRecyclerViewSchedule( currentYear, currentMonth, currentDate );
    }

    @Override
    public void onAddNewSchedule( ScheduleModel model ) {
        if (isMatchedDate( model.getDate(),  StaticMethods.getDate( currentDate, currentMonth, currentYear ) )){
            scheduleModelList.add( model );
        }
        schedulerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText( getActivity(), msg, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void showDialog() {
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }

}
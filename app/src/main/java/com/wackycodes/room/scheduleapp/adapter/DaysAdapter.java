package com.wackycodes.room.scheduleapp.adapter;

import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wackycodes.room.scheduleapp.MainFragment;
import com.wackycodes.room.scheduleapp.R;
import com.wackycodes.room.scheduleapp.RootListener;
import com.wackycodes.room.scheduleapp.model.DateDayModel;

import java.util.List;

/**
 * Created by Shailendra (WackyCodes) on 30/11/2020 20:03
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {

    private int crrChecked;
    private List <DateDayModel> currentWeekList;
    private RootListener.WeekActionListener rootListener;

    public DaysAdapter(List <DateDayModel> currentWeekList, RootListener.WeekActionListener rootListener) {
        this.currentWeekList = currentWeekList;
        this.rootListener = rootListener;
        crrChecked = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_days_recycler_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData( position );
    }

    @Override
    public int getItemCount() {
        return currentWeekList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDay;
        TextView textViewDate;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            textViewDay = itemView.findViewById( R.id.text_day );
            textViewDate = itemView.findViewById( R.id.text_date );
            layout = itemView.findViewById( R.id.layout_days );
        }

        private void setData( int position ){
            int date = currentWeekList.get( position ).getDate();
            textViewDay.setText( currentWeekList.get( position ).getDay() );
            textViewDate.setText( String.valueOf( date ));
            // Check ...
            if (MainFragment.currentDate == date){
                crrChecked = position;
                setBgColor(R.drawable.back_round_primary);
                setTypeFace(true);
            }else{
                setBgColor(R.drawable.back_round_1dp_white);
                setTypeFace(false);
            }

            itemView.setOnClickListener( v -> {
                if (MainFragment.currentDate != date){
                    setBgColor(R.drawable.back_round_primary);
//                    setTypeFace(true);
                    rootListener.onDaysClicked( date );
                    setNotify( position, crrChecked );
                }
            } );

        }

        private void setBgColor(int id){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                layout.setBackground( itemView.getContext().getResources().getDrawable( id ) );
            }
        }

        private void setTypeFace(boolean isChecked){
            if (isChecked){
                textViewDate.setTypeface( textViewDate.getTypeface(), Typeface.BOLD );
                textViewDay.setTypeface( textViewDay.getTypeface(), Typeface.BOLD );
                textViewDate.setTextColor( itemView.getResources().getColor( R.color.colorBlack ) );
            }else{
                textViewDate.setTextColor( itemView.getResources().getColor( R.color.colorRed ) );
                textViewDate.setTypeface( textViewDate.getTypeface(), Typeface.NORMAL );
                textViewDay.setTypeface( textViewDay.getTypeface(), Typeface.NORMAL );
            }
        }

        private void setNotify( int a, int b){
            DaysAdapter.this.notifyItemChanged( a );
            DaysAdapter.this.notifyItemChanged( b );
        }


    }



}

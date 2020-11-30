package com.wackycodes.room.scheduleapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wackycodes.room.scheduleapp.R;
import com.wackycodes.room.scheduleapp.helper.MyRoomDatabase;
import com.wackycodes.room.scheduleapp.model.ScheduleModel;

import java.util.List;

/**
 * Created by Shailendra (WackyCodes) on 30/11/2020 20:00
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */

public class SchedulerAdapter extends RecyclerView.Adapter<SchedulerAdapter.ViewHolder> {

    private List <ScheduleModel> scheduleModelList;
    private MyRoomDatabase myRoomDatabase;

    public SchedulerAdapter(List <ScheduleModel> scheduleModelList, MyRoomDatabase myRoomDatabase) {
        this.scheduleModelList = scheduleModelList;
        this.myRoomDatabase = myRoomDatabase;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_schedule_recycler_item, parent, false );
        return new SchedulerAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData( position );
    }

    @Override
    public int getItemCount() {
        return scheduleModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkIsComplete;
        private TextView textSchedule;
        private ImageButton btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            checkIsComplete = itemView.findViewById( R.id.check_box_complete_schedule );
            textSchedule = itemView.findViewById( R.id.text_schedule_text );
            btnDelete = itemView.findViewById( R.id.image_btn_delete_schedule );
        }

        private void setData(int position){
            ScheduleModel model = scheduleModelList.get( position );
            textSchedule.setText( model.getScheduleData() );
            checkIsComplete.setChecked( model.isCompleted() );
            // delete Action
            btnDelete.setOnClickListener( v -> {
                scheduleModelList.remove( model );
                myRoomDatabase.mInstance.myDaoInterface().delete( model );
                SchedulerAdapter.this.notifyDataSetChanged();
            } );

            checkIsComplete.setOnClickListener( v -> {
                if (checkIsComplete.isChecked()){
                    setState( position, model.getId(), true );
                }else{
                    setState( position, model.getId(), false );
                }
            } );

        }

        private void setState(int position, int id, boolean isChecked){
            scheduleModelList.get( position ).setCompleted( isChecked );
            myRoomDatabase.mInstance.myDaoInterface().updateCompleted(  isChecked, id );
            SchedulerAdapter.this.notifyDataSetChanged();
        }

    }


}

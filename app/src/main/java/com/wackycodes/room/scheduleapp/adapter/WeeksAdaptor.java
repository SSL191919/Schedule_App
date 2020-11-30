package com.wackycodes.room.scheduleapp.adapter;

import android.annotation.SuppressLint;
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

import java.util.List;

/**
 * Created by Shailendra (WackyCodes) on 30/11/2020 17:04
 * ( To Know more, Click : https://linktr.ee/wackycodes )
 */
public class WeeksAdaptor extends RecyclerView.Adapter<WeeksAdaptor.ViewHolder>{

    private int crrChecked;
    private List<String> weekList;
    private RootListener.WeekActionListener rootListener;
    public WeeksAdaptor(List <String> weekList,  RootListener.WeekActionListener rootListener) {
        this.weekList = weekList;
        this.rootListener = rootListener;
        crrChecked = -1;
    }

    @NonNull
    @Override
    public WeeksAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.layout_recycler_week_item, parent, false );
        return new WeeksAdaptor.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull WeeksAdaptor.ViewHolder holder, int position) {
        holder.setData( position );
    }

    @Override
    public int getItemCount() {
        return weekList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textWeek;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            textWeek = itemView.findViewById( R.id.text_week_name );
            layout = itemView.findViewById( R.id.layout_week );
        }

        @SuppressLint("UseCompatLoadingForColorStateLists")
        private void setData(int position){
            textWeek.setText( weekList.get( position ) );

            if (MainFragment.currentWeek == position){
                setBgColor( R.color.colorRed,  R.color.colorWhite );
                crrChecked = position;
            }else{
                setBgColor( R.color.colorWhite,  R.color.colorBlack );
            }

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainFragment.currentWeek != position){
                        setBgColor( R.color.colorRed,  R.color.colorWhite );
                        rootListener.onWeekClicked( position );

                        setNotify( position, crrChecked );
                    }
                }
            } );

        }


        private void setBgColor(int color, int textColor){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                layout.setBackgroundTintList( itemView.getContext().getResources().getColorStateList( color ) );
            }
            textWeek.setTextColor( itemView.getResources().getColor( textColor ) );
        }

        private void setNotify( int a, int b){
            WeeksAdaptor.this.notifyItemChanged( a );
            WeeksAdaptor.this.notifyItemChanged( b );
        }


    }


}

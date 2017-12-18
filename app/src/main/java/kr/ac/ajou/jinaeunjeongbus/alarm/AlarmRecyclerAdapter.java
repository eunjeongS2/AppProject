package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractRecyclerAdapter;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractViewHolder;
import kr.ac.ajou.jinaeunjeongbus.database.DBHelper;


public class AlarmRecyclerAdapter extends AbstractRecyclerAdapter<Alarm> {

    private Context context;
    private OnAlarmClickListener onAlarmClickListener;
    private OnAlarmCheckedChangeListener onAlarmCheckedChangeListener;
    private DBHelper dbHelper;

    @FunctionalInterface
    public interface OnAlarmCheckedChangeListener {
        void onCheckedChanged(int position, boolean isChecked);
    }


    public void setOnAlarmCheckedChangeListener(OnAlarmCheckedChangeListener onAlarmCheckedChangeListener) {
        this.onAlarmCheckedChangeListener = onAlarmCheckedChangeListener;
    }

    public AlarmRecyclerAdapter(Context context, OnAlarmClickListener listener) {
        this.context = context;
        this.onAlarmClickListener = listener;
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlarmViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        CheckBox alarmCheckBox = holder.itemView.findViewById(R.id.item_bus_alarm_check);
        holder.itemView.setOnLongClickListener(v -> {
            deleteAlarm(getItem(position).getAlarmId());
            removeItem(position);
            this.notifyDataSetChanged();
            return true;
        });

        alarmCheckBox.setChecked(getItem(position).getOn() == 1);
        holder.itemView.setOnClickListener(view -> onAlarmClickListener.onClick(position));

        alarmCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(onAlarmCheckedChangeListener != null) {
                onAlarmCheckedChangeListener.onCheckedChanged(position, b);
            }
        });
    }

    private void deleteAlarm(String id) {
        if (dbHelper == null)
            dbHelper = new DBHelper(context);
        dbHelper.deleteAlarm(id);
    }

}

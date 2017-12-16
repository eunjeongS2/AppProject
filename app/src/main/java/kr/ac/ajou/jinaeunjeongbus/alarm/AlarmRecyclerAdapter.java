package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.ajou.jinaeunjeongbus.base.AbstractRecyclerAdapter;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractViewHolder;
import kr.ac.ajou.jinaeunjeongbus.database.DBHelper;


public class AlarmRecyclerAdapter extends AbstractRecyclerAdapter<Alarm> {

    private Context context;
    private OnAlarmClickListener onAlarmClickListener;
    private DBHelper dbHelper;

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
        holder.itemView.setOnLongClickListener(v -> {
            int pos = position;
            deleteAlarm(getItem(pos).getArriveTime());
            removeItem(pos);
            this.notifyDataSetChanged();
            return true;
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAlarmClickListener.onClick(position);
            }
        });
    }

    private void deleteAlarm(String id) {
        if (dbHelper == null)
            dbHelper = new DBHelper(context);
        dbHelper.deleteAlarm(id);
    }

    public void updateAlarm(String stringId, Alarm alarm) {
        if (dbHelper == null)
            dbHelper = new DBHelper(context);
        dbHelper.updateAlarm(stringId, alarm);
    }
}

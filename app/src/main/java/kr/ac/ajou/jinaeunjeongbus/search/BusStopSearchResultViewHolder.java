package kr.ac.ajou.jinaeunjeongbus.search;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.alarm.BusStop;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractViewHolder;

class BusStopSearchResultViewHolder extends AbstractViewHolder<BusStop> {
    private OnBusAlarmCheckListener onBusAlarmCheckListener;

    private TextView busStopName;
    private TextView busStopId;
    private CheckBox alarmCheckButton;
    private RelativeLayout relativeLayout;

    public BusStopSearchResultViewHolder(ViewGroup parent, OnBusAlarmCheckListener onBusAlarmCheckListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_bus_stop, parent, false));

        this.onBusAlarmCheckListener = onBusAlarmCheckListener;

        busStopName = itemView.findViewById(R.id.bus_stop_name);
        busStopId = itemView.findViewById(R.id.bus_stop_id);
        alarmCheckButton = itemView.findViewById(R.id.bus_stop_alarm_check);
        relativeLayout = itemView.findViewById(R.id.bus_stop_relative);

    }

    @Override
    public void onBindView(@NonNull BusStop item, int position) {
        busStopName.setText(item.getBusStopName());
        busStopId.setText(item.getBusStopId());

        alarmCheckButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if (onBusAlarmCheckListener != null) {
                onBusAlarmCheckListener.onBusStopAlarmChecked(item, position);
            }
        });

    }
}

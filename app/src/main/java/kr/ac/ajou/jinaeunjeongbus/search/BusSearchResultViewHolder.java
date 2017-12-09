package kr.ac.ajou.jinaeunjeongbus.search;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractViewHolder;


class BusSearchResultViewHolder extends AbstractViewHolder<Bus> {

    private OnBusAlarmCheckListener onBusAlarmCheckListener;

    private TextView busNumber;
    private TextView busDescription;
    private CheckBox alarmCheckButton;
    private RelativeLayout relativeLayout;

    public BusSearchResultViewHolder(ViewGroup parent, OnBusAlarmCheckListener onBusAlarmCheckListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_bus, parent, false));

        this.onBusAlarmCheckListener = onBusAlarmCheckListener;

        busNumber = itemView.findViewById(R.id.bus_number);
        busDescription = itemView.findViewById(R.id.bus_description);
        alarmCheckButton = itemView.findViewById(R.id.bus_alarm_check);
        relativeLayout = itemView.findViewById(R.id.bus_relative);

    }

    @Override
    public void onBindView(@NonNull Bus item, int position) {
        busNumber.setText(item.getNumber());
        busDescription.setText(item.getDescription());

        alarmCheckButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if(onBusAlarmCheckListener != null) {
                onBusAlarmCheckListener.onBusAlarmChecked(item, position);
            }
        });


    }
}

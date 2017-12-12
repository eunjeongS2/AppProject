package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractViewHolder;


class AlarmViewHolder extends AbstractViewHolder<Alarm> {

    private TextView destination;
    private TextView departure;
    private TextView busStop;
    private TextView time;
    private TextView bus;

    public AlarmViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false));

        destination = itemView.findViewById(R.id.alarm_destination);
        departure = itemView.findViewById(R.id.alarm_departure);
        busStop = itemView.findViewById(R.id.alarm_bus_stop);
        time = itemView.findViewById(R.id.alarm_time);
        bus = itemView.findViewById(R.id.alarm_bus_list);
    }

    @Override
    public void onBindView(@NonNull Alarm item, int position) {
        destination.setText(item.getDestinationPlace());
        departure.setText(item.getDeparturePlace());
        busStop.setText(item.getDepartureStop());
        time.setText(item.getArriveTime());
        bus.setText(item.getBusName());

    }
}

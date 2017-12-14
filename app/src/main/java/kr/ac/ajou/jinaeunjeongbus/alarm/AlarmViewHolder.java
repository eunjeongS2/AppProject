package kr.ac.ajou.jinaeunjeongbus.alarm;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.ac.ajou.jinaeunjeongbus.R;
import kr.ac.ajou.jinaeunjeongbus.base.AbstractViewHolder;


class AlarmViewHolder extends AbstractViewHolder<Alarm> {

    private TextView destination;
    private TextView departure;
    private TextView departureBusStop;
    private TextView destinationBusStop;
    private TextView time;
    private TextView bus;

    public AlarmViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false));

        destination = itemView.findViewById(R.id.alarm_destination);
        departure = itemView.findViewById(R.id.alarm_departure);
        departureBusStop = itemView.findViewById(R.id.alarm_departure_bus_stop);
        destinationBusStop = itemView.findViewById(R.id.alarm_destination_bus_stop);
        time = itemView.findViewById(R.id.alarm_time);
        bus = itemView.findViewById(R.id.alarm_bus_list);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindView(@NonNull Alarm item, int position) {
        destination.setText(item.getDestinationPlace());
        departure.setText(item.getDeparturePlace());
        departureBusStop.setText(item.getDepartureStop());
        destinationBusStop.setText(item.getDestinationStop());
        time.setText(item.getArriveTime().substring(0,2) + " : "+ item.getArriveTime().substring(2,4));
        bus.setText(item.getBusName());
    }
}

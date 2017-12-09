package kr.ac.ajou.jinaeunjeongbus.search;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;
import kr.ac.ajou.jinaeunjeongbus.alarm.BusStop;


public interface OnBusAlarmCheckListener {
    void onBusAlarmChecked(Bus bus, int position);
    void onBusStopAlarmChecked(BusStop busStop, int position);
}

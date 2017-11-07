package kr.ac.ajou.jinaeunjeongbus.search;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;


public interface OnBusAlarmCheckListener {
    void onBusAlarmChecked(Bus bus, int position);
}

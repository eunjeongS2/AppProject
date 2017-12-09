package kr.ac.ajou.jinaeunjeongbus.dataParse;

import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;


public interface OnBusFinderListener {
    void onBusFinderStart();
    void onBusFinderSuccess(List<Bus> busList);

}

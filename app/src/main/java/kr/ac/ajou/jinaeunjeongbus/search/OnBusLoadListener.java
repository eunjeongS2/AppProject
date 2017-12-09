package kr.ac.ajou.jinaeunjeongbus.search;

import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;


public interface OnBusLoadListener {
    void onFindStart();
    void onLoad(List<Bus> busList);

    void onSearchComplete(List<Bus> searchResult);
}

package kr.ac.ajou.jinaeunjeongbus.search;

import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.BusStop;


public interface OnBusStopLoadListener {
    void onLoad(List<BusStop> busList);

    void onSearchComplete(List<BusStop> searchResult);
}

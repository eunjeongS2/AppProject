package kr.ac.ajou.jinaeunjeongbus.search;

import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.BusStop;
import kr.ac.ajou.jinaeunjeongbus.dataParse.Address;


public interface OnBusStopLoadListener {
    void onBusStopSearchComplete(List<BusStop> searchResult);
    void onBusStopCoordinatesLoad(List<Address> addressList);
}

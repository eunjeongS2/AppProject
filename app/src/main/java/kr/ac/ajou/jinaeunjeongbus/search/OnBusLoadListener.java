package kr.ac.ajou.jinaeunjeongbus.search;

import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;

/**
 * Created by ijina on 2017. 11. 6..
 */

public interface OnBusLoadListener {
    void onLoad(List<Bus> busList);

    void onSearchComplete(List<Bus> searchResult);
}

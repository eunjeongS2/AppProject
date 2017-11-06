package kr.ac.ajou.jinaeunjeongbus.search;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;

/**
 * Created by ijina on 2017. 11. 6..
 */

public class SearchModel {

    private List<Bus> busList = new ArrayList<>();

    private OnBusLoadListener onBusLoadListener;

    public SearchModel(OnBusLoadListener onBusLoadListener) {
        this.onBusLoadListener = onBusLoadListener;
    }

    public void fetchBusList() {

        busList.add(new Bus("3", "광역버스"));
        busList.add(new Bus("4", "광역버스"));
        busList.add(new Bus("5", "광역버스"));
        busList.add(new Bus("6", "광역버스"));
        busList.add(new Bus("7", "광역버스"));

        if (onBusLoadListener != null) {
            onBusLoadListener.onLoad(busList);
        }
    }

    public void query(String message) {
        List<Bus> searchResult = new ArrayList<>();

        for (Bus bus : busList) {
            Log.d("asdf", "query: " + bus.getNumber() + " . " + message);
            if (bus.getNumber().equals(message)) {
                searchResult.add(bus);
            }
        }

        if(onBusLoadListener != null) {
            onBusLoadListener.onSearchComplete(searchResult);
        }
    }
}

package kr.ac.ajou.jinaeunjeongbus.search;

import java.util.ArrayList;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;


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

        System.out.println("fetch");


        if (onBusLoadListener != null) {
            System.out.println("onLoad" + busList.size());

            onBusLoadListener.onLoad(busList);
        }

    }

    public void query(String message) {
        List<Bus> searchResult = new ArrayList<>();

        for (Bus bus : busList) {
            if (bus.getNumber().equals(message)) {
                searchResult.add(bus);
            }
        }


        if(onBusLoadListener != null) {
            onBusLoadListener.onSearchComplete(searchResult);
        }
    }
}

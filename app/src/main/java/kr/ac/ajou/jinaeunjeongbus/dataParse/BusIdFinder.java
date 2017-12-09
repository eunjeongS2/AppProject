package kr.ac.ajou.jinaeunjeongbus.dataParse;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.ajou.jinaeunjeongbus.search.OnBusLoadListener;

public class BusIdFinder extends BusFinder{
    private static final String ROUTE_URL = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?ServiceKey=";
    private static final String SEOUL_API_KEY = "DD0pwxcJt7QW0EtFlsbEwQ8w2sWJMfADc%2FMBBK1Ju0RQgbWrVRIb4jDTGAzAI0p3kS1KBYwHpULqXZy%2FX%2Fe7RA%3D%3D";

    private String searchBusNumber;


    public BusIdFinder(OnBusLoadListener onBusLoadListener, String searchBusNumber) {
        super(onBusLoadListener);
        this.searchBusNumber = searchBusNumber;
    }

    @Override
    public void execute() throws UnsupportedEncodingException {
        new DownloadRawData(getOnBusLoadListener()).execute(createUrl());
    }

    @Override
    public String createUrl() throws UnsupportedEncodingException {
        String urlSearchBusNumber = URLEncoder.encode(searchBusNumber, "utf-8");

        return ROUTE_URL + SEOUL_API_KEY + "&strSrch=" + urlSearchBusNumber;
    }

}


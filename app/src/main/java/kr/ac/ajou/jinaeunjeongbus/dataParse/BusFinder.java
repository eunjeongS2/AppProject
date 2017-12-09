package kr.ac.ajou.jinaeunjeongbus.dataParse;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BusFinder {
    private static final String ROUTE_URL = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?ServiceKey=";
    private static final String LOCATION_URL = "http://ws.bus.go.kr/api/rest/buspos";
    private static final String ARRIVE_URL = "http://ws.bus.go.kr/api/rest/arrive";
    private static final String SEOUL_API_KEY = "";

    private OnBusFinderListener onBusFinderListener;
    private String searchBusNumber;
//    http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?ServiceKey=인증키&strSrch=3

    public BusFinder(OnBusFinderListener onBusFinderListener, String searchBusNumber){
        this.onBusFinderListener = onBusFinderListener;
        this.searchBusNumber = searchBusNumber;

    }

    public void execute() throws UnsupportedEncodingException {
        onBusFinderListener.onBusFinderStart();
        new DownloadRawData(onBusFinderListener).execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {
        String urlSearchBusNumber = URLEncoder.encode(searchBusNumber, "utf-8");

        return ROUTE_URL + SEOUL_API_KEY + "&strSrch=" + searchBusNumber;
    }


}


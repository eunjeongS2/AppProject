package kr.ac.ajou.jinaeunjeongbus.dataParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.BusStop;
import kr.ac.ajou.jinaeunjeongbus.search.OnBusStopLoadListener;


public class BusStopFinder extends Finder implements FindListener.OnBusStopIdFindListener{

    private static final String SEARCH_BUS_STOP_ID_URL = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByName?ServiceKey=";
    private static final String SEOUL_API_KEY = "DD0pwxcJt7QW0EtFlsbEwQ8w2sWJMfADc%2FMBBK1Ju0RQgbWrVRIb4jDTGAzAI0p3kS1KBYwHpULqXZy%2FX%2Fe7RA%3D%3D";


    private OnBusStopLoadListener onBusStopLoadListener;
    private String busStopName;

    public BusStopFinder(OnBusStopLoadListener onBusStopLoadListener, String busStopName) {
        this.onBusStopLoadListener = onBusStopLoadListener;
        this.busStopName = "사당역";
    }

    @Override
    public void execute() throws UnsupportedEncodingException {
        new DownloadRawData(this).execute(createUrl());
    }

    @Override
    public String createUrl() throws UnsupportedEncodingException {
        String urlSearchBusStopNumber = URLEncoder.encode(busStopName, "utf-8");

        return SEARCH_BUS_STOP_ID_URL + SEOUL_API_KEY + "&stSrch=" + urlSearchBusStopNumber;
    }

    @Override
    public void parseXML(Document document) {
        List<BusStop> busStops = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("itemList");

        for(int i = 0; i< nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;
            BusStop busStop = new BusStop();

            NodeList busStopIdNode = firstElement.getElementsByTagName("stId");
            busStop.setBusStopId(busStopIdNode.item(0).getChildNodes().item(0).getNodeValue());

            NodeList busStopNameNode = firstElement.getElementsByTagName("stNm");
            busStop.setBusStopName(busStopNameNode.item(0).getChildNodes().item(0).getNodeValue());

            NodeList busStopDistinctNumberNode = firstElement.getElementsByTagName("arsId");
            busStop.setDistinctNumber(busStopDistinctNumberNode.item(0).getChildNodes().item(0).getNodeValue());

            busStops.add(busStop);

        }

        onBusStopLoadListener.onSearchComplete(busStops);

    }
}

package kr.ac.ajou.jinaeunjeongbus.dataParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.ajou.jinaeunjeongbus.alarm.BusStop;
import kr.ac.ajou.jinaeunjeongbus.alarm.OnBusRequiredTimeLoadListener;
import kr.ac.ajou.jinaeunjeongbus.dataParse.DownloadRawData;
import kr.ac.ajou.jinaeunjeongbus.dataParse.FindListener;
import kr.ac.ajou.jinaeunjeongbus.dataParse.Finder;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.OnBusRequiredTimeLoadListener;
import kr.ac.ajou.jinaeunjeongbus.search.OnBusStopLoadListener;

public class BusStopByRouteFinder extends Finder implements FindListener.OnBusStopByRouteFinderListener {
    private String busId;
    private OnBusStopLoadListener onBusStopLoadListener;

    private static final String SEARCH_LOCATION_URL = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute?serviceKey=";
    private static final String SEOUL_API_KEY = "DD0pwxcJt7QW0EtFlsbEwQ8w2sWJMfADc%2FMBBK1Ju0RQgbWrVRIb4jDTGAzAI0p3kS1KBYwHpULqXZy%2FX%2Fe7RA%3D%3D";


    public BusStopByRouteFinder(OnBusStopLoadListener onBusStopLoadListener, String busId) {
        this.busId = busId;
        this.onBusStopLoadListener = onBusStopLoadListener;
    }


    @Override
    public void execute() throws UnsupportedEncodingException {
        new DownloadRawData(this).execute(createUrl());
    }

    @Override
    public String createUrl() throws UnsupportedEncodingException {
        String urlSearchBusLocation = URLEncoder.encode(busId, "utf-8");

        return SEARCH_LOCATION_URL + SEOUL_API_KEY + "&busRouteId=" + urlSearchBusLocation;
    }

    @Override
    public void parseXML(Document document) {
        List<BusStop> busStops = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("itemList");


        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;

            BusStop busStop = new BusStop();
            Address address = new Address();

            NodeList busStopIdNode = firstElement.getElementsByTagName("station");
            busStop.setBusStopId(busStopIdNode.item(0).getChildNodes().item(0).getNodeValue());

            NodeList busStopNameNode = firstElement.getElementsByTagName("stationNm");
            busStop.setBusStopName(busStopNameNode.item(0).getChildNodes().item(0).getNodeValue());
            address.setAddressName(busStopNameNode.item(0).getChildNodes().item(0).getNodeValue());

            NodeList busStopLongitudeNode = firstElement.getElementsByTagName("gpsX");
            address.setAddressLongitude(busStopLongitudeNode.item(0).getChildNodes().item(0).getNodeValue());

            NodeList busStopLatitudeNode = firstElement.getElementsByTagName("gpsY");
            address.setAddressLatitude(busStopLatitudeNode.item(0).getChildNodes().item(0).getNodeValue());

            busStops.add(busStop);
            addresses.add(address);
        }


        onBusStopLoadListener.onBusStopSearchComplete(busStops);
        onBusStopLoadListener.onBusStopCoordinatesLoad(addresses);

    }

}



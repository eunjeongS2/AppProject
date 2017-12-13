package kr.ac.ajou.jinaeunjeongbus.dataParse;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;
import kr.ac.ajou.jinaeunjeongbus.search.OnBusLoadListener;

public class BusIdFinder extends Finder implements FindListener.OnBusIdFindListener{
    private static final String SEARCH_ID_URL = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?ServiceKey=";
    private static final String SEOUL_API_KEY = "DD0pwxcJt7QW0EtFlsbEwQ8w2sWJMfADc%2FMBBK1Ju0RQgbWrVRIb4jDTGAzAI0p3kS1KBYwHpULqXZy%2FX%2Fe7RA%3D%3D";

    private String searchBusNumber;
    private OnBusLoadListener onBusLoadListener;

    public BusIdFinder(OnBusLoadListener onBusLoadListener, String searchBusNumber) {
        this.searchBusNumber = searchBusNumber;
        this.onBusLoadListener = onBusLoadListener;
    }

    @Override
    public void execute() throws UnsupportedEncodingException {
        new DownloadRawData(this).execute(createUrl());
    }

    @Override
    public String createUrl() throws UnsupportedEncodingException {
        String urlSearchBusNumber = URLEncoder.encode(searchBusNumber, "utf-8");

        return SEARCH_ID_URL + SEOUL_API_KEY + "&strSrch=" + urlSearchBusNumber;
    }

    @Override
    public void parseXML(Document document) {
        List<Bus> buses = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("itemList");

        for(int i = 0; i< nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;
            Bus bus = new Bus();

            NodeList busIdNode = firstElement.getElementsByTagName("busRouteId");
            bus.setId(busIdNode.item(0).getChildNodes().item(0).getNodeValue());

            System.out.println("id"+bus.getId());


            NodeList busNameNode = firstElement.getElementsByTagName("busRouteNm");
            bus.setNumber(busNameNode.item(0).getChildNodes().item(0).getNodeValue());


            NodeList stopFlag = firstElement.getElementsByTagName("term");
            bus.setTermInfo(stopFlag.item(0).getChildNodes().item(0).getNodeValue());

            NodeList startBusStop = firstElement.getElementsByTagName("stStationNm");
            NodeList endBusStop = firstElement.getElementsByTagName("edStationNm");

            bus.setDescription(startBusStop.item(0).getChildNodes().item(0).getNodeValue()+" <---> "+
            endBusStop.item(0).getChildNodes().item(0).getNodeValue());


            buses.add(bus);

        }

        onBusLoadListener.onSearchComplete(buses);
    }
}


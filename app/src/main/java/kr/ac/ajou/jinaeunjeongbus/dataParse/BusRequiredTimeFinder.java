package kr.ac.ajou.jinaeunjeongbus.dataParse;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.ajou.jinaeunjeongbus.alarm.OnBusRequiredTimeLoadListener;

public class BusRequiredTimeFinder extends Finder implements FindListener.OnBusRequiredTimeFindListener {
    private String busId;
    private String departureBusStopId;
    private String destinationBusStopId;
    private OnBusRequiredTimeLoadListener onBusRequiredTimeLoadListener;
//    http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute?serviceKey=d352JRNo6a9JWZlifq8DhSbfo1KvhOIHmtj%2F9GbDDXpHaYD6flXPUcAiUskXbTnUeWIJ7ywd%2BLCfmJu6aDcwEA%3D%3D&busRouteId=200000112

    private static final String SEARCH_LOCATION_URL = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute?serviceKey=";
    private static final String SEOUL_API_KEY = "DD0pwxcJt7QW0EtFlsbEwQ8w2sWJMfADc%2FMBBK1Ju0RQgbWrVRIb4jDTGAzAI0p3kS1KBYwHpULqXZy%2FX%2Fe7RA%3D%3D";


    public BusRequiredTimeFinder(OnBusRequiredTimeLoadListener onBusRequiredTimeLoadListener, String busId, String departureBusStopId, String destinationBusStopId) {
        this.busId = busId;
        this.departureBusStopId = departureBusStopId;
        this.destinationBusStopId = destinationBusStopId;
        this.onBusRequiredTimeLoadListener = onBusRequiredTimeLoadListener;
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
        NodeList nodeList = document.getElementsByTagName("itemList");
        //arrmsg1 : 9분7초후[5번째 전] arrmsg2
        float requiredTime = 0;
        int departureBusStopNumber = 0;
        int destinationBusStopNumber = 0;

        for(int i = 0; i< nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;

            NodeList busStopIdNode = firstElement.getElementsByTagName("station");
            String currentBusStopId = busStopIdNode.item(0).getChildNodes().item(0).getNodeValue();

            if(currentBusStopId.equals(departureBusStopId)){
                NodeList stationNumber = firstElement.getElementsByTagName("seq");
                departureBusStopNumber = Integer.parseInt(stationNumber.item(0).getChildNodes().item(0).getNodeValue());
                System.out.println("Finder: departureBusStopNumber: "+departureBusStopNumber);
            }else if (currentBusStopId.equals(destinationBusStopId)){
                NodeList stationNumber = firstElement.getElementsByTagName("seq");
                destinationBusStopNumber = Integer.parseInt(stationNumber.item(0).getChildNodes().item(0).getNodeValue());
                System.out.println("Finder: destinationBusStopNumber: "+destinationBusStopNumber);

            }
        }

        for(int i = 0; i< nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;

            NodeList busStopNumberNode = firstElement.getElementsByTagName("seq");
            if(Integer.parseInt(busStopNumberNode.item(0).getChildNodes().item(0).getNodeValue())>departureBusStopNumber
                    && Integer.parseInt(busStopNumberNode.item(0).getChildNodes().item(0).getNodeValue())<=destinationBusStopNumber){
                NodeList speedNode = firstElement.getElementsByTagName("sectSpd");
                NodeList distanceNode = firstElement.getElementsByTagName("fullSectDist");
                float speed = Float.parseFloat(speedNode.item(0).getChildNodes().item(0).getNodeValue());
                float distance = Float.parseFloat(distanceNode.item(0).getChildNodes().item(0).getNodeValue())/1000;

                System.out.println("speed: "+speed);
                System.out.println("distance: "+distance);

                float sectionTime = distance/speed;

                System.out.println("sectionTime: "+sectionTime);

                requiredTime += sectionTime;
                System.out.println("Finder: requiredTime"+requiredTime);
            }
        }

        onBusRequiredTimeLoadListener.onBusRequiredTimeLoad(String.valueOf(((int)(requiredTime*60))+1));

    }

}


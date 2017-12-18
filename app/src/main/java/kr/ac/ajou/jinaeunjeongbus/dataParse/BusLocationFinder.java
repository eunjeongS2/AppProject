package kr.ac.ajou.jinaeunjeongbus.dataParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.ajou.jinaeunjeongbus.alarm.OnLocationLoadListener;


public class BusLocationFinder extends Finder implements FindListener.OnBusLocationFindListener {
    private String busId;
    private String busStopId;
    private String firstArrive;
    private String secondArrive;
    private OnLocationLoadListener onLocationLoadListener;
    private int alarmPosition;

    private static final String SEARCH_LOCATION_URL = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?ServiceKey=";
    private static final String SEOUL_API_KEY = "%2BiZRN8V4rI%2BGzHUb23Aib8OhPshR5AxVmvZUnkvfJe3Z6rqGrAa3%2Bo%2BU1MvcU%2BOzG6T%2Fc%2FPJiogevEvPZ3Lxpw%3D%3D";

    public BusLocationFinder(OnLocationLoadListener onLocationLoadListener, int alarmPosition, String busId, String busStopId) {
        this.busId = busId;
        this.busStopId = busStopId;
        this.onLocationLoadListener = onLocationLoadListener;
        this.alarmPosition = alarmPosition;
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

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;

            NodeList busStopIdNode = firstElement.getElementsByTagName("stId");

            if (busStopIdNode.item(0).getChildNodes().item(0).getNodeValue().equals(busStopId)) {
                NodeList arrmsg1Node = firstElement.getElementsByTagName("arrmsg1");
                firstArrive = arrmsg1Node.item(0).getChildNodes().item(0).getNodeValue();
                NodeList arrmsg2Node = firstElement.getElementsByTagName("arrmsg2");
                secondArrive = arrmsg2Node.item(0).getChildNodes().item(0).getNodeValue();

            }
        }
        onLocationLoadListener.onLocationLoadListener(alarmPosition, firstArrive, secondArrive);

    }

}

package kr.ac.ajou.jinaeunjeongbus.dataParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.ajou.jinaeunjeongbus.search.OnBusLoadListener;


public class BusLocationFinder extends BusFinder implements OnBusLocationFinder{
    private String busId;
    private String busStopId;

    private static final String SEARCH_LOCATION_URL = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?ServiceKey=";
    private static final String SEOUL_API_KEY = "DD0pwxcJt7QW0EtFlsbEwQ8w2sWJMfADc%2FMBBK1Ju0RQgbWrVRIb4jDTGAzAI0p3kS1KBYwHpULqXZy%2FX%2Fe7RA%3D%3D";


    public BusLocationFinder(OnBusLoadListener onBusLoadListener, String busId, String busStopId) {
        super(onBusLoadListener);
        this.busId = "200000112";
        this.busStopId = "120000059";
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

        for(int i = 0; i< nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;

            NodeList busStopIdNode = firstElement.getElementsByTagName("stId");
            if(busStopIdNode.item(0).getChildNodes().item(0).getNodeValue().equals(busStopId)){
                NodeList arrmsg1Node = firstElement.getElementsByTagName("arrmsg1");
                System.out.println(arrmsg1Node.item(0).getChildNodes().item(0).getNodeValue());

            }



        }

    }
}

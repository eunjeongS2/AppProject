package kr.ac.ajou.jinaeunjeongbus.dataParse;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.ajou.jinaeunjeongbus.alarm.OnCoordinatesLoadListener;

//https://api2.sktelecom.com/tmap/geo/geocoding?version=1&format=xml&callback=result
// &city_do=%EC%88%98%EC%9B%90%EC%8B%9C
// &gu_gun=%EC%98%81%ED%86%B5%EA%B5%AC
// &dong=%EB%A7%9D%ED%8F%AC%EB%8F%99
// &bunji=
// &detailAddress=%EB%A7%88%EC%A0%A4%EB%9E%80%EC%95%84%ED%8C%8C%ED%8A%B8+1102%EB%8F%99+1004%ED%98%B8
// &addressFlag=F01
// &coordType=WGS84GEO
// &appKey=0615e827-7bbe-4792-b9ae-1a17265bc8d3


public class CoordinatesFinder extends Finder implements FindListener.OnCoordinatesFindListener{

    private static final String COORDINATES_FIND_URL = "https://api2.sktelecom.com/tmap/geo/fullAddrGeo?version=1&format=xml&callback=result&addressFlag=F01&coordType=WGS84GEO&fullAddr=";
    private static final String TMAP_KEY = "0615e827-7bbe-4792-b9ae-1a17265bc8d3";


    private OnCoordinatesLoadListener onCoordinatesLoadListener;
    private String addressName;



    public CoordinatesFinder(OnCoordinatesLoadListener onCoordinatesLoadListener, String addressName) {
        this.onCoordinatesLoadListener = onCoordinatesLoadListener;
        this.addressName = addressName;
    }

    @Override
    public void execute() throws UnsupportedEncodingException {
        new DownloadRawData(this).execute(createUrl());
    }

    @Override
    public String createUrl() throws UnsupportedEncodingException {
        String urlSearchAddressName = URLEncoder.encode(addressName, "utf-8");

        return COORDINATES_FIND_URL+urlSearchAddressName+"&appKey="+TMAP_KEY;
    }

    @Override
    public void parseXML(Document document) {
        NodeList nodeList = document.getElementsByTagName("coordinate");
        String latitude = "";
        String longitude = "";

        for(int i = 0; i< nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;

            NodeList latitudeNode = firstElement.getElementsByTagName("lat");
            latitude = latitudeNode.item(0).getChildNodes().item(0).getNodeValue();

            NodeList longitudeNode = firstElement.getElementsByTagName("lon");
            longitude = longitudeNode.item(0).getChildNodes().item(0).getNodeValue();

        }

        onCoordinatesLoadListener.onCoordinatesLoad(new Address(addressName, latitude, longitude));

    }
}

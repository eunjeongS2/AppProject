package kr.ac.ajou.jinaeunjeongbus.dataParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.ajou.jinaeunjeongbus.alarm.OnWalkRequiredTimeLoadListener;

public class WalkRequiredTimeFinder extends Finder implements FindListener.OnWalkRequiredTimeFindListener{

    private static final String WALK_REQUIRED_TIME_FIND_URL = "https://api2.sktelecom.com/tmap/routes/pedestrian?version=1&format=xml";
    private static final String TMAP_KEY = "0615e827-7bbe-4792-b9ae-1a17265bc8d3";
    private static final String SETTING_URL = "&reqCoordType=WGS84GEO";

    private String walkDepartureAddressName;
    private String walkDepartureAddressLatitude;
    private String walkDepartureAddressLongitude;

    private String walkDestinationAddressName;
    private String walkDestinationAddressLatitude;
    private String walkDestinationAddressLongitude;

    private OnWalkRequiredTimeLoadListener onWalkRequiredTimeLoadListener;


    public WalkRequiredTimeFinder(OnWalkRequiredTimeLoadListener onWalkRequiredTimeLoadListener, Address walkDepartureAddress, Address walkDestinationAddress) {
        this.onWalkRequiredTimeLoadListener = onWalkRequiredTimeLoadListener;
        this.walkDepartureAddressName = walkDepartureAddress.getAddressName();
        this.walkDepartureAddressLatitude = walkDepartureAddress.getAddressLatitude();
        this.walkDepartureAddressLongitude = walkDepartureAddress.getAddressLongitude();

        this.walkDestinationAddressName = walkDestinationAddress.getAddressName();
        this.walkDestinationAddressLatitude = walkDestinationAddress.getAddressLatitude();
        this.walkDestinationAddressLongitude = walkDestinationAddress.getAddressLongitude();

        System.out.println("Finderdeplat"+walkDepartureAddressLatitude);
        System.out.println("Finderdeplon"+walkDepartureAddressLongitude);
        System.out.println("Finderdeslat"+walkDestinationAddressLatitude);
        System.out.println("Finderdeslon"+walkDestinationAddressLongitude);

    }

    @Override
    public void execute() throws UnsupportedEncodingException {
        new DownloadRawData(this).execute(createUrl());

    }

    @Override
    public String createUrl() throws UnsupportedEncodingException {
        String urlStart = URLEncoder.encode(walkDepartureAddressName, "utf-8");
        String urlEnd = URLEncoder.encode(walkDestinationAddressName, "utf-8");


        return WALK_REQUIRED_TIME_FIND_URL +"&startX="+ walkDepartureAddressLongitude +"&startY="+ walkDepartureAddressLatitude
                +"&endX="+ walkDestinationAddressLongitude +"&endY="+ walkDestinationAddressLatitude +"&startName="+urlStart+"&endName="+urlEnd
                + SETTING_URL+"&appKey="+TMAP_KEY;
    }

    @Override
    public void parseXML(Document document) {
        String requiredTime = "";

        NodeList nodeList = document.getElementsByTagName("Document");


        for(int i = 0; i< nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;

            NodeList requiredTimeNode = firstElement.getElementsByTagName("tmap:totalTime");
            requiredTime = requiredTimeNode.item(0).getChildNodes().item(0).getNodeValue();

        }

        onWalkRequiredTimeLoadListener.onWalkRequiredTimeLoad(walkDepartureAddressName, requiredTime);

    }
}

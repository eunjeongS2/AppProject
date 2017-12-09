package kr.ac.ajou.jinaeunjeongbus.dataParse;


import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;

public class DownloadRawData extends AsyncTask<String, Void, Document> {
    private OnBusFinderListener onBusFinderListener;

    private Document document;
    private String result = "";

    public DownloadRawData(OnBusFinderListener onBusFinderListener) {
        this.onBusFinderListener = onBusFinderListener;

    }

    @Override
    protected Document doInBackground(String... urls) {
        URL url;
        try{
            url = new URL("http://ws.bus.go.kr/api/rest/buspos/" +
                    "getLowBusPosByRtid" +
                    "?ServiceKey=DD0pwxcJt7QW0EtFlsbEwQ8w2sWJMfADc%2FMBBK1Ju0RQgbWrVRIb4jDTGAzAI0p3kS1KBYwHpULqXZy%2FX%2Fe7RA%3D%3D" +
                    "&" +
                    "busRouteId" +
                    "=" +
                    "100100118");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(new InputSource(url.openStream()));
            document.getDocumentElement().normalize();
            System.out.println("TEST2" +document.getDocumentURI());
        }catch (Exception e){
            e.printStackTrace();
        }
        return document;
    }

    @Override
    protected void onPostExecute(Document document) {
        parseXML(document);

    }

    private void parseXML(Document document){
        List<Bus> buses = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("itemList");

        for(int i = 0; i< nodeList.getLength(); i++){

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;

            NodeList busType = firstElement.getElementsByTagName("busType");
            result += "busType = "+  busType.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList lastStTm = firstElement.getElementsByTagName("lastStTm");
            result += "lastStTm = "+  lastStTm.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList lastStnId  = firstElement.getElementsByTagName("lastStnId");
            result += "lastStnId = "+ lastStnId.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList nextStTm = firstElement.getElementsByTagName("nextStTm");
            result += "nextStTm = "+  nextStTm.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList stopFlag = firstElement.getElementsByTagName("stopFlag");
            result += "stopFlag = "+  stopFlag.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList  vehId = firstElement.getElementsByTagName("vehId");
            result += "vehId = "+  vehId.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList dataTm = firstElement.getElementsByTagName("dataTm");
            result += "dataTm = "+  dataTm.item(0).getChildNodes().item(0).getNodeValue() +"\n";
        }

        onBusFinderListener.onBusFinderSuccess(buses);

    }
}

package kr.ac.ajou.jinaeunjeongbus.dataParse;


import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kr.ac.ajou.jinaeunjeongbus.alarm.Bus;
import kr.ac.ajou.jinaeunjeongbus.search.OnBusLoadListener;

public class DownloadRawData extends AsyncTask<String, Void, Document> {
    private OnBusLoadListener onBusLoadListener;

    private Document document;

    public DownloadRawData(OnBusLoadListener onBusLoadListener) {
        this.onBusLoadListener = onBusLoadListener;

    }

    @Override
    protected Document doInBackground(String... urls) {
        String url = urls[0];
        try{
            URL currentUrl = new URL(url);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(new InputSource(currentUrl.openStream()));
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

        for(int i = 0; i< nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element firstElement = (Element) node;
            Bus bus = new Bus();

            NodeList busType = firstElement.getElementsByTagName("busRouteId");
            bus.setId(busType.item(0).getChildNodes().item(0).getNodeValue());

            NodeList lastStTm = firstElement.getElementsByTagName("busRouteNm");
            bus.setNumber(lastStTm.item(0).getChildNodes().item(0).getNodeValue());

            NodeList stopFlag = firstElement.getElementsByTagName("term");
            bus.setTermInfo(stopFlag.item(0).getChildNodes().item(0).getNodeValue());

            buses.add(bus);

        }

        onBusLoadListener.onSearchComplete(buses);

    }
}

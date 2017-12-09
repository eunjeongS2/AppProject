package kr.ac.ajou.jinaeunjeongbus.dataParse;
 
import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParser extends AsyncTask<String, Void, Document> {

    private Document doc;
    private String result = "";

    public String getResult() {
        return result;
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
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
            System.out.println("TEST2" +doc.getDocumentURI());
        }catch (Exception e){
            e.printStackTrace();
        }
        return doc;
    }

    @Override
    protected void onPostExecute(Document doc) {

        NodeList nodeList = doc.getElementsByTagName("itemList");

        for(int i = 0; i< nodeList.getLength(); i++){

            Node node = nodeList.item(i);
            Element fstElmnt = (Element) node;

            NodeList busType = fstElmnt.getElementsByTagName("busType");
            result += "busType = "+  busType.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList lastStTm = fstElmnt.getElementsByTagName("lastStTm");
            result += "lastStTm = "+  lastStTm.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList lastStnId  = fstElmnt.getElementsByTagName("lastStnId");
            result += "lastStnId = "+ lastStnId.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList nextStTm = fstElmnt.getElementsByTagName("nextStTm");
            result += "nextStTm = "+  nextStTm.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList stopFlag = fstElmnt.getElementsByTagName("stopFlag");
            result += "stopFlag = "+  stopFlag.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList  vehId = fstElmnt.getElementsByTagName("vehId");
            result += "vehId = "+  vehId.item(0).getChildNodes().item(0).getNodeValue() +"\n";

            NodeList dataTm = fstElmnt.getElementsByTagName("dataTm");
            result += "dataTm = "+  dataTm.item(0).getChildNodes().item(0).getNodeValue() +"\n";
        }

        super.onPostExecute(doc);
    }

}



package kr.ac.ajou.jinaeunjeongbus.dataParse;


import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class DownloadRawData extends AsyncTask<String, Void, Document> {

    private Document document;
    private OnBusIdFindListener onBusIdFindListener;
    private OnBusLocationFinder onBusLocationFinder;
    private OnBusStopIdFindListener onBusStopIdFindListener;

    public DownloadRawData(OnBusStopIdFindListener onBusStopIdFindListener) {
        this.onBusStopIdFindListener = onBusStopIdFindListener;

    }

    public DownloadRawData(OnBusIdFindListener onBusIdFindListener) {
        this.onBusIdFindListener = onBusIdFindListener;

    }

    public DownloadRawData(OnBusLocationFinder onBusLocationFinder) {
        this.onBusLocationFinder = onBusLocationFinder;
    }

    @Override
    protected Document doInBackground(String... urls) {
        String url = urls[0];
        try{
            URL currentUrl = new URL(url);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(new InputSource(currentUrl.openStream()));
//            System.out.println("TEST2" +document.getDocumentURI());

            document.getDocumentElement().normalize();
        }catch (Exception e){
            e.printStackTrace();
        }
        return document;
    }

    @Override
    protected void onPostExecute(Document document) {
        if(onBusIdFindListener!=null){
            onBusIdFindListener.parseXML(document);
        }else if(onBusLocationFinder!=null){
            onBusLocationFinder.parseXML(document);
        }else if(onBusStopIdFindListener!=null){
            onBusStopIdFindListener.parseXML(document);
        }
    }
}

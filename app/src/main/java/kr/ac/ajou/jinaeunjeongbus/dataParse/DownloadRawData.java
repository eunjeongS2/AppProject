package kr.ac.ajou.jinaeunjeongbus.dataParse;


import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class DownloadRawData extends AsyncTask<String, Void, Document> {

    private Document document;
    private FindListener.OnBusIdFindListener onBusIdFindListener;
    private FindListener.OnBusLocationFindListener onBusLocationFindListener;
    private FindListener.OnBusStopIdFindListener onBusStopIdFindListener;
    private FindListener.OnCoordinatesFindListener onCoordinatesFindListener;
    private FindListener.OnWalkRequiredTimeFindListener onWalkRequiredTimeFindListener;

    public DownloadRawData(FindListener.OnBusIdFindListener onBusIdFindListener) {
        this.onBusIdFindListener = onBusIdFindListener;
    }
    public DownloadRawData(FindListener.OnBusLocationFindListener onBusLocationFindListener) {
        this.onBusLocationFindListener = onBusLocationFindListener;
    }

    public DownloadRawData(FindListener.OnBusStopIdFindListener onBusStopIdFindListener) {
        this.onBusStopIdFindListener = onBusStopIdFindListener;
    }

    public DownloadRawData(FindListener.OnCoordinatesFindListener onCoordinatesFindListener) {
        this.onCoordinatesFindListener = onCoordinatesFindListener;
    }

    public DownloadRawData(FindListener.OnWalkRequiredTimeFindListener onWalkRequiredTimeFindListener) {
        this.onWalkRequiredTimeFindListener = onWalkRequiredTimeFindListener;
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return document;
    }

    @Override
    protected void onPostExecute(Document document) {
        if(onBusIdFindListener!=null){
            onBusIdFindListener.parseXML(document);
        }else if(onBusLocationFindListener !=null){
            onBusLocationFindListener.parseXML(document);
        }else if(onBusStopIdFindListener!=null){
            onBusStopIdFindListener.parseXML(document);
        }else if(onCoordinatesFindListener != null){
            onCoordinatesFindListener.parseXML(document);
        }else if(onWalkRequiredTimeFindListener != null){
            onWalkRequiredTimeFindListener.parseXML(document);
        }
    }
}

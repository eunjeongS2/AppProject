package kr.ac.ajou.jinaeunjeongbus.dataParse;


import org.w3c.dom.Document;

public interface FindListener {
    interface OnBusIdFindListener{
        void parseXML(Document document);
    }

    interface OnBusLocationFindListener {
        void parseXML(Document document);
    }

    interface OnBusStopIdFindListener {
        void parseXML(Document document);
    }

    interface OnCoordinatesFindListener {
        void parseXML(Document document);
    }

    interface OnWalkRequiredTimeFindListener {
        void parseXML(Document document);
    }
}

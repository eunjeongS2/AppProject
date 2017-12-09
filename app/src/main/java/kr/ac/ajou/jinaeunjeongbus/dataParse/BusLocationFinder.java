package kr.ac.ajou.jinaeunjeongbus.dataParse;

import java.io.UnsupportedEncodingException;

import kr.ac.ajou.jinaeunjeongbus.search.OnBusLoadListener;


public class BusLocationFinder extends BusFinder{
    private String id;

    public BusLocationFinder(OnBusLoadListener onBusLoadListener, String id) {
        super(onBusLoadListener);
        this.id = id;
    }

    @Override
    public void execute() throws UnsupportedEncodingException {

    }

    @Override
    public String createUrl() throws UnsupportedEncodingException {
        return null;
    }
}

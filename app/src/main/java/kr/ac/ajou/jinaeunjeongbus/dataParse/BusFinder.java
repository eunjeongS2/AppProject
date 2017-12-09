package kr.ac.ajou.jinaeunjeongbus.dataParse;

import java.io.UnsupportedEncodingException;

import kr.ac.ajou.jinaeunjeongbus.search.OnBusLoadListener;


public abstract class BusFinder {

    private OnBusLoadListener onBusLoadListener;

    public BusFinder(OnBusLoadListener onBusLoadListener){
        this.onBusLoadListener = onBusLoadListener;

    }

    public OnBusLoadListener getOnBusLoadListener() {
        return onBusLoadListener;
    }

    public void setOnBusLoadListener(OnBusLoadListener onBusLoadListener) {
        this.onBusLoadListener = onBusLoadListener;
    }

    public abstract void execute() throws UnsupportedEncodingException;

    public abstract String createUrl() throws UnsupportedEncodingException;
}

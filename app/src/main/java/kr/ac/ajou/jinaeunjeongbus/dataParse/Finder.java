package kr.ac.ajou.jinaeunjeongbus.dataParse;

import java.io.UnsupportedEncodingException;


public abstract class Finder {

    public abstract void execute() throws UnsupportedEncodingException;

    public abstract String createUrl() throws UnsupportedEncodingException;
}

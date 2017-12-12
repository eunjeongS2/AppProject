package kr.ac.ajou.jinaeunjeongbus.alarm;

import java.util.List;

public final class Alarm {

    private String destination;
    private String departure;
    private String busStop;
    private String destinationTime;
    private String alarmTerm;
    private List<Bus> busList;
    private boolean isOn;


    public Alarm(String destination, String departure, String busStop, String destinationTime, String alarmTerm,
                 List<Bus> busList, boolean isOn) {
        this.destination = destination;
        this.departure = departure;
        this.busStop = busStop;
        this.destinationTime = destinationTime;
        this.alarmTerm = alarmTerm;
        this.busList = busList;
        this.isOn = isOn;
    }

    public Alarm(String destination, String departure, String destinationTime, String alarmTerm) {
        this.destination = destination;
        this.departure = departure;
        this.destinationTime = destinationTime;
        this.alarmTerm = alarmTerm;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setBusStop(String busStop) {
        this.busStop = busStop;
    }

    public void setDestinationTime(String destinationTime) {
        this.destinationTime = destinationTime;
    }

    public void setAlarmTerm(String alarmTerm) {
        this.alarmTerm = alarmTerm;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isOn() {

        return isOn;
    }

    public String getDestination() {
        return destination;
    }

    public String getDeparture() {
        return departure;
    }

    public String getBusStop() {
        return busStop;
    }

    public String getDestinationTime() {
        return destinationTime;
    }

    public String getAlarmTerm() {
        return alarmTerm;
    }

    public List<Bus> getBusList() {
        return busList;
    }

    //API정보 받아오기
    public void setBusList(List<Bus> busList) {
        this.busList = busList;
    }
}

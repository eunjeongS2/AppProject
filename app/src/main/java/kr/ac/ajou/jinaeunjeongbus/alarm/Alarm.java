package kr.ac.ajou.jinaeunjeongbus.alarm;

import java.util.List;

public final class Alarm {

    private String destination;
    private String departure;
    private String busStop;
    private String destinationTime;
    private String alarmTerm;
    private List<Bus> busList;

    public Alarm(String destination, String departure, String busStop, String destinationTime, String alarmTerm,
                 List<Bus> busList) {
        this.destination = destination;
        this.departure = departure;
        this.busStop = busStop;
        this.destinationTime = destinationTime;
        this.alarmTerm = alarmTerm;
        this.busList = busList;
    }

    public Alarm(String destination, String departure, String destinationTime, String alarmTerm) {
        this.destination = destination;
        this.departure = departure;
        this.destinationTime = destinationTime;
        this.alarmTerm = alarmTerm;
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

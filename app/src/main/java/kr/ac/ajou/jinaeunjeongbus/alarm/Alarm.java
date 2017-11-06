package kr.ac.ajou.jinaeunjeongbus.alarm;

import java.util.List;

public final class Alarm {

    private String destination;
    private String departure;
    private String busStop;
    private String startTime;
    private String endTime;
    private List<Bus> busList;

    public Alarm(String destination, String departure, String busStop, String startTime, String endTime,
        List<Bus> busList) {
        this.destination = destination;
        this.departure = departure;
        this.busStop = busStop;
        this.startTime = startTime;
        this.endTime = endTime;
        this.busList = busList;
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

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public List<Bus> getBusList() {
        return busList;
    }
}

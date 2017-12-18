package kr.ac.ajou.jinaeunjeongbus.alarm;

public class BusStop {
    private String busStopName;
    private String busStopId;
    private String distinctNumber;

    public BusStop() {
    }

    public String getBusStopName() {
        return busStopName;
    }

    public String getBusStopId() {
        return busStopId;
    }

    public String getDistinctNumber() {
        return distinctNumber;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }

    public void setBusStopId(String busStopId) {
        this.busStopId = busStopId;
    }

    public void setDistinctNumber(String distinctNumber) {
        this.distinctNumber = distinctNumber;
    }
}

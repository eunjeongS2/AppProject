package kr.ac.ajou.jinaeunjeongbus.alarm;


public class Alarm {

    public static final String TABLE = "ALARM_TABLE";
    public static final String KEY_ID = "_id";
    public static final String KEY_DEPARTURE_NAME = "departureName";
    public static final String KEY_DEPARTURE_STOP = "departureStop";
    public static final String KEY_DEPARTURE_NO = "departureNo";
    public static final String KEY_DEPARTURE_ID = "departureID";
    public static final String KEY_DESTINATION_NAME = "destinationName";
    public static final String KEY_DESTINATION_STOP = "destinationStop";
    public static final String KEY_BUS_NAME = "busName";
    public static final String KEY_BUS_ID = "busID";
    public static final String KEY_DESTINATION_TIME = "destinationTime";
    public static final String KEY_ALARM_TERM = "alarmTerm";
    public static final String KEY_ALARM_ISON = "alarmIsOn";

    private int alarmId;
    private String departurePlace;
    private String departureStop;
    private String departureNo;
    private String departureId;
    private String destinationPlace;
    private String destinationStop;
    private String busName;
    private String busId;
    private String arriveTime;
    private String alarmTerm;
    private Boolean isOn;

    public String getBusName() {
        return busName;
    }

    public Alarm(int alarmId, String departurePlace, String departureStop, String departureNo, String departureId,
                 String destinationPlace, String destinationStop, String busName, String busId, String arriveTime, String alarmTerm) {
        this.alarmId = alarmId;
        this.departurePlace = departurePlace;
        this.departureStop = departureStop;
        this.departureNo = departureNo;
        this.departureId = departureId;
        this.destinationPlace = destinationPlace;
        this.destinationStop = destinationStop;
        this.busName = busName;
        this.busId = busId;
        this.arriveTime = arriveTime;
        this.alarmTerm = alarmTerm;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setOn(Boolean on) {
        isOn = on;
    }

    public Boolean getOn() {
        return isOn;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public String getDepartureStop() {
        return departureStop;
    }

    public String getDepartureNo() {
        return departureNo;
    }

    public String getDepartureId() {
        return departureId;
    }

    public String getDestinationPlace() {
        return destinationPlace;
    }

    public String getDestinationStop() {
        return destinationStop;
    }

    public String getBusId() {
        return busId;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public String getAlarmTerm() {
        return alarmTerm;
    }
}

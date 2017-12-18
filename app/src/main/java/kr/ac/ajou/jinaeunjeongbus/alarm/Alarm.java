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
    public static final String KEY_BUS_REQUIRED_TIME = "busRequiredTime";
    public static final String KEY_DEPARTURE_REQUIRED_TIME = "departureRequiredTime";
    public static final String KEY_DESTINATION_REQUIRED_TIME = "destinationRequiredTime";


    private String alarmId;
    private String departurePlaceName;
//    private String departurePlaceLongitude;
//    private String departurePlaceLatitude;

    private String departureStop;
    private String departureStopNo;
    private String departureStopId;

    private String destinationPlaceName;
//    private String destinationPlaceLongitude;
//    private String destinationPlaceLatitude;

    private String destinationStop;
    private String destinationStopNo;
    private String destinationStopId;

    private String busName;
    private String busId;
    private String arriveTime;
    private String alarmTerm;
    private int isOn;

    private String busRequiredTime;
    private String departureRequiredTime;
    private String destinationRequiredTime;

    private String firstArrive = "";
    private String secondArrive = "";

    public Alarm(String alarmId, String departurePlaceName, String departureStop, String departureStopId, String departureStopNo,
                 String destinationPlaceName, String destinationStop, String busName, String busId, String arriveTime, String alarmTerm,
                 String busRequiredTime, String departureRequiredTime, String destinationRequiredTime) {
        this.alarmId = alarmId;
        this.departurePlaceName = departurePlaceName;
        this.departureStop = departureStop;
        this.departureStopId = departureStopId;
        this.departureStopNo = departureStopNo;
        this.destinationPlaceName = destinationPlaceName;
        this.destinationStop = destinationStop;
        this.busName = busName;
        this.busId = busId;
        this.arriveTime = arriveTime;
        this.alarmTerm = alarmTerm;
        this.busRequiredTime = busRequiredTime;
        this.departureRequiredTime = departureRequiredTime;
        this.destinationRequiredTime = destinationRequiredTime;

    }

    public String getBusName() {
        return busName;
    }

    public String getBusRequiredTime() {
        return busRequiredTime;
    }

    public String getDepartureRequiredTime() {
        return departureRequiredTime;
    }

    public String getDestinationRequiredTime() {
        return destinationRequiredTime;
    }

    public void setBusRequiredTime(String busRequiredTime) {
        this.busRequiredTime = busRequiredTime;
    }

    public void setDepartureRequiredTime(String departureRequiredTime) {
        this.departureRequiredTime = departureRequiredTime;
    }

    public void setDestinationRequiredTime(String destinationRequiredTime) {
        this.destinationRequiredTime = destinationRequiredTime;
    }

    public void setFirstArrive(String firstArrive) {
        this.firstArrive = firstArrive;
    }

    public void setSecondArrive(String secondArrive) {
        this.secondArrive = secondArrive;
    }

    public String getFirstArrive() {
        return firstArrive;
    }

    public String getSecondArrive() {
        return secondArrive;
    }

    public String getDestinationStopNo() {
        return destinationStopNo;
    }

    public String getDestinationStopId() {
        return destinationStopId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public void setDeparturePlaceName(String departurePlaceName) {
        this.departurePlaceName = departurePlaceName;
    }


    public void setDepartureStop(String departureStop) {
        this.departureStop = departureStop;
    }

    public void setDepartureStopNo(String departureStopNo) {
        this.departureStopNo = departureStopNo;
    }

    public void setDepartureStopId(String departureStopId) {
        this.departureStopId = departureStopId;
    }

    public void setDestinationPlaceName(String destinationPlaceName) {
        this.destinationPlaceName = destinationPlaceName;
    }

    public void setDestinationStop(String destinationStop) {
        this.destinationStop = destinationStop;
    }

    public void setDestinationStopNo(String destinationStopNo) {
        this.destinationStopNo = destinationStopNo;
    }

    public void setDestinationStopId(String destinationStopId) {
        this.destinationStopId = destinationStopId;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void setAlarmTerm(String alarmTerm) {
        this.alarmTerm = alarmTerm;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setOn(int on) {
        isOn = on;
    }

    public int getOn() {
        return isOn;
    }

    public String getDeparturePlaceName() {
        return departurePlaceName;
    }

    public String getDepartureStop() {
        return departureStop;
    }

    public String getDepartureStopNo() {
        return departureStopNo;
    }

    public String getDepartureStopId() {
        return departureStopId;
    }

    public String getDestinationPlaceName() {
        return destinationPlaceName;
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

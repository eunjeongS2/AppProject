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
    private String departurePlaceLongitude;
    private String departurePlaceLatitude;

    private String departureStop;
    private String departureNo;
    private String departureId;
    private String destinationPlace;
    private String destinationPlaceLongitude;
    private String destinationPlaceLatitude;

    private String destinationStop;
    private String destinationNo;
    private String destinationId;

    private String busName;
    private String busId;
    private String arriveTime;
    private String alarmTerm;
    private Boolean isOn;

    public String getBusName() {
        return busName;
    }

    public Alarm() {
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

    public String getDeparturePlaceLongitude() {
        return departurePlaceLongitude;
    }

    public String getDeparturePlaceLatitude() {
        return departurePlaceLatitude;
    }

    public String getDestinationPlaceLongitude() {
        return destinationPlaceLongitude;
    }

    public String getDestinationPlaceLatitude() {
        return destinationPlaceLatitude;
    }

    public String getDestinationNo() {
        return destinationNo;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public void setDeparturePlaceLongitude(String departurePlaceLongitude) {
        this.departurePlaceLongitude = departurePlaceLongitude;
    }

    public void setDeparturePlaceLatitude(String departurePlaceLatitude) {
        this.departurePlaceLatitude = departurePlaceLatitude;
    }

    public void setDepartureStop(String departureStop) {
        this.departureStop = departureStop;
    }

    public void setDepartureNo(String departureNo) {
        this.departureNo = departureNo;
    }

    public void setDepartureId(String departureId) {
        this.departureId = departureId;
    }

    public void setDestinationPlace(String destinationPlace) {
        this.destinationPlace = destinationPlace;
    }

    public void setDestinationPlaceLongitude(String destinationPlaceLongitude) {
        this.destinationPlaceLongitude = destinationPlaceLongitude;
    }

    public void setDestinationPlaceLatitude(String destinationPlaceLatitude) {
        this.destinationPlaceLatitude = destinationPlaceLatitude;
    }

    public void setDestinationStop(String destinationStop) {
        this.destinationStop = destinationStop;
    }

    public void setDestinationNo(String destinationNo) {
        this.destinationNo = destinationNo;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
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

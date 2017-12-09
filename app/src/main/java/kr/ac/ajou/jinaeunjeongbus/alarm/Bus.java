package kr.ac.ajou.jinaeunjeongbus.alarm;


public class Bus {
    private String Id;
    private String number;
    private String description;
    private String position;
    private String local;
    private String route;
    private String timeInfo;
    private String termInfo;

    public Bus(){
    }

    public Bus(String number, String description) {
        this.number = number;
        this.description = description;

    }
    public Bus(String number, String description, String local, String route, String timeInfo, String termInfo) {
        this.number = number;
        this.description = description;
        this.local = local;
        this.route = route;
        this.timeInfo = timeInfo;
        this.termInfo = termInfo;
    }

    public Bus(String number, String description, String position) {
        this.number = number;
        this.description = description;
        this.position = position;
    }

    public String getId() {
        return Id;
    }

    public String getLocal() {
        return local;
    }

    public String getRoute() {
        return route;
    }

    public String getTimeInfo() {
        return timeInfo;
    }

    public String getTermInfo() {
        return termInfo;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public String getPosition() {
        return position;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setTimeInfo(String timeInfo) {
        this.timeInfo = timeInfo;
    }

    public void setTermInfo(String termInfo) {
        this.termInfo = termInfo;
    }
}

package kr.ac.ajou.jinaeunjeongbus.alarm;

public final class Bus {
    private String number;
    private String description;

    public Bus(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }
}

package kr.ac.ajou.jinaeunjeongbus.dataParse;


public class Address {
    private String addressName;
    private String addressLatitude;
    private String addressLongitude;

    public Address(String addressName, String addressLatitude, String addressLongitude) {
        this.addressName = addressName;
        this.addressLatitude = addressLatitude;
        this.addressLongitude = addressLongitude;
    }

    public String getAddressName() {
        return addressName;
    }

    public String getAddressLatitude() {
        return addressLatitude;
    }

    public String getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public void setAddressLatitude(String addressLatitude) {
        this.addressLatitude = addressLatitude;
    }

    public void setAddressLongitude(String addressLongitude) {
        this.addressLongitude = addressLongitude;
    }
}

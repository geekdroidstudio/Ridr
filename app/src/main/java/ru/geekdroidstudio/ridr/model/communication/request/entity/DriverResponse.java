package ru.geekdroidstudio.ridr.model.communication.request.entity;

public class DriverResponse {

    private String driverId;
    private String passengerId;
    private Boolean accept;

    public DriverResponse(String driverId, String passengerId, Boolean accept) {
        this.driverId = driverId;
        this.passengerId = passengerId;
        this.accept = accept;
    }

    public DriverResponse() {
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}

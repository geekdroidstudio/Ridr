package geekdroidstudio.ru.ridr.model.entity.users;

public class DriverResponse {
    private PassengerRequest passengerRequest;
    private Boolean accept;

    public DriverResponse(PassengerRequest passengerRequest, Boolean accept) {
        this.passengerRequest = passengerRequest;
        this.accept = accept;
    }

    public DriverResponse() {
    }

    public PassengerRequest getPassengerRequest() {
        return passengerRequest;
    }

    public void setPassengerRequest(PassengerRequest passengerRequest) {
        this.passengerRequest = passengerRequest;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }
}

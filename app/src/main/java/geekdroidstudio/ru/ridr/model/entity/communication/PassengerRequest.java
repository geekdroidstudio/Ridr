package geekdroidstudio.ru.ridr.model.entity.communication;

public class PassengerRequest {

    private String driverId;
    private String passengerId;
    private SimpleRoute simpleRoute;

    public PassengerRequest(String passengerId, String driverId, SimpleRoute simpleRoute) {
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.simpleRoute = simpleRoute;
    }

    public PassengerRequest() {
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public SimpleRoute getSimpleRoute() {
        return simpleRoute;
    }

    public void setSimpleRoute(SimpleRoute simpleRoute) {
        this.simpleRoute = simpleRoute;
    }
}

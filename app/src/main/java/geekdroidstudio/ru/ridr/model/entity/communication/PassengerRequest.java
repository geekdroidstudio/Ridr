package geekdroidstudio.ru.ridr.model.entity.communication;

import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;

public class PassengerRequest {

    private String passengerId;
    private String driverId;

    private Coordinate start;
    private Coordinate finish;

    public PassengerRequest(String passengerId, String driverId, Coordinate start, Coordinate finish) {
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.start = start;
        this.finish = finish;
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

    public Coordinate getStart() {
        return start;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }

    public Coordinate getFinish() {
        return finish;
    }

    public void setFinish(Coordinate finish) {
        this.finish = finish;
    }
}

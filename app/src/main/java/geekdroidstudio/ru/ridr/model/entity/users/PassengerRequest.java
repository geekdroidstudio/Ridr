package geekdroidstudio.ru.ridr.model.entity.users;

public class PassengerRequest {

    private String passengerId;
    private String driverId;

    private CoordinatePoint start;
    private CoordinatePoint finish;

    public PassengerRequest(String passengerId, String driverId, CoordinatePoint start, CoordinatePoint finish) {
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

    public CoordinatePoint getStart() {
        return start;
    }

    public void setStart(CoordinatePoint start) {
        this.start = start;
    }

    public CoordinatePoint getFinish() {
        return finish;
    }

    public void setFinish(CoordinatePoint finish) {
        this.finish = finish;
    }
}

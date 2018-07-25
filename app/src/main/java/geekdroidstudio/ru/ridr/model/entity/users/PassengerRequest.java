package geekdroidstudio.ru.ridr.model.entity.users;

public class PassengerRequest {

    private String passengerId;
    private String driverId;

    private Point start;
    private Point finish;

    public PassengerRequest(String passengerId, String driverId, Point start, Point finish) {
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

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getFinish() {
        return finish;
    }

    public void setFinish(Point finish) {
        this.finish = finish;
    }
}

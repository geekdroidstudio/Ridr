package geekdroidstudio.ru.ridr.model.communication.request.entity;

import geekdroidstudio.ru.ridr.model.communication.location.entity.DualCoordinateRoute;

public class PassengerRequest {

    private String driverId;
    private String passengerId;
    private DualCoordinateRoute dualCoordinateRoute;

    public PassengerRequest(String passengerId, String driverId, DualCoordinateRoute dualCoordinateRoute) {
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.dualCoordinateRoute = dualCoordinateRoute;
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

    public DualCoordinateRoute getDualCoordinateRoute() {
        return dualCoordinateRoute;
    }

    public void setDualCoordinateRoute(DualCoordinateRoute dualCoordinateRoute) {
        this.dualCoordinateRoute = dualCoordinateRoute;
    }
}

package ru.geekdroidstudio.ridr.model.communication.location.entity;

public class DualRoute {
    private DualTextRoute textRoute;
    private DualCoordinateRoute coordinateRoute;

    public DualRoute() {
    }

    public DualRoute(DualTextRoute textRoute, DualCoordinateRoute coordinateRoute) {
        this.textRoute = textRoute;
        this.coordinateRoute = coordinateRoute;
    }

    public DualTextRoute getTextRoute() {
        return textRoute;
    }

    public void setTextRoute(DualTextRoute textRoute) {
        this.textRoute = textRoute;
    }

    public DualCoordinateRoute getCoordinateRoute() {
        return coordinateRoute;
    }

    public void setCoordinateRoute(DualCoordinateRoute coordinateRoute) {
        this.coordinateRoute = coordinateRoute;
    }

    @Override
    public String toString() {
        return "{" + textRoute + ", " + coordinateRoute + "}";
    }
}

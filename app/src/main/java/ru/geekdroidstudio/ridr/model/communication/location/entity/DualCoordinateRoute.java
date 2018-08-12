package ru.geekdroidstudio.ridr.model.communication.location.entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class DualCoordinateRoute {

    private Coordinate start;
    private Coordinate finish;

    public DualCoordinateRoute() {
    }

    public DualCoordinateRoute(Coordinate start, Coordinate finish) {
        this.start = start;
        this.finish = finish;
    }

    public DualCoordinateRoute(LatLng latLngStart, LatLng latLngFinish) {
        start = coordinateFromLatLng(latLngStart);
        finish = coordinateFromLatLng(latLngFinish);
    }

    public DualCoordinateRoute(List<LatLng> latLngArray) {
        this(latLngArray.get(0), latLngArray.get(latLngArray.size() - 1));
    }

    private Coordinate coordinateFromLatLng(LatLng latLng) {
        return new Coordinate(latLng.latitude, latLng.longitude);
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

    @Override
    public String toString() {
        return "{start=" + start +
                ", finish=" + finish + '}';
    }
}

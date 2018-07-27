package geekdroidstudio.ru.ridr.model.communication.entity;

import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;

public class UserLocation {
    private String id;
    private Coordinate coordinate;

    public UserLocation() {
    }

    public UserLocation(String id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
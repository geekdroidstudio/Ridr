package geekdroidstudio.ru.ridr.model.entity.users;

public class Location {

    private String keyId;

    private CoordinatePoint coordinatePoint;

    public Location() {
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public CoordinatePoint getCoordinatePoint() {
        return coordinatePoint;
    }

    public void setCoordinatePoint(CoordinatePoint coordinatePoint) {
        this.coordinatePoint = coordinatePoint;
    }
}

package geekdroidstudio.ru.ridr.model.entity.users;

public class Location {

    private String keyId;

    private Point point;

    public Location() {
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}

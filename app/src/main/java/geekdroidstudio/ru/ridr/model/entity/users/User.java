package geekdroidstudio.ru.ridr.model.entity.users;

abstract class User {

    private String id;

    private String name;

    private CoordinatePoint coordinatePoint;

    User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinatePoint getCoordinatePoint() {
        return coordinatePoint;
    }

    public void setCoordinatePoint(CoordinatePoint coordinatePoint) {
        this.coordinatePoint = coordinatePoint;
    }
}

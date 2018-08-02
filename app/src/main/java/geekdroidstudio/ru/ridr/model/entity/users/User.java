package geekdroidstudio.ru.ridr.model.entity.users;

import com.google.firebase.database.PropertyName;

public class User {

    private String id;

    private String name;

    private Coordinate location;

    User() {

    }

    User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("userName")
    public String getName() {
        return name;
    }

    @PropertyName("userName")
    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "{id:" + getId() + ", name:" + getName() + ", loc:\n" + getLocation() + "}";
    }
}

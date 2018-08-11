package ru.geekdroidstudio.ridr.model.communication.entity;

import com.google.firebase.database.PropertyName;

import ru.geekdroidstudio.ridr.model.communication.location.entity.Coordinate;

public class User {

    private static final String USER_NAME_FIELD = "userName";

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

    @PropertyName(USER_NAME_FIELD)
    public String getName() {
        return name;
    }

    @PropertyName(USER_NAME_FIELD)
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

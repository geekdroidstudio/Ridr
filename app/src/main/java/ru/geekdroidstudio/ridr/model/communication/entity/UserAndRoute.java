package ru.geekdroidstudio.ridr.model.communication.entity;

import ru.geekdroidstudio.ridr.model.communication.location.entity.DualRoute;

public class UserAndRoute<T extends User> {

    private T user;
    private DualRoute dualRoute;

    public UserAndRoute() {
    }

    public UserAndRoute(T user, DualRoute dualRoute) {
        this.user = user;
        this.dualRoute = dualRoute;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public DualRoute getDualRoute() {
        return dualRoute;
    }

    public void setDualRoute(DualRoute dualRoute) {
        this.dualRoute = dualRoute;
    }
}

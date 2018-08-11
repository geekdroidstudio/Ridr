package geekdroidstudio.ru.ridr.model.entity.users;

import geekdroidstudio.ru.ridr.model.entity.routes.DualRoute;

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

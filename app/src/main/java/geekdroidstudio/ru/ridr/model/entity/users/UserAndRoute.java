package geekdroidstudio.ru.ridr.model.entity.users;

import geekdroidstudio.ru.ridr.model.entity.communication.SimpleRoute;

public class UserAndRoute<T extends User> {

    private T user;
    private SimpleRoute simpleRoute;

    public UserAndRoute() {
    }

    public UserAndRoute(T user, SimpleRoute simpleRoute) {
        this.user = user;
        this.simpleRoute = simpleRoute;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public SimpleRoute getSimpleRoute() {
        return simpleRoute;
    }

    public void setSimpleRoute(SimpleRoute simpleRoute) {
        this.simpleRoute = simpleRoute;
    }
}

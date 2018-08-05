package geekdroidstudio.ru.ridr.model.entity.communication;

import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;

public class SimpleRoute {

    private Coordinate start;
    private Coordinate finish;

    public SimpleRoute() {
    }

    public SimpleRoute(Coordinate start, Coordinate finish) {
        this.start = start;
        this.finish = finish;
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
}

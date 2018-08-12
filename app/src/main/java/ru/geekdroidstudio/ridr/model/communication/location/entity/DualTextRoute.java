package ru.geekdroidstudio.ridr.model.communication.location.entity;

public class DualTextRoute {
    private String start;
    private String finish;

    public DualTextRoute() {
    }

    public DualTextRoute(String start, String finish) {
        this.start = start;
        this.finish = finish;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "{start=" + start + '\'' +
                ", finish='" + finish + "\'}";
    }
}

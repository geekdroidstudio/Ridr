package geekdroidstudio.ru.ridr.model.communication.entity;

public class Driver extends User {

    public Driver() {
    }

    public Driver(String id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Driver" + super.toString();
    }
}

package geekdroidstudio.ru.ridr.model.entity.users;

public class Passenger extends User {
    public Passenger() {
    }

    public Passenger(String id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Passenger" + super.toString();
    }
}

package geekdroidstudio.ru.ridr.model.entity.users;

abstract class User {

    private String keyId;

    private String name;

    private Point point;

    User() {

    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

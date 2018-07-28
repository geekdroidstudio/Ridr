package geekdroidstudio.ru.ridr.model.entity;

public class User {
	private String id;

	private String name;

	private String email;
	public User() {
	}

	public User(String id, String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




}

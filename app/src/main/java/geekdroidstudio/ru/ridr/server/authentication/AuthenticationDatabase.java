package geekdroidstudio.ru.ridr.server.authentication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import geekdroidstudio.ru.ridr.model.entity.User;
import timber.log.Timber;

public class AuthenticationDatabase {

	public static final String BOOK_AUTHENTICATION = "authentication";

	private FirebaseDatabase firebaseDatabase;
	private DatabaseReference databaseReference;

	protected AuthenticationDatabase() {

		firebaseDatabase = FirebaseDatabase.getInstance();
		databaseReference = firebaseDatabase.getReference();
	}

	//добавление пользователя в базу
	public void addUser(String userId, String userName, String userEmail) {
		if (userName != null) {
			Timber.d("addUser: " + userId + " " + userName);
			databaseReference.child(BOOK_AUTHENTICATION).child(userId).child("name").setValue(userName);
			databaseReference.child(BOOK_AUTHENTICATION).child(userId).child("email").setValue(userEmail);
		}
	}

	public User getUser(String userId){

		Timber.d(databaseReference.child(BOOK_AUTHENTICATION).child(userId).getKey());

		return new User();
	}


}

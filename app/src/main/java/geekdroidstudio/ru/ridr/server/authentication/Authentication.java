package geekdroidstudio.ru.ridr.server.authentication;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import timber.log.Timber;

public class Authentication {

	private FirebaseAuth firebaseAuth;
	private FirebaseAuth.AuthStateListener authStateListener;
	@Inject
	AuthDatabase authDatabase;
	private IAuthentication iAuthentication;


	public Authentication(AuthDatabase authDatabase) {
		firebaseAuth = FirebaseAuth.getInstance();
		this.authDatabase = authDatabase;
		//authDatabase = new AuthDatabase();
	}

	public interface IAuthentication {
		//пользователь вошёл
		void wasSignIn();

		//пользователь не смог войти
		void wasNotSignIn();

		//пользователь зарегистрировался
		void wasSignUp();

		//пользователь не смог зарегистрироваться
		void wasNotSignUp();

		//не валидные поля
		void invalidFields();
	}

	public void setContext(Context context) {
		iAuthentication = (IAuthentication) context;
	}

	//вход пользователя
	public void signIn(String userEmail, String userPassword) {

		if (validateString(userEmail, userPassword)) {
			firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(
					new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							if (task.isSuccessful()) {
								Timber.d("onComplete: wasSignIn()");
								AuthDatabase.setUserId(firebaseAuth.getUid());
								iAuthentication.wasSignIn();
							} else {
								Timber.d("onComplete: wasSignIn()");
								iAuthentication.wasNotSignIn();
							}
						}
					});
		} else {
			iAuthentication.invalidFields();
		}

	}

	//регистрация пользователя
	public void signUp(final String userName, final String userEmail, String userPassword) {

		if (validateString(userName, userEmail, userPassword)) {

			firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(
					new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							if (task.isSuccessful()) {
								authDatabase.addUser(firebaseAuth.getUid(), userName, userEmail);
								Timber.d("onComplete: wasSignUp()");
								iAuthentication.wasSignUp();
							} else {
								Timber.d("onComplete: wasNotSignUp()");
								iAuthentication.wasNotSignUp();
							}
						}
					});
		} else {
			iAuthentication.invalidFields();
		}

	}

	public boolean validateString(String... strings) {
		for (String str : strings) {
			if (str.isEmpty()) {
				return false;
			}
		}
		return true;
	}
}

package geekdroidstudio.ru.ridr.server.authentication;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import timber.log.Timber;

public class Authentication {

	private FirebaseAuth firebaseAuth;
	private FirebaseAuth.AuthStateListener authStateListener;
	private AuthenticationDatabase authenticationDatabase;
	private IAuthentication iAuthentication;

	public Authentication() {
		firebaseAuth = FirebaseAuth.getInstance();
		authenticationDatabase = new AuthenticationDatabase();
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
	}

	public void setContext(Context context){
		iAuthentication = (IAuthentication) context;
	}

	//вход пользователя
	public void signIn(String email, String password) {
		firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
				new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Timber.d("onComplete: wasSignIn()");
							iAuthentication.wasSignIn();
						} else {
							Timber.d("onComplete: wasSignIn()");
							iAuthentication.wasNotSignIn();
						}
					}
				});
	}

	//регистрация пользователя
	public void signUp(final String userName, final String userEmail, String userPassword) {
		firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(
				new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							authenticationDatabase.addUser(firebaseAuth.getUid(), userName, userEmail);
							Timber.d("onComplete: wasSignUp()");
							iAuthentication.wasSignUp();
						} else {
							Timber.d("onComplete: wasNotSignUp()");
							iAuthentication.wasNotSignUp();
						}
					}
				});
	}
	
	public void getUserData(String userId){
		authenticationDatabase.getUser(userId);
	}
}

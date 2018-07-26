package geekdroidstudio.ru.ridr.server.authentication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import geekdroidstudio.ru.ridr.view.passMainScreen.PassMainActivity;
import timber.log.Timber;

public class Authentication {

	private FirebaseAuth firebaseAuth;
	private FirebaseAuth.AuthStateListener authStateListener;
	private PassMainActivity context;
	private IAuthentication iAuthentication;
	private AuthenticationDatabase authenticationDatabase;

	public Authentication(PassMainActivity context) {
		this.context = context;
		iAuthentication = context;
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

	//вход пользователя
	public void signIn(String email, String password) {
		firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(context,
				new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Timber.d("onComplete: wasSignIn()");
							context.wasSignIn();
						} else {
							Timber.d("onComplete: wasSignIn()");
							context.wasNotSignIn();
						}
					}
				});
	}

	//регистрация пользователя
	public void signUp(final String name, final String email, String password) {
		firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(context,
				new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							authenticationDatabase.addUser(firebaseAuth.getUid(), name, email);
							Timber.d("onComplete: wasSignUp()");
							context.wasSignUp();
						} else {
							Timber.d("onComplete: wasNotSignUp()");
							context.wasNotSignUp();
						}
					}
				});
	}
}

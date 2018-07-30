package geekdroidstudio.ru.ridr.server.authentication;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpAppCompatFragment;
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
	private IAuthenticationSignIn iAuthenticationSignIn;
	private IAuthenticationSignUp iAuthenticationSignUp;


	public Authentication(AuthDatabase authDatabase) {
		firebaseAuth = FirebaseAuth.getInstance();
		this.authDatabase = authDatabase;
		//authDatabase = new AuthDatabase();
	}

	public interface IAuthenticationSignIn {
		//пользователь вошёл
		void wasSignIn();

		//пользователь не смог войти
		void wasNotSignIn();

	}

	public interface IAuthenticationSignUp {
		//пользователь вошёл
		void wasSignUp();

		//пользователь не смог войти
		void wasNotSignUp();

	}

	public void setContextSignIn(MvpAppCompatFragment fragment) {
		iAuthenticationSignIn = (IAuthenticationSignIn) fragment;
	}

	public void setContextSignUp(MvpAppCompatFragment fragment) {
		iAuthenticationSignUp = (IAuthenticationSignUp) fragment;
	}

	//вход пользователя
	public void signIn(String userEmail, String userPassword) {


		firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(
				new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Timber.d("onComplete: wasSignIn()");
							AuthDatabase.setUserId(firebaseAuth.getUid());
							iAuthenticationSignIn.wasSignIn();
						} else {
							Timber.d("onComplete: wasSignIn()");
							//iAuthenticationSignIn.wasNotSignIn();
						}
					}
				});


	}

	//регистрация пользователя
	public void signUp(final String userName, final String userEmail, String userPassword) {

		Timber.d("signUp");


		firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(
				new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							authDatabase.addUser(firebaseAuth.getUid(), userName, userEmail);
							Timber.d("onComplete: wasSignUp()");
							iAuthenticationSignUp.wasSignUp();
						} else {
							Timber.d("onComplete: wasNotSignUp()");
							iAuthenticationSignUp.wasNotSignUp();
						}
					}
				});


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

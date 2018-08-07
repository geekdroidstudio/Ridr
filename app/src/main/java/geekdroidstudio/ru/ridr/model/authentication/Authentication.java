package geekdroidstudio.ru.ridr.model.authentication;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import timber.log.Timber;

public class Authentication {

    @Inject
    AuthDatabase authDatabase;
    private FirebaseAuth firebaseAuth;
    private IAuthenticationSignIn iAuthenticationSignIn;
    private IAuthenticationSignUp iAuthenticationSignUp;


    public Authentication(AuthDatabase authDatabase) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.authDatabase = authDatabase;
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
                task -> {
                    if (task.isSuccessful()) {
                        Timber.d("onComplete: wasSignIn()");
                        AuthDatabase.setUserId(firebaseAuth.getUid());
                        iAuthenticationSignIn.wasSignIn();
                    } else {
                        Timber.d("onComplete: wasNotSignIn()");
                        iAuthenticationSignIn.wasNotSignIn();
                    }
                });


    }

    //регистрация пользователя
    public void signUp(String userName, String userEmail, String userPassword, String userStatus) {

        Timber.d("signUp");

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        authDatabase.addUser(firebaseAuth.getUid(), userName, userEmail, userStatus);
                        Timber.d("onComplete: wasSignUp()");
                        iAuthenticationSignUp.wasSignUp();
                    } else {
                        Timber.d("onComplete: wasNotSignUp()");
                        iAuthenticationSignUp.wasNotSignUp();
                    }
                });


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
}
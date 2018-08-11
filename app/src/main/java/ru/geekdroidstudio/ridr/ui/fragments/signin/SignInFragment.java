package ru.geekdroidstudio.ridr.ui.fragments.signin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.geekdroidstudio.ridr.App;
import ru.geekdroidstudio.ridr.R;
import ru.geekdroidstudio.ridr.model.authentication.Authentication;
import ru.geekdroidstudio.ridr.ui.activities.user.authentication.AuthenticationView;
import timber.log.Timber;

public class SignInFragment extends MvpAppCompatFragment implements
        SignInView, Authentication.IAuthenticationSignIn {

    @Inject
    Authentication authentication;

    @BindView(R.id.edit_text_email)
    TextInputEditText editTextEmail;
    @BindView(R.id.edit_text_password)
    TextInputEditText editTextPassword;
    @BindView(R.id.button_choose_driver)
    AppCompatButton buttonChooseDriver;
    @BindView(R.id.button_choose_passenger)
    AppCompatButton buttonChoosePassenger;

    private Unbinder unbinder;
    private SignInFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    public SignInFragment() {
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignInFragment.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (SignInFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.error_implement_fragment_interaction_listener));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        unbinder = ButterKnife.bind(this, view);

        App.getInstance().getComponent().inject(this);
        authentication.setContextSignIn(this);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.button_choose_passenger)
    @Override
    public void onClickSignInPassenger() {
        ((AuthenticationView) getActivity()).onPassengerSingingIn();
        doSignIn();
    }

    @OnClick(R.id.button_choose_driver)
    @Override
    public void onClickSignInDriver() {
        ((AuthenticationView) getActivity()).onDriverSingingIn();
        doSignIn();
    }

    private void doSignIn() {
        String userEmail = editTextEmail.getText().toString();
        String userPassword = editTextPassword.getText().toString();

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(getContext(), R.string.authorisation_error_text, Toast.LENGTH_SHORT).show();
        } else {
            authentication.signIn(userEmail, userPassword);
        }
    }

    @OnClick(R.id.text_view_sign_up)
    @Override
    public void onClickSignUp() {
        Timber.d("onClickSignUp()");
        onFragmentInteractionListener.changeFragmentToRegistration();
    }

    public interface OnFragmentInteractionListener {
        void changeFragmentToRegistration();
    }

    @Override
    public void wasSignIn(String userId) {
        Timber.d("wasSignIn()");
        ((AuthenticationView) getActivity()).onSignedIn(userId);

    }

    @Override
    public void wasNotSignIn() {
        Toast.makeText(getContext(), R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show();
    }


}
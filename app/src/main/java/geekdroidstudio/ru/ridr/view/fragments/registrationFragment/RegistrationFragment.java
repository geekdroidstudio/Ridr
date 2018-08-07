package geekdroidstudio.ru.ridr.view.fragments.registrationFragment;

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
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.model.authentication.Authentication;
import timber.log.Timber;

public class RegistrationFragment extends MvpAppCompatFragment implements RegistrationView, Authentication.IAuthenticationSignUp {
    @BindView(R.id.edit_text_phone_email)
    TextInputEditText editTextPhoneEmail;
    @BindView(R.id.edit_text_registration_password)
    TextInputEditText editTextRegistrationPassword;
    @BindView(R.id.button_sign_up)
    AppCompatButton buttonSignUp;

    @Inject Authentication authentication;

    private Unbinder unbinder;
    private RegistrationFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    public RegistrationFragment(){}

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegistrationFragment.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (RegistrationFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.error_implement_fragment_interaction_listener));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        unbinder = ButterKnife.bind(this, view);

        App.getInstance().getComponent().inject(this);
        authentication.setContextSignUp(this);
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

    public interface OnFragmentInteractionListener {
        void startDriverActivity();
        void startPassengerActivity();
    }

    @OnClick(R.id.button_sign_up)
    @Override
    public void doSignUp() {
        String phoneEmail = editTextPhoneEmail.getText().toString();
        String registrationPassword = editTextRegistrationPassword.getText().toString();
        Timber.d("doSignUp()");

        if(phoneEmail.isEmpty() || registrationPassword.isEmpty()){
            Toast.makeText(getContext(),R.string.registration_error_text, Toast.LENGTH_SHORT).show();

        }else {
            authentication.signUp("noName", phoneEmail, registrationPassword, "driver");

        }
    }

    @Override
    public void wasSignUp() {
        Timber.d("wasSignUp()");
        onFragmentInteractionListener.startDriverActivity();
    }

    @Override
    public void wasNotSignUp() {
        Timber.d("wasNotSignUp()");
    }

}

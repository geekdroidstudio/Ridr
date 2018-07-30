package geekdroidstudio.ru.ridr.view.fragments.registrationFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.server.authentication.Authentication;
import timber.log.Timber;

public class RegistrationFragment extends MvpAppCompatFragment implements RegistrationView, Authentication.IAuthenticationSignUp {
    @BindView(R.id.edit_text_phone_email)
    TextInputEditText editTextPhoneEmail;
    @BindView(R.id.button_choose_driver)
    AppCompatButton buttonChooseDriver;
    @BindView(R.id.button_choose_passenger)
    AppCompatButton buttonChoosePassenger;
    @BindView(R.id.edit_text_registration_password) EditText editTextRegistrationPassword;

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

        App.getComponent().inject(this);
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

    @OnClick(R.id.button_choose_driver)
    @Override
    public void chooseRoleDriver() {
        String phoneEmail = editTextPhoneEmail.getText().toString();
        if(phoneEmail.isEmpty()){
            Toast.makeText(getContext(),R.string.registration_error_text, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),R.string.driver_success_text, Toast.LENGTH_SHORT).show();
            //presenter.loginDriver();
            onFragmentInteractionListener.startDriverActivity();
        }
    }

    @OnClick(R.id.button_choose_passenger)
    @Override
    public void chooseRolePassenger() {

        String phoneEmail = editTextPhoneEmail.getText().toString();
        String registrationPassword = editTextRegistrationPassword.getText().toString();
        Timber.d("chooseRolePassenger()");


        if(phoneEmail.isEmpty() || registrationPassword.isEmpty()){
            Toast.makeText(getContext(),R.string.registration_error_text, Toast.LENGTH_SHORT).show();

        }else {
            //Toast.makeText(getContext(),R.string.passenger_success_text, Toast.LENGTH_SHORT).show();
            //presenter.loginPassenger();
            authentication.signUp("noName", phoneEmail, registrationPassword);

        }
    }

    @Override
    public void wasSignUp() {
        onFragmentInteractionListener.startPassengerActivity();
    }

    @Override
    public void wasNotSignUp() {

    }

}

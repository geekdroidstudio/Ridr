package ru.geekdroidstudio.ridr.ui.fragments.registration;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
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
import timber.log.Timber;

public class RegistrationFragment extends MvpAppCompatFragment implements RegistrationView, Authentication.IAuthenticationSignUp {
    @BindView(R.id.edit_text_user_name)
    TextInputEditText editTextUserName;
    @BindView(R.id.edit_text_phone_email)
    TextInputEditText editTextPhoneEmail;
    @BindView(R.id.edit_text_registration_password)
    TextInputEditText editTextRegistrationPassword;
    @BindView(R.id.button_sign_up)
    AppCompatButton buttonSignUp;
    @BindView(R.id.switch_passenger_or_driver)
    Switch switchPassengerOrDriver;

    @Inject
    Authentication authentication;

    private Unbinder unbinder;
    private RegistrationFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    public RegistrationFragment() {
    }

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

        addListenerToSwitch();

        return view;
    }

    private void addListenerToSwitch() {
        switchPassengerOrDriver.setOnCheckedChangeListener((compoundButton, b) -> {
            if (switchPassengerOrDriver.isChecked()) {
                switchPassengerOrDriver.setText("Водитель");
            } else if (!switchPassengerOrDriver.isChecked()) {
                switchPassengerOrDriver.setText("Пассажир");

            }
        });
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

    @OnClick(R.id.button_sign_up)
    @Override
    public void doSignUp() {
        String userName = editTextUserName.getText().toString();
        String phoneEmail = editTextPhoneEmail.getText().toString();
        String registrationPassword = editTextRegistrationPassword.getText().toString();
        Timber.d("doSignUp()");


        if (phoneEmail.isEmpty() || registrationPassword.isEmpty()) {
            Toast.makeText(getContext(), R.string.registration_error_text, Toast.LENGTH_SHORT).show();

        } else {
            if (switchPassengerOrDriver.isChecked()) {
                authentication.signUp(userName, phoneEmail, registrationPassword, "driver");
            } else if (!switchPassengerOrDriver.isChecked()) {
                authentication.signUp(userName, phoneEmail, registrationPassword, "passenger");
            }
        }
    }

    @Override
    public void wasSignUp(String userId, String userStatus) {
        Timber.d("wasSignUp()");
        if (userStatus.equals("driver")) {
            onFragmentInteractionListener.startDriverActivity(userId);
        } else if (userStatus.equals("passenger")) {
            onFragmentInteractionListener.startPassengerActivity(userId);
        }
    }

    public interface OnFragmentInteractionListener {
        void startDriverActivity(String userId);

        void startPassengerActivity(String userId);
    }

    @Override
    public void wasNotSignUp() {
        Timber.d("wasNotSignUp()");
    }

}

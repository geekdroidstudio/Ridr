package geekdroidstudio.ru.ridr.view.fragments.startAuthenticationFragment;

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
import geekdroidstudio.ru.ridr.server.authentication.Authentication;
import geekdroidstudio.ru.ridr.view.userMainScreen.UserMainActivity;
import timber.log.Timber;

public class StartAuthenticationFragment extends MvpAppCompatFragment implements StartAuthenticationView, Authentication.IAuthenticationSignIn {

    @Inject Authentication authentication;

    @BindView(R.id.edit_text_email)
    TextInputEditText editTextEmail;
    @BindView(R.id.edit_text_password)
    TextInputEditText editTextPassword;
    @BindView(R.id.button_sign_in)
    AppCompatButton buttonEnter;

    private Unbinder unbinder;
    private StartAuthenticationFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    public StartAuthenticationFragment(){}

    public static StartAuthenticationFragment newInstance() {
        return new StartAuthenticationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StartAuthenticationFragment.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (StartAuthenticationFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.error_implement_fragment_interaction_listener));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication_start, container, false);
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

    public interface OnFragmentInteractionListener {
        void changeFragmentToRegistration();
        void startDriverActivity();
    }

    @OnClick(R.id.button_sign_in)
	@Override
	public void onClickSignIn(){
		String userEmail = editTextEmail.getText().toString();
		String userPassword =  editTextPassword.getText().toString();

		if(userEmail.isEmpty() || userPassword.isEmpty()){
			Toast.makeText(getContext(),R.string.authorisation_error_text, Toast.LENGTH_SHORT).show();
		}else {
			//presenter.loginUser(login,password);
			authentication.signIn(userEmail, userPassword);

		}
	}

	@OnClick(R.id.button_sign_up)
	@Override
	public void onClickSignUp(){
    	Timber.d("onClickSignUp()");
		onFragmentInteractionListener.changeFragmentToRegistration();
	}

    @Override
    public void wasSignIn() {
        Timber.d("wasSignIn()");
		((UserMainActivity)getActivity()).launchPassengerActivity();
    }

    @Override
    public void wasNotSignIn() {
        Toast.makeText(getContext(),R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show();
    }


}
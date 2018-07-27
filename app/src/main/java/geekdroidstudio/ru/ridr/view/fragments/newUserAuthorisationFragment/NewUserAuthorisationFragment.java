package geekdroidstudio.ru.ridr.view.fragments.newUserAuthorisationFragment;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import geekdroidstudio.ru.ridr.R;

public class NewUserAuthorisationFragment extends MvpAppCompatFragment implements NewUserAuthorisationView {
    @BindView(R.id.edit_text_login)
    TextInputEditText editTextLogin;
    @BindView(R.id.edit_text_password)
    TextInputEditText editTextPassword;
    @BindView(R.id.button_enter)
    AppCompatButton buttonEnter;

    private Unbinder unbinder;
    private NewUserAuthorisationFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    public NewUserAuthorisationFragment(){}

    public static NewUserAuthorisationFragment newInstance() {
        return new NewUserAuthorisationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewUserAuthorisationFragment.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (NewUserAuthorisationFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.error_implement_frag_interact_list));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authorisation_start, container, false);
        unbinder = ButterKnife.bind(this, view);
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

    }

    @OnClick(R.id.button_enter)
    @Override
    public void finishRegistration(){
        String login = editTextLogin.getText().toString();
        String password =  editTextPassword.getText().toString();
        if(login.isEmpty() || password.isEmpty()){
            Toast.makeText(getContext(),R.string.authorisation_error_text, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),R.string.authorisation_success_text, Toast.LENGTH_SHORT).show();
            //presenter.loginUser(login,password);
        }
    }
}
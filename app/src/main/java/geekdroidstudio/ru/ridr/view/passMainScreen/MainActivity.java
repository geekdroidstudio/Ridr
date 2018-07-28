package geekdroidstudio.ru.ridr.view.passMainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.server.authentication.Authentication;

public class MainActivity extends AppCompatActivity implements Authentication.IAuthentication{

	@BindView (R.id.edit_text_name) EditText editTextName;
	@BindView(R.id.edit_text_email) EditText editTextEmail;
	@BindView(R.id.edit_text_password) EditText editTextPassword;

	@Inject
	Authentication authentication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);

		//пробные, чтобы не вводить каждый раз
		editTextName.setText("Leonid");
		editTextEmail.setText("qw@qw.qw");
		editTextPassword.setText("123456");

		//обязательно заинжектить и передать контекст
		App.getComponent().inject(this);
		authentication.setContext(this);

	}

	@OnClick({R.id.button_sign_in, R.id.button_sign_up})
	public void onClick(View view) {
		if (view.getId() == R.id.button_sign_in) {
			authentication.signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
		} else {
			authentication.signUp(editTextName.getText().toString(), editTextEmail.getText().toString(), editTextPassword.getText().toString());
		}
	}


	@Override
	public void wasSignIn() {
		Toast.makeText(MainActivity.this, "sign in is successful", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MainActivity.this, EnteredActivity.class);
		startActivity(intent);
	}


	@Override
	public void wasNotSignIn() {
		Toast.makeText(MainActivity.this, "sign up, please", Toast.LENGTH_SHORT).show();
	}


	@Override
	public void wasSignUp() {
		Toast.makeText(MainActivity.this, "sign up is successful", Toast.LENGTH_SHORT).show();
	}


	@Override
	public void wasNotSignUp() {
		Toast.makeText(MainActivity.this, "sign up is not successful", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void invalidFields() {
		Toast.makeText(MainActivity.this, "for sign in: email, password\n for sign up: name, email, password", Toast.LENGTH_SHORT).show();
	}
}

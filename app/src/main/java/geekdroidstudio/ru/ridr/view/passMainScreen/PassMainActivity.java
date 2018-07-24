package geekdroidstudio.ru.ridr.view.passMainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.server.Database;
import timber.log.Timber;

public class PassMainActivity extends AppCompatActivity {

	@BindView(R.id.edit_text_email) EditText editTextEmail;
	@BindView(R.id.edit_text_password) EditText editTextPassword;

	private FirebaseAuth firebaseAuth;
	private FirebaseAuth.AuthStateListener authStateListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ButterKnife.bind(this);

		//пробные, чтобы не вводить каждый раз
		editTextEmail.setText("qw@qw.qw");
		editTextPassword.setText("123456");

		firebaseAuth = FirebaseAuth.getInstance();

	}

	@OnClick({R.id.button_sign_in, R.id.button_sign_up})
	public void onClick(View view) {
		if (view.getId() == R.id.button_sign_in) {
			signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
		} else {
			signUp(editTextEmail.getText().toString(), editTextPassword.getText().toString());
		}
	}

	private void signIn(String email, String password) {
		firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
				new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Toast.makeText(PassMainActivity.this, "sign in is successful", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(PassMainActivity.this, Database.class);
							startActivity(intent);
						} else {
							Toast.makeText(PassMainActivity.this, "sign in is not successful", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	private void signUp(String email, String password) {
		firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
				new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Toast.makeText(PassMainActivity.this, "sign up is successful", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(PassMainActivity.this, "sign up is not successful", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}


}

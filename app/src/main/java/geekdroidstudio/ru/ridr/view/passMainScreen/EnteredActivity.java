package geekdroidstudio.ru.ridr.view.passMainScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geekdroidstudio.ru.ridr.App;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.server.authentication.AuthDatabase;
import geekdroidstudio.ru.ridr.server.authentication.Authentication;

public class EnteredActivity extends AppCompatActivity implements AuthDatabase.IAuthDatabase{

	@BindView(R.id.text_view_hi) TextView textViewHi;
	@Inject AuthDatabase authDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entered);

		ButterKnife.bind(this);

		//обязательно заинжектить и передать контекст
		App.getComponent().inject(this);
		authDatabase.setContext(this);
		//проверка имени
		authDatabase.getUserName();

	}

	@Override
	public void getUserName(String userName) {
		textViewHi.setText("Hi, " + userName + "!");
	}
}

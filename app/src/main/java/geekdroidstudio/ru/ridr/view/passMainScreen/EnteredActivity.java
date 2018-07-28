package geekdroidstudio.ru.ridr.view.passMainScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geekdroidstudio.ru.ridr.R;
import geekdroidstudio.ru.ridr.server.authentication.Authentication;

public class EnteredActivity extends AppCompatActivity {

	@BindView(R.id.text_view_hi) TextView textViewHi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entered);

		ButterKnife.bind(this);

		//пробные, чтобы не вводить каждый раз
		textViewHi.setText("Hi!");
	}

}

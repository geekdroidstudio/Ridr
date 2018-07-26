package geekdroidstudio.ru.ridr.view.passMainScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geekdroidstudio.ru.ridr.R;

public class EnteredActivity extends AppCompatActivity {

	@BindView(R.id.edit_text_item_key) EditText editTextItemKey;
	@BindView(R.id.edit_text_item_value) EditText editTextItemValue;
	@BindView(R.id.button_add_item) Button buttonAddItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entered);

		ButterKnife.bind(this);

		//пробные, чтобы не вводить каждый раз
		editTextItemKey.setText("111");
		editTextItemValue.setText("aaa");
	}

	@OnClick(R.id.button_add_item)
	public void onClick(View view){

	}
}

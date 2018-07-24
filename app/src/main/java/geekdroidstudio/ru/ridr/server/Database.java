package geekdroidstudio.ru.ridr.server;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geekdroidstudio.ru.ridr.R;
import timber.log.Timber;

public class Database extends AppCompatActivity {

	public static final String BOOK = "book";

	private FirebaseDatabase firebaseDatabase;
	private DatabaseReference databaseReference;

	@BindView(R.id.edit_text_item_key) EditText editTextItemKey;
	@BindView(R.id.edit_text_item_value) EditText editTextItemValue;
	@BindView(R.id.button_add_item) Button buttonAddItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);

		ButterKnife.bind(this);

		firebaseDatabase = FirebaseDatabase.getInstance();
		databaseReference = firebaseDatabase.getReference();

		//пробные, чтобы не вводить каждый раз
		editTextItemKey.setText("111");
		editTextItemValue.setText("aaa");

		databaseReference.getRoot().addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				Timber.d("All notes");
				for(DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
					Timber.d(childDataSnapshot.getKey() + " " + childDataSnapshot.getValue());
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});
	}

	@OnClick(R.id.button_add_item)
	public void onClick(View view){
		if(view.getId() == R.id.button_add_item){
			databaseReference.child(editTextItemKey.getText().toString())
					.setValue(editTextItemValue.getText().toString());
		}
	}
}

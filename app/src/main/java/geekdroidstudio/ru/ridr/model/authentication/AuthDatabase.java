package geekdroidstudio.ru.ridr.model.authentication;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

public class AuthDatabase {

    public static final String AUTH_BOOK = "authentication";
    public static final String AUTH_USER_NAME = "userName";
    public static final String AUTH_USER_EMAIL = "userEmail";
    public static final String AUTH_USER_STATUS = "userStatus";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private IAuthDatabase iAuthDatabase;

    public AuthDatabase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void setListener(IAuthDatabase iAuthDatabase) {
        this.iAuthDatabase = iAuthDatabase;
    }

    //добавление пользователя в базу
    public void addUser(String userId, String userName, String userEmail, String userStatus) {
        Timber.d("addUser: " + userId + " " + userName + " " + userEmail + " " + userStatus);
        databaseReference.child(AUTH_BOOK).child(userId).child(AUTH_USER_NAME).setValue(userName);
        databaseReference.child(AUTH_BOOK).child(userId).child(AUTH_USER_EMAIL).setValue(userEmail);
        databaseReference.child(AUTH_BOOK).child(userId).child(AUTH_USER_STATUS).setValue(userStatus);
    }

    public void getUserName(String userId) {

        databaseReference
                .child(AUTH_BOOK)
                .child(userId)
                .child(AUTH_USER_NAME)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        iAuthDatabase.wasGetUserName(dataSnapshot.getValue().toString());
                        Timber.d(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.e("wasGetUserName: onCancelled");
                    }
                });

    }

    public interface IAuthDatabase {
        void wasGetUserName(String userName);
    }


}
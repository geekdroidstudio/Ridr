package geekdroidstudio.ru.ridr.model.authentication;

import android.content.Context;
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

    private static String userId;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private IAuthDatabase iAuthDatabase;

    public AuthDatabase() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public static void setUserId(String userId) {
        AuthDatabase.userId = userId;
    }

    public void setContext(Context context) {
        iAuthDatabase = (IAuthDatabase) context;
    }

    //добавление пользователя в базу
    public void addUser(String userId, String userName, String userEmail, String userStatus) {
        Timber.d("addUser: " + userId + " " + userName + " " + userEmail + " " + userStatus);
        databaseReference.child(AUTH_BOOK).child(userId).child(AUTH_USER_NAME).setValue(userName);
        databaseReference.child(AUTH_BOOK).child(userId).child(AUTH_USER_EMAIL).setValue(userEmail);
        databaseReference.child(AUTH_BOOK).child(userId).child(AUTH_USER_STATUS).setValue(userStatus);
    }

    public void getUserName() {

        databaseReference
                .child(AUTH_BOOK)
                .child(userId)
                .child(AUTH_USER_NAME)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        iAuthDatabase.getUserName(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.e("getUserName: onCancelled");
                    }
                });

    }

    public interface IAuthDatabase {
        void getUserName(String userName);
    }


}

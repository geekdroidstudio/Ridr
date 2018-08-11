package geekdroidstudio.ru.ridr.model.communication.request;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import timber.log.Timber;

public abstract class FirebaseChildEventListener implements ChildEventListener {

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String id) {
        //nothing
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        //nothing
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        //nothing
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        //nothing
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.e(databaseError.getMessage());
    }

}

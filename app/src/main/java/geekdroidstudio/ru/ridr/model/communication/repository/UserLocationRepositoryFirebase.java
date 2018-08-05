package geekdroidstudio.ru.ridr.model.communication.repository;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class UserLocationRepositoryFirebase implements IUserLocationRepository {

    private static final String USER_IS_NULL_ERROR = "User is null";

    private DatabaseReference usersReference;
    private DatabaseReference driversReference;
    private DatabaseReference passengersReference;

    private Subject<Map<String, Coordinate>> driversSubject;
    private Subject<Map<String, Coordinate>> passengersSubject;

    private ValueEventListener driversListener;
    private ValueEventListener passengersListener;

    public UserLocationRepositoryFirebase(DatabaseReference usersReference,
                                          DatabaseReference driversReference,
                                          DatabaseReference passengersReference) {
        this.usersReference = usersReference;
        this.driversReference = driversReference;
        this.passengersReference = passengersReference;

        driversSubject = BehaviorSubject.create();
        passengersSubject = BehaviorSubject.create();

        driversListener = getUsersListener(driversSubject);
        passengersListener = getUsersListener(passengersSubject);
    }

    @Override
    public Single<User> getUser(String id) {
        return Single.create(emitter -> {
            DatabaseReference userReference = usersReference.child(id);

            userReference.addListenerForSingleValueEvent(getUserListener(emitter));
        });
    }

    @Override
    public Completable postDriverLocation(String id, Coordinate location) {
        return getCompletableUserLocation(driversReference, id, location);
    }

    @Override
    public Completable postPassengerLocation(String id, Coordinate location) {
        return getCompletableUserLocation(passengersReference, id, location);
    }


    @Override
    public Observable<Map<String, Coordinate>> getDrivers() {
        return driversSubject.doOnSubscribe(disposable -> {
            //passengersReference.removeEventListener(passengersListener);//TODO:после отладки раскомментить
            driversReference.addValueEventListener(driversListener);
        });
    }

    @Override
    public Observable<Map<String, Coordinate>> getPassengers() {
        return passengersSubject.doOnSubscribe(disposable -> {
            //driversReference.removeEventListener(driversListener);//TODO:после отладки раскомментить
            passengersReference.addValueEventListener(passengersListener);
        });
    }

    @NonNull
    private Completable getCompletableUserLocation(DatabaseReference usersReference, String id,
                                                   Coordinate location) {
        return Completable.create(emitter -> {
            usersReference.child(id)
                    .setValue(location);
            emitter.onComplete();
        });
    }

    @NonNull
    private ValueEventListener getUserListener(SingleEmitter<User> emitter) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null) {
                    Timber.e(USER_IS_NULL_ERROR);
                    emitter.onError(new RuntimeException(USER_IS_NULL_ERROR));
                } else {
                    user.setId(dataSnapshot.getKey());
                    emitter.onSuccess(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e(databaseError.getMessage());
                emitter.onError(new RuntimeException(databaseError.getMessage()));
            }
        };
    }

    @NonNull
    private ValueEventListener getUsersListener(Subject<Map<String, Coordinate>> usersSubject) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Coordinate> coordinateMap = new HashMap<>();

                for (DataSnapshot subDataSnapshot : dataSnapshot.getChildren()) {
                    Coordinate coordinate = subDataSnapshot.getValue(Coordinate.class);
                    coordinateMap.put(subDataSnapshot.getKey(), coordinate);
                }

                usersSubject.onNext(coordinateMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e(databaseError.getMessage());
                usersSubject.onError(new RuntimeException(databaseError.getMessage()));
            }
        };
    }
}

package geekdroidstudio.ru.ridr.model.communication.repository;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import geekdroidstudio.ru.ridr.model.communication.entity.UserLocation;
import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class CommunicationRepositoryFirebase implements ICommunicationRepository {

    private DatabaseReference usersReference;
    private DatabaseReference driversReference;
    private DatabaseReference passengersReference;

    private Subject<Map<String, Coordinate>> driversSubject;
    private Subject<Map<String, Coordinate>> passengersSubject;

    private ValueEventListener driversListener;
    private ValueEventListener passengersListener;

    public CommunicationRepositoryFirebase(DatabaseReference usersReference,
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
    public Observable<User> getUser(String id) {
        return Observable.create(emitter -> {
            DatabaseReference userReference = usersReference.child(id);

            userReference.addValueEventListener(getUserListener(emitter, userReference));
        });
    }

    @Override
    public Completable postDriverLocation(UserLocation userLocation) {
        return getCompletableUserLocation(driversReference, userLocation);
    }

    @Override
    public Completable postPassengerLocation(UserLocation userLocation) {
        return getCompletableUserLocation(passengersReference, userLocation);
    }


    @Override
    public Observable<Map<String, Coordinate>> getDrivers() {
        return driversSubject.doOnSubscribe(disposable -> {
            passengersReference.removeEventListener(passengersListener);
            driversReference.addValueEventListener(driversListener);
        });
    }

    @Override
    public Observable<Map<String, Coordinate>> getPassengers() {
        return passengersSubject.doOnSubscribe(disposable -> {
            driversReference.removeEventListener(driversListener);
            passengersReference.addValueEventListener(passengersListener);
        });
    }

    @NonNull
    private Completable getCompletableUserLocation(DatabaseReference usersReference,
                                                   UserLocation userLocation) {
        return Completable.create(emitter -> {
            usersReference.child(userLocation.getId())
                    .setValue(userLocation.getCoordinate());
            emitter.onComplete();
        });
    }

    @NonNull
    private ValueEventListener getUserListener(ObservableEmitter<User> emitter,
                                               DatabaseReference userReference) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userReference.removeEventListener(this);

                User user = dataSnapshot.getValue(User.class);
                emitter.onNext(user);
                emitter.onComplete();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                userReference.removeEventListener(this);

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

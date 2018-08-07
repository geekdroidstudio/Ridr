package geekdroidstudio.ru.ridr.model.communication.repository.request;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import geekdroidstudio.ru.ridr.model.entity.communication.DriverResponse;
import geekdroidstudio.ru.ridr.model.entity.communication.PassengerRequest;
import geekdroidstudio.ru.ridr.model.entity.routes.DualCoordinateRoute;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import timber.log.Timber;

public class PassengerRequestRepositoryFirebase implements IPassengerRequestRepository {

    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";

    private DatabaseReference mainReference;

    public PassengerRequestRepositoryFirebase(DatabaseReference mainReference) {
        this.mainReference = mainReference;
    }

    @Override
    public Single<DriverResponse> postRequest(PassengerRequest request) {
        return Single.create(emitter -> {
            String driverId = request.getDriverId();
            String passengerId = request.getPassengerId();

            DatabaseReference reference = mainReference.child(driverId).child(passengerId);
            DatabaseReference requestReference = reference.child(REQUEST);
            DatabaseReference responseReference = reference.child(RESPONSE);

            requestReference.setValue(request.getDualCoordinateRoute());

            responseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boolean accept = dataSnapshot.getValue(Boolean.class);

                    if (accept != null) {
                        responseReference.removeEventListener(this);
                        responseReference.setValue(null);

                        emitter.onSuccess(new DriverResponse(driverId, passengerId, accept));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Timber.e(databaseError.getMessage());
                    emitter.onError(databaseError.toException());
                }
            });
        });
    }

    @Override
    public Observable<PassengerRequest> getRequestObservable(String driverId) {
        return BehaviorSubject.create(emitter -> {
                    DatabaseReference requestReference = mainReference.child(driverId);
            requestReference.addChildEventListener(getRequestsListener(driverId, emitter));
                }
        );
    }

    @Override
    public Completable postResponse(DriverResponse response) {
        return Completable.create(emitter -> {
            DatabaseReference responseReference = mainReference.child(response.getDriverId())
                    .child(response.getPassengerId())
                    .child(RESPONSE);

            responseReference.setValue(response.getAccept());
        });
    }

    @NonNull
    private FirebaseChildEventListener getRequestsListener(String driverId,
                                                           ObservableEmitter<PassengerRequest> emitter) {
        return new FirebaseChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DataSnapshot request = dataSnapshot.child(REQUEST);
                DualCoordinateRoute dualCoordinateRoute = request.getValue(DualCoordinateRoute.class);
                if (dualCoordinateRoute != null) {
                    emitter.onNext(new PassengerRequest(dataSnapshot.getKey(), driverId, dualCoordinateRoute));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                super.onCancelled(databaseError);
                emitter.onError(databaseError.toException());
            }
        };
    }
}

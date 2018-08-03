package geekdroidstudio.ru.ridr.di.modules;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.model.communication.DriverCommunication;
import geekdroidstudio.ru.ridr.model.communication.IDriverCommunication;
import geekdroidstudio.ru.ridr.model.communication.IPassengerCommunication;
import geekdroidstudio.ru.ridr.model.communication.PassengerCommunication;
import geekdroidstudio.ru.ridr.model.communication.repository.IUserLocationRepository;
import geekdroidstudio.ru.ridr.model.communication.repository.UserLocationRepositoryFirebase;
import geekdroidstudio.ru.ridr.model.communication.repository.request.IPassengerRequestRepository;
import geekdroidstudio.ru.ridr.model.communication.repository.request.PassengerRequestRepositoryFirebase;

@Module
public class CommunicationModule {

    private static final String USERS_ON_MAP = "usersOnMap";
    private static final String AUTHENTICATION = "authentication";
    private static final String PASSENGERS = "passengers";
    private static final String DRIVERS = "drivers";
    private static final String REQUESTS = "requests";

    private static final String NAMED_USERS = "users";
    private static final String NAMED_DRIVERS = "drivers";
    private static final String NAMED_PASSENGERS = "passengers";
    private static final String NAMED_REQUEST = "requests";
    private static final String NAMED_ROOT = "root";
    private static final String NAMED_USER_COORDINATES = "userCoordinates";

    @Provides
    @Singleton
    public IDriverCommunication getDriverCommunication(
            IUserLocationRepository locationRepository,
            IPassengerRequestRepository requestRepository) {
        return new DriverCommunication(locationRepository, requestRepository);
    }

    @Provides
    @Singleton
    public IPassengerCommunication getPassengerCommunication(
            IUserLocationRepository locationRepository,
            IPassengerRequestRepository requestRepository) {
        return new PassengerCommunication(locationRepository, requestRepository);
    }

    @Provides
    @Singleton
    public IUserLocationRepository getUserLocationRepository(
            @Named(NAMED_USERS) DatabaseReference users,
            @Named(NAMED_DRIVERS) DatabaseReference drivers,
            @Named(NAMED_PASSENGERS) DatabaseReference passengers) {

        return new UserLocationRepositoryFirebase(users, drivers, passengers);
    }

    @Provides
    @Singleton
    public IPassengerRequestRepository getPassengerRequestRepository(
            @Named(NAMED_REQUEST) DatabaseReference requests) {
        return new PassengerRequestRepositoryFirebase(requests);
    }

    @Provides
    @Singleton
    @Named("root")
    public DatabaseReference getRootDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides
    @Singleton
    @Named(NAMED_USER_COORDINATES)
    public DatabaseReference getUserCoordinatesDatabaseReference(
            @Named(NAMED_ROOT) DatabaseReference root) {
        return root.child(USERS_ON_MAP);
    }

    @Provides
    @Singleton
    @Named(NAMED_USERS)
    public DatabaseReference getUsersDatabaseReference(
            @Named(NAMED_ROOT) DatabaseReference root) {
        return root.child(AUTHENTICATION);
    }

    @Provides
    @Singleton
    @Named(NAMED_PASSENGERS)
    public DatabaseReference getPassengersDatabaseReference(
            @Named(NAMED_USER_COORDINATES) DatabaseReference userCoordinates) {
        return userCoordinates.child(PASSENGERS);
    }

    @Provides
    @Singleton
    @Named(NAMED_DRIVERS)
    public DatabaseReference getDriversDatabaseReference(
            @Named(NAMED_USER_COORDINATES) DatabaseReference userCoordinates) {
        return userCoordinates.child(DRIVERS);
    }

    @Provides
    @Singleton
    @Named(NAMED_REQUEST)
    public DatabaseReference getRequestsDatabaseReference(
            @Named(NAMED_ROOT) DatabaseReference root) {
        return root.child(REQUESTS);
    }
}

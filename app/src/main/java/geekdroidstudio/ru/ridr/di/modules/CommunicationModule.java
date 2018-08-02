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
            @Named("users") DatabaseReference users,
            @Named("drivers") DatabaseReference drivers,
            @Named("passengers") DatabaseReference passengers) {

        return new UserLocationRepositoryFirebase(users, drivers, passengers);
    }

    @Provides
    @Singleton
    public IPassengerRequestRepository getPassengerRequestRepository(
            @Named("requests") DatabaseReference requests) {
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
    @Named("userCoordinates")
    public DatabaseReference getUserCoordinatesDatabaseReference(
            @Named("root") DatabaseReference root) {
        return root.child("usersOnMap");
    }

    @Provides
    @Singleton
    @Named("users")
    public DatabaseReference getUsersDatabaseReference(
            @Named("root") DatabaseReference root) {
        return root.child("authentication");
    }

    @Provides
    @Singleton
    @Named("passengers")
    public DatabaseReference getPassengersDatabaseReference(
            @Named("userCoordinates") DatabaseReference userCoordinates) {
        return userCoordinates.child("passengers");
    }

    @Provides
    @Singleton
    @Named("drivers")
    public DatabaseReference getDriversDatabaseReference(
            @Named("userCoordinates") DatabaseReference userCoordinates) {
        return userCoordinates.child("drivers");
    }

    @Provides
    @Singleton
    @Named("requests")
    public DatabaseReference getRequestsDatabaseReference(
            @Named("root") DatabaseReference root) {
        return root.child("requests");
    }
}

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

@Module
public class CommunicationModule {

    @Provides
    @Singleton
    public IDriverCommunication getDriverCommunication(IUserLocationRepository repository,
                                                       IPassengerRequestRepository requestRepository) {
        return new DriverCommunication(repository, requestRepository);
    }

    @Provides
    @Singleton
    public IPassengerCommunication getPassengerCommunication(IUserLocationRepository repository,
                                                             IPassengerRequestRepository requestRepository) {
        return new PassengerCommunication(repository, requestRepository);
    }

    @Provides
    @Singleton
    public IUserLocationRepository getUserLocationRepository(
            @Named("users") DatabaseReference users,
            @Named("passengers") DatabaseReference passengers,
            @Named("drivers") DatabaseReference drivers) {

        return new UserLocationRepositoryFirebase(users, passengers, drivers);
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
}

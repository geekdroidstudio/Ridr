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
import geekdroidstudio.ru.ridr.model.communication.repository.CommunicationRepositoryFirebase;
import geekdroidstudio.ru.ridr.model.communication.repository.ICommunicationRepository;

@Module
public class CommunicationModule {

    @Provides
    @Singleton
    public IDriverCommunication getDriverCommunication(ICommunicationRepository repository) {
        return new DriverCommunication(repository);
    }

    @Provides
    @Singleton
    public IPassengerCommunication getPassengerCommunication(ICommunicationRepository repository) {
        return new PassengerCommunication(repository);
    }

    @Provides
    @Singleton
    public ICommunicationRepository getCommunicationRepository(
            @Named("users") DatabaseReference users,
            @Named("passengers") DatabaseReference passengers,
            @Named("drivers") DatabaseReference drivers) {

        return new CommunicationRepositoryFirebase(users, passengers, drivers);
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
        return root.child("user_coordinates");
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

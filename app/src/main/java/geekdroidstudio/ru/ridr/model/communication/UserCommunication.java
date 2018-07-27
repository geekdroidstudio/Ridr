package geekdroidstudio.ru.ridr.model.communication;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import geekdroidstudio.ru.ridr.model.communication.entity.UserLocation;
import geekdroidstudio.ru.ridr.model.communication.repository.ICommunicationRepository;
import geekdroidstudio.ru.ridr.model.entity.users.Coordinate;
import geekdroidstudio.ru.ridr.model.entity.users.User;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

abstract class UserCommunication<U extends User, Other extends User> {

    private ICommunicationRepository communicationRepository;

    private Map<String, Other> userMap = new HashMap<>();

    private Subject<List<Other>> usersSubject = BehaviorSubject.create();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    UserCommunication(ICommunicationRepository communicationRepository,
                      Observable<Map<String, Coordinate>> userLocationObservable) {
        this.communicationRepository = communicationRepository;

        compositeDisposable.add(userLocationObservable.subscribe(getUsersConsumer()));
    }

    @NonNull
    private Consumer<Map<String, Coordinate>> getUsersConsumer() {
        return userLocations -> {
            removeOldUsers(userLocations);
            updateUsers(userLocations);
        };
    }

    /*
     * Удаляет из списка устаревших (отсутствующих) пользователей
     */
    private void removeOldUsers(Map<String, Coordinate> userLocations) {
        Set<String> missingSet = new HashSet<>();
        for (String id : userMap.keySet()) {
            if (!userLocations.containsKey(id)) {
                missingSet.add(id);
            }
        }

        userMap.keySet().removeAll(missingSet);
    }

    /*
     * Обновляет текущих или создаёт новых пользователей, с координатами
     */
    private void updateUsers(Map<String, Coordinate> userLocations) {
        boolean userUpdated = false;
        for (String id : userLocations.keySet()) {
            Other user = userMap.get(id);
            Coordinate location = userLocations.get(id);
            if (user != null) {
                user.setLocation(location);
                userUpdated = true;
            } else {
                compositeDisposable.add(communicationRepository.getUser(id)
                        .subscribe(getUserConsumer(location)));
            }
        }
        if (userUpdated) {
            postUsers();
        }
    }

    private void postUsers() {
        usersSubject.onNext(new ArrayList<>(userMap.values()));
    }

    @NonNull
    private Consumer<User> getUserConsumer(Coordinate location) {
        return user -> {
            Other newUser = createUser();

            newUser.setId(user.getId());
            newUser.setName(user.getName());
            newUser.setLocation(location);

            userMap.put(newUser.getId(), newUser);

            postUsers();
        };
    }

    protected abstract Other createUser();

    public Completable postLocation(U user) {
        UserLocation userLocation = new UserLocation(user.getId(), user.getLocation());
        return communicationRepository.postDriverLocation(userLocation);
    }

    Observable<List<Other>> getUsersObservable() {
        return usersSubject;
    }
}

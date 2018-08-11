package geekdroidstudio.ru.ridr.model.communication;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import geekdroidstudio.ru.ridr.model.communication.entity.User;
import geekdroidstudio.ru.ridr.model.communication.location.IUserLocationRepository;
import geekdroidstudio.ru.ridr.model.communication.location.entity.Coordinate;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

abstract class UserCommunication<Other extends User> {

    final IUserLocationRepository locationRepository;

    private Map<String, Other> userMap = new HashMap<>();

    private Subject<List<Other>> usersSubject = BehaviorSubject.create();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    UserCommunication(IUserLocationRepository locationRepository) {
        this.locationRepository = locationRepository;

    }

    public void setLocationsObservable(Observable<Map<String, Coordinate>> userLocationsObservable) {
        compositeDisposable.add(userLocationsObservable.subscribe(getUsersConsumer()));
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
                compositeDisposable.add(locationRepository.getUser(id)
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

    Observable<List<Other>> getUsersObservable() {
        return usersSubject;
    }
}

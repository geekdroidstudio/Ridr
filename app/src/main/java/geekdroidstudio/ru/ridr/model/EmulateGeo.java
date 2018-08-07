package geekdroidstudio.ru.ridr.model;


import android.location.Location;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

//debug
public class EmulateGeo {

    private Subject<Location> subject = PublishSubject.create();

    private Location location = new Location("");

    private Random random;

    public EmulateGeo() {
        random = new Random();

        location.setLatitude(55.7582785);
        location.setLongitude(37.6028725);

        addRandom(0.2334595);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                addRandom(0.001);
                subject.onNext(location);
            }
        }, 0, 3000);
    }

    private void addRandom(double max) {
        location.setLatitude(location.getLatitude() + max * (random.nextDouble() - 0.5));
        location.setLongitude(location.getLongitude() + max * (random.nextDouble() - 0.5));
    }

    public Observable<Location> getSubject() {
        return subject;
    }
}

package ru.geekdroidstudio.ridr.model.maphelper;

import java.util.List;

public interface IMapHelper<M, I, C> {

    void init(M map);

    void drawRoute(int size, int routeLineColor, float routeLineWidth, I endPointIcon,
                   I startPointIcon, List<C> routePoints);

    void drawMapObjects(List<C> mapObjects, I objectIcon);

    void drawUser(C user, I userIcon);
}

package geekdroidstudio.ru.ridr.model.mapHelper;

import java.util.List;

public interface IMapHelper<M, I, C> {

    void init(M map);

    void drawRoute(int size, int routeLineColor, float routeLineWidth, I endPointIcon,
                   I startPointIcon, List<C> routePoints);
}

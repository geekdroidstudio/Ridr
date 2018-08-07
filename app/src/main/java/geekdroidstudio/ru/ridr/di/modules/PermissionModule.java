package geekdroidstudio.ru.ridr.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.model.permissions.android.PermissionsHelper;

@Singleton
@Module
public class PermissionModule {

    @Provides
    public PermissionsHelper permissionsHelper() {
        return new PermissionsHelper();
    }

}

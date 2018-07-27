package geekdroidstudio.ru.ridr.di.modules;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekdroidstudio.ru.ridr.model.api.IApiService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module
public class ApiModule {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/";
    private static final int CONNECTION_TIMEOUT_SEC = 3;

    @Provides
    public IApiService api(Retrofit retrofit) {
        return retrofit.create(IApiService.class);
    }

    @Provides
    public Retrofit retrofit(String baseUrl,
                             @Named("interceptOkHttp") OkHttpClient client,
                             RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                             GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public String baseUrl() {
        return BASE_URL;
    }

    @Named("defaultOkHttp")
    @Provides
    public OkHttpClient defaultOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
                .build();
    }

    @Named("interceptOkHttp")
    @Provides
    public OkHttpClient interceptOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    @Provides
    public RxJava2CallAdapterFactory rxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }


    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public Gson gson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }


}

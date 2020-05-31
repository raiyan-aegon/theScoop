package com.mukhtarinc.thescoop.di.application.modules;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mukhtarinc.thescoop.di.application.scopes.AppScoped;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Raiyan Mukhtar on 5/29/2020.
 */

@Module
public class NetworkModule {


    private static final String TAG = "NetworkModule";
    @Provides
    Cache provideCache(Application application){

        int CacheSize = 10 * 1024 * 1024; //10MB

        File httpCacheDir  = new File(application.getCacheDir(),"offline-cache");


        return new Cache(httpCacheDir,CacheSize);

    }

    @Provides
    @Named("cache")
    Interceptor provideInterceptor(){




        return chain -> {


            Log.d(TAG, "Network Interceptor: Called");

            Response response = chain.proceed(chain.request());

            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(5,TimeUnit.SECONDS)
                    .build();


            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control",cacheControl.toString())
                    .build();

        };
    }



    @Provides
    @Named("offline")
    Interceptor providesOfflineInterceptor(ConnectivityManager manager){


        return new Interceptor()  {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {

                Log.d(TAG, "Offline Interceptor: Called");

                Request request = chain.request();


                if(hasNetwork(manager)){
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7,TimeUnit.DAYS)
                            .build();


                    request = request.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .cacheControl(cacheControl)
                            .build();


                }

                return chain.proceed(request);

            }
        };

    }

    @Provides
    OkHttpClient provideOkHttpClient(Cache cache, @Named("cache") Interceptor interceptor, @Named("offline") Interceptor offlineCacheInterceptor){

        return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .addInterceptor(offlineCacheInterceptor)
                .cache(cache)
                .build();

    }


    @Provides
    boolean hasNetwork(ConnectivityManager connectivityManager){

        boolean isConnected = false;

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            isConnected = true;
        }

        return  isConnected;

    }

}

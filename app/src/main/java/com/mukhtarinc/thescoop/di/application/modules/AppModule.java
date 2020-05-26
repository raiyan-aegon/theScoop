package com.mukhtarinc.thescoop.di.application.modules;

import com.mukhtarinc.thescoop.utils.Constants;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */

@Module
public class AppModule {


    @Provides
    static Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

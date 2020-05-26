package com.mukhtarinc.thescoop.di.today;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */

import com.mukhtarinc.thescoop.network.today.TodayApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public  class TodayApiServiceModule {


    @Provides
   static TodayApi provideTodayApiService(Retrofit retrofit){

        return retrofit.create(TodayApi.class);
    }
}

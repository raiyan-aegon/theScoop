package com.mukhtarinc.thescoop.di.main.today;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */

import android.util.Log;

import com.mukhtarinc.thescoop.di.application.scopes.AppScoped;
import com.mukhtarinc.thescoop.di.application.scopes.FragmentScoped;
import com.mukhtarinc.thescoop.network.today.TodayApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public  class TodayApiServiceModule {

    private static final String TAG = "TodayApiServiceModule";

    @Provides
   static TodayApi provideTodayApiService(Retrofit retrofit){

        if(retrofit==null){

            Log.d(TAG, "provideTodayApiService: Retrofit is null");
            
        }else {
            Log.d(TAG, "provideTodayApiService: Retrofit is not NULL");
        }

        return retrofit.create(TodayApi.class);
    }
}

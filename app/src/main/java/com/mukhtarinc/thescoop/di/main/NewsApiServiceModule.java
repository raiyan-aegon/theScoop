package com.mukhtarinc.thescoop.di.main;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */

import android.util.Log;


import com.mukhtarinc.thescoop.data.network.NewsAPIService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public  class NewsApiServiceModule {

    private static final String TAG = "TodayApiServiceModule";

    @Provides
   static NewsAPIService provideTodayApiService(Retrofit retrofit){

        if(retrofit==null){

            Log.d(TAG, "provideTodayApiService: Retrofit is null");
            
        }else {
            Log.d(TAG, "provideTodayApiService: Retrofit is not NULL");
        }

        return retrofit.create(NewsAPIService.class);
    }
}

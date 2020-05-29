package com.mukhtarinc.thescoop.di.application.modules;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import com.bumptech.glide.RequestManager;
import com.mukhtarinc.thescoop.di.application.scopes.FragmentScoped;
import com.mukhtarinc.thescoop.utils.TodayListAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raiyan Mukhtar on 5/28/2020.
 */

@Module
public class UtilsModule {

    @Provides
    TodayListAdapter provideTodayListAdapter(RequestManager requestManager){


        return new TodayListAdapter(requestManager);

    }

    @Provides
    ConnectivityManager provideConnectivityManager(Application application){

        return (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);

    }



}

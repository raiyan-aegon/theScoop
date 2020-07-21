package com.mukhtarinc.thescoop.di.application;



import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.mukhtarinc.thescoop.di.application.component.AppComponent;
import com.mukhtarinc.thescoop.di.application.component.DaggerAppComponent;
import com.mukhtarinc.thescoop.di.application.Provider;
import com.mukhtarinc.thescoop.utils.NotificationWorker;

import java.util.concurrent.TimeUnit;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */


public class BaseApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class,1, TimeUnit.HOURS).addTag("NOTIFICATION_WORK")
                .setConstraints(constraints)
                .build();



        WorkManager.getInstance().enqueueUniquePeriodicWork("NOTIFICATION_WORK", ExistingPeriodicWorkPolicy.KEEP,periodicWorkRequest);

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);

        Provider.INSTANCE.setAppComponent(appComponent);
        return appComponent;
    }
}

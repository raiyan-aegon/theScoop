package com.mukhtarinc.thescoop;




import com.mukhtarinc.thescoop.di.application.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */


public class BaseApplication extends DaggerApplication {


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}

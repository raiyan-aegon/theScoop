package com.mukhtarinc.thescoop.di.application.component;

import android.app.Application;

import com.mukhtarinc.thescoop.BaseApplication;
import com.mukhtarinc.thescoop.di.ViewModelFactoryModule;
import com.mukhtarinc.thescoop.di.application.modules.ActivityBuilderModule;
import com.mukhtarinc.thescoop.di.application.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 **/


@Singleton
@Component (modules = { AndroidInjectionModule.class, AppModule.class,
        ActivityBuilderModule.class,
        ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {



    @Component.Builder
    interface Builder {


        @BindsInstance
        Builder application(Application application);


        AppComponent build();

    }

}

package com.mukhtarinc.thescoop.di.application.component;

import android.app.Application;

import com.mukhtarinc.thescoop.di.application.BaseApplication;
import com.mukhtarinc.thescoop.di.application.modules.ViewModelFactoryModule;
import com.mukhtarinc.thescoop.di.application.modules.ActivityBuilderModule;
import com.mukhtarinc.thescoop.di.application.modules.AppModule;
import com.mukhtarinc.thescoop.di.application.scopes.AppScoped;
import com.mukhtarinc.thescoop.utils.NotificationWorker;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 **/



@AppScoped
@Component (modules = { AndroidInjectionModule.class, AppModule.class,
        ActivityBuilderModule.class,
        ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {



    void inject(NotificationWorker worker);

    @Component.Builder
    interface Builder {


        @BindsInstance
        Builder application(Application application);


        AppComponent build();

    }

}

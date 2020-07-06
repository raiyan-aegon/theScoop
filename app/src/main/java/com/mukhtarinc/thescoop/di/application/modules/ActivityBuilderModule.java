package com.mukhtarinc.thescoop.di.application.modules;

import com.bumptech.glide.RequestManager;
import com.mukhtarinc.thescoop.di.main.FragmentsBuilderModule;
import com.mukhtarinc.thescoop.ui.activities.MainActivity;
import com.mukhtarinc.thescoop.ui.activities.MoreSourcesActivity;
import com.mukhtarinc.thescoop.ui.activities.TheScoopDetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */


@Module (includes = {FragmentsBuilderModule.class})
public abstract class ActivityBuilderModule {


    @ContributesAndroidInjector
   abstract  MainActivity injectActivity();

    @ContributesAndroidInjector
    abstract TheScoopDetailsActivity injectDetailsActivity();

    @ContributesAndroidInjector
    abstract MoreSourcesActivity injectMoreSourcesActivity();
}

package com.mukhtarinc.thescoop.di.application.modules;

import com.mukhtarinc.thescoop.di.main.module.FragmentsBuilderModule;
import com.mukhtarinc.thescoop.ui.fragments.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */


@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {FragmentsBuilderModule.class})
   abstract  MainActivity injectActivity();
}

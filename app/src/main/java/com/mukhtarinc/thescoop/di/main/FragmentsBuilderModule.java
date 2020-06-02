package com.mukhtarinc.thescoop.di.main;

import com.mukhtarinc.thescoop.di.main.today.TodayApiServiceModule;
import com.mukhtarinc.thescoop.di.main.today.TodayViewModelModule;
import com.mukhtarinc.thescoop.ui.fragments.today.TodayFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */


@Module
public abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector(modules = {TodayApiServiceModule.class, TodayViewModelModule.class})
    abstract TodayFragment injectTodayFragment();
}

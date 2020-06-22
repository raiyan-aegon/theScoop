package com.mukhtarinc.thescoop.di.main;

import com.mukhtarinc.thescoop.di.application.modules.NewsViewModelModule;
import com.mukhtarinc.thescoop.ui.fragments.following.FollowingFragment;

import com.mukhtarinc.thescoop.ui.fragments.foryou.ForYouFragment;
import com.mukhtarinc.thescoop.ui.fragments.today.TodayFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */


@Module(includes = { NewsViewModelModule.class, NewsApiServiceModule.class})
public abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract TodayFragment injectTodayFragment();

    @ContributesAndroidInjector
    abstract FollowingFragment injectFollowFragment();

    @ContributesAndroidInjector
    abstract ForYouFragment injectForYouFragment();
}

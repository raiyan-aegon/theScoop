package com.mukhtarinc.thescoop.di.application.modules;

import androidx.lifecycle.ViewModel;

import com.mukhtarinc.thescoop.di.application.scopes.ViewModelKey;
import com.mukhtarinc.thescoop.ui.fragments.following.FollowingFragment;
import com.mukhtarinc.thescoop.ui.fragments.following.FollowingViewModel;
import com.mukhtarinc.thescoop.ui.fragments.today.TodayViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Raiyan Mukhtar on 5/25/2020.
 */

@Module
public abstract class NewsViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(TodayViewModel.class)
    abstract ViewModel bindTodayViewModel(TodayViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FollowingViewModel.class)
    abstract ViewModel bindFollowingViewModel(FollowingViewModel viewModel);


}

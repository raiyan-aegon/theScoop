package com.mukhtarinc.thescoop.di.today;

import androidx.lifecycle.ViewModel;

import com.mukhtarinc.thescoop.di.ViewModelKey;
import com.mukhtarinc.thescoop.ui.today.TodayViewModel;

import javax.inject.Inject;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Raiyan Mukhtar on 5/25/2020.
 */

@Module
public abstract class TodayViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(TodayViewModel.class)
    abstract ViewModel bindTodayViewModel(TodayViewModel viewModel);

}

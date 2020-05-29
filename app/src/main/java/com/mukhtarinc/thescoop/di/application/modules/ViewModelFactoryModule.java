package com.mukhtarinc.thescoop.di.application.modules;

import androidx.lifecycle.ViewModelProvider;

import com.mukhtarinc.thescoop.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Raiyan Mukhtar on 5/25/2020.
 */

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewFactory(ViewModelProviderFactory viewModelProviderFactory);
}

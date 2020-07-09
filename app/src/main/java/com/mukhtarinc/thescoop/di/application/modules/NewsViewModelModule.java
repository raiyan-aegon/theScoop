package com.mukhtarinc.thescoop.di.application.modules;

import androidx.lifecycle.ViewModel;

import com.mukhtarinc.thescoop.di.application.scopes.ViewModelKey;
import com.mukhtarinc.thescoop.ui.activities.SearchViewModel;
import com.mukhtarinc.thescoop.ui.fragments.following.CategoryViewModel;
import com.mukhtarinc.thescoop.ui.fragments.following.FollowingViewModel;
import com.mukhtarinc.thescoop.ui.fragments.following.SourceViewModel;
import com.mukhtarinc.thescoop.ui.fragments.foryou.ForYouViewModel;
import com.mukhtarinc.thescoop.ui.fragments.shelf.ShelfViewModel;
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


    @Binds
    @IntoMap
    @ViewModelKey(ForYouViewModel.class)
    abstract ViewModel bindForYouViewModel(ForYouViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ShelfViewModel.class)
    abstract ViewModel bindShelfViewModel(ShelfViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel.class)
    abstract ViewModel bindCategoryViewModel(CategoryViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(SourceViewModel.class)
    abstract ViewModel bindSourceViewModel(SourceViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel viewModel);



}

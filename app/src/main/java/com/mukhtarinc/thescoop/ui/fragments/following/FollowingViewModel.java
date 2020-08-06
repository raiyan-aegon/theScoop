package com.mukhtarinc.thescoop.ui.fragments.following;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mukhtarinc.thescoop.data.network.NetworkResource;
import com.mukhtarinc.thescoop.data.network.NewsAPIService;
import com.mukhtarinc.thescoop.data.network.sources.SourceResponse;
import com.mukhtarinc.thescoop.data.network.today.TodayResponse;
import com.mukhtarinc.thescoop.model.Category;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Raiyan Mukhtar on 6/3/2020.
 */
public class FollowingViewModel extends ViewModel {


    private static final String TAG = "FollowingViewModel";

    NewsAPIService apiService;

    @Inject
    public FollowingViewModel(NewsAPIService apiService){

        Log.d(TAG, "FollowingViewModel");

        this.apiService = apiService;
    }


    public List<Category> getCategories() {

        return  Constants.getCategoriesList();
    }

    public MutableLiveData<NetworkResource<SourceResponse>> getSources(String apiKey) {

        MutableLiveData<NetworkResource<SourceResponse>> sources = new MutableLiveData<>();

        sources.setValue(NetworkResource.loading(null));
        apiService.getSources(apiKey).
                onErrorReturn(new Function<Throwable, SourceResponse>() {
            @Override
            public SourceResponse apply(Throwable throwable) throws Exception {
                SourceResponse sourceResponse = new SourceResponse();
                sourceResponse.setSources(null);

                return sourceResponse;
            }
        })
                .map(new Function<SourceResponse, NetworkResource<SourceResponse>>() {
                    @Override
                    public NetworkResource<SourceResponse> apply(SourceResponse sourceResponse) throws Exception {

                        if(sourceResponse.getSources()==null){

                            return  NetworkResource.error("Check Connection",null);
                        }

                        Log.d(TAG, "Response is not null");
                        return NetworkResource.successful(sourceResponse);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NetworkResource<SourceResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NetworkResource<SourceResponse> s) {

                        sources.setValue(s);

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: Error",e.getCause());
                    }

                    @Override
                    public void onComplete() {

                    }
                });



        return sources;

    }

    }

package com.mukhtarinc.thescoop.ui.activities;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mukhtarinc.thescoop.data.network.NetworkResource;
import com.mukhtarinc.thescoop.data.network.NewsAPIService;
import com.mukhtarinc.thescoop.data.network.today.TodayResponse;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Raiyan Mukhtar on 7/9/2020.
 */
public class SearchViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";

    NewsAPIService apiService;

    @Inject
    public SearchViewModel(NewsAPIService apiService){
        this.apiService = apiService;
    }




    public MutableLiveData<NetworkResource<TodayResponse>> getSearchArticles(String keyword, String apiKey){
        MutableLiveData<NetworkResource<TodayResponse>> articles = new MutableLiveData<>();

        articles.setValue(NetworkResource.loading(null));
        apiService.getSearchArticles(keyword,"publishedAt",apiKey)
                .onErrorReturn(new Function<Throwable, TodayResponse>() {
                    @Override
                    public TodayResponse apply(Throwable throwable) throws Exception {
                        TodayResponse todayResponse = new TodayResponse();
                        todayResponse.setArticles(null);

                        return todayResponse;
                    }
                })
                .map((Function<TodayResponse, NetworkResource<TodayResponse>>) todayResponse -> {

                    if(todayResponse.getArticles()==null){

                        return  NetworkResource.error("Check Connection",null);
                    }

                    Log.d(TAG, "Response is not null");
                    return NetworkResource.successful(todayResponse);
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NetworkResource<TodayResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NetworkResource<TodayResponse> todayResponseNetworkResource) {

                        articles.setValue(todayResponseNetworkResource);

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: Error",e.getCause());
                    }

                    @Override
                    public void onComplete() {

                    }
                });



        return articles;

    }

}

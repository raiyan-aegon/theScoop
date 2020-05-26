package com.mukhtarinc.thescoop.ui.today;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.mukhtarinc.thescoop.model.Article;
import com.mukhtarinc.thescoop.network.today.TodayApi;
import com.mukhtarinc.thescoop.network.today.TodayResponse;
import com.mukhtarinc.thescoop.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Raiyan Mukhtar on 5/25/2020.
 */
public class TodayViewModel extends ViewModel {

    private static final String TAG = "TodayViewModel";
    
    private MediatorLiveData<TodayResponse> articles = new MediatorLiveData<>();

    private TodayApi api;

    @Inject
    public TodayViewModel(TodayApi api) {

        this.api =api;
        Log.d(TAG, "TodayViewModel: is Working");
        
    }
    
    
    public void getTodayArticle(String country,String apiKey){


        final LiveData<TodayResponse> source = LiveDataReactiveStreams.fromPublisher(
                api.getTodayArticles(country,apiKey)
                .subscribeOn(Schedulers.io())
        );

        articles.addSource(source, new Observer<TodayResponse>() {
            @Override
            public void onChanged(TodayResponse todayResponse) {
                articles.setValue(todayResponse);
                articles.removeSource(source);
            }
        });
        
        
    }

    public LiveData<TodayResponse> getArticles() {
        return articles;
    }
}

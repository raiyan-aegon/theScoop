package com.mukhtarinc.thescoop.ui.today;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.mukhtarinc.thescoop.network.today.TodayApi;
import com.mukhtarinc.thescoop.network.today.TodayResource;
import com.mukhtarinc.thescoop.network.today.TodayResponse;
import com.mukhtarinc.thescoop.utils.Constants;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Raiyan Mukhtar on 5/25/2020.
 */
public class TodayViewModel extends ViewModel {

    private static final String TAG = "TodayViewModel";


    private MediatorLiveData<TodayResource<TodayResponse>> articles = new MediatorLiveData<>();

    private LiveData<TodayResponse> myArticles;
    private TodayApi api;

    @Inject
    public TodayViewModel(TodayApi api) {

        this.api =api;
        Log.d(TAG, "TodayViewModel: is Working");


    }
    
    
    public void getTodayArticle(String country,String apiKey){





        //request is attempting to be made
        articles.setValue(TodayResource.loading(null));

        final LiveData<TodayResource<TodayResponse>> source = LiveDataReactiveStreams.fromPublisher(
                api.getTodayArticles(country,apiKey)

                        // if error Ocurrs
                        .onErrorReturn(new Function<Throwable, TodayResponse>() {


                            @Override
                            public TodayResponse apply(Throwable throwable) throws Exception {
                                TodayResponse errorResponse = new TodayResponse();
                                errorResponse.setArticles(null);
                                return errorResponse;
                            }
                        })

                        //maps to the above Todayresponse Object to the data retrieved to check and compare if there is an error
                        .map(new Function<TodayResponse, TodayResource<TodayResponse>>() {
                            @Override
                            public TodayResource<TodayResponse> apply(TodayResponse todayResponse) throws Exception {
                                if(todayResponse.getArticles()==null) {
                                    return TodayResource.error("Check your Internet Connection", null);
                                }
                                    return TodayResource.successful(todayResponse);

                            }
                        })
                .subscribeOn(Schedulers.io())
        );

        articles.addSource(source, new Observer<TodayResource<TodayResponse>>() {
            @Override
            public void onChanged(TodayResource<TodayResponse> todayResponseTodayResource) {
                
                if(todayResponseTodayResource.data==null){

                    Log.d(TAG, "onChanged: NULL");
                }else{

                    Log.d(TAG, "onChanged: not null");
                }
                articles.setValue(todayResponseTodayResource);
                articles.removeSource(source);
            }
        });
//
        
    }

    public LiveData<TodayResource<TodayResponse>> getArticles() {
        return articles;
    }
}

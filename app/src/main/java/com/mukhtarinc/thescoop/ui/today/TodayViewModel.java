package com.mukhtarinc.thescoop.ui.today;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mukhtarinc.thescoop.data.network.today.TodayApi;
import com.mukhtarinc.thescoop.data.network.today.TodayResource;
import com.mukhtarinc.thescoop.data.network.today.TodayResponse;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Raiyan Mukhtar on 5/25/2020.
 */
public class TodayViewModel extends ViewModel {

    private static final String TAG = "TodayViewModel";


//    private MediatorLiveData<TodayResource<TodayResponse>> articles = new MediatorLiveData<>();
    //private LiveData<TodayResource<TodayResponse>> articles ;

    private TodayApi api;

    private Application application;

    @Inject
    public TodayViewModel(TodayApi api, Application application) {

        this.api =api;
      //  Log.d(TAG, "TodayViewModel: is Working");
        this.application = application;


    }
    
    
//    public void getTodayArticle(String country,String apiKey){
//
//
//
//                        //request is attempting to be made
////            articles.setValue(TodayResource.loading(null));
////
////            final LiveData<TodayResource<TodayResponse>> source = LiveDataReactiveStreams.fromPublisher(
////                    api.getTodayArticles(country,apiKey)
////
////                            // if error Ocurrs
////                            .onErrorReturn(new Function<Throwable, TodayResponse>() {
////
////                                @Override
////                                public TodayResponse apply(Throwable throwable) throws Exception {
////                                    TodayResponse errorResponse = new TodayResponse();
////                                    errorResponse.setArticles(null);
////                                    return errorResponse;
////                                }
////                            })
////
////                            //maps to the above Todayresponse Object to the data retrieved to check and compare if there is an error
////                            .map(new Function<TodayResponse, TodayResource<TodayResponse>>() {
////                                @Override
////                                public TodayResource<TodayResponse> apply(TodayResponse todayResponse) throws Exception {
////                                    if(todayResponse.getArticles()==null) {
////                                        return TodayResource.error("Check your Internet Connection", null);
////                                    }
////                                    return TodayResource.successful(todayResponse);
////
////                                }
////                            })
////                            .subscribeOn(Schedulers.io())
////            );
////
////
////
////
////            articles.addSource(source, new Observer<TodayResource<TodayResponse>>() {
////                @Override
////                public void onChanged(TodayResource<TodayResponse> todayResponseTodayResource) {
////
////                    if(todayResponseTodayResource.data==null){
////
////                        Log.d(TAG, "onChanged: NULL");
////                    }else{
////
////                        Log.d(TAG, "onChanged: not null");
////                    }
////                    articles.setValue(todayResponseTodayResource);
////                    articles.removeSource(source);
////                }
////            });
//
//
//    }


    public   MutableLiveData<TodayResource<TodayResponse>> getTodayArticles(String country,String apiKey){


        MutableLiveData<TodayResource<TodayResponse>> articles = new MutableLiveData<>();

        articles.setValue(TodayResource.loading(null));
        api.getTodayArticles(country,apiKey)
                .onErrorReturn(new Function<Throwable, TodayResponse>() {
                    @Override
                    public TodayResponse apply(Throwable throwable) throws Exception {
                        TodayResponse todayResponse = new TodayResponse();
                        todayResponse.setArticles(null);

                        return todayResponse;
                    }
                })
                .map(new Function<TodayResponse, TodayResource<TodayResponse>>() {
                    @Override
                    public TodayResource<TodayResponse> apply(TodayResponse todayResponse) throws Exception {

                        if(todayResponse.getArticles()==null){

                            return  TodayResource.error("Check Connection",null);
                        }

                        Log.d(TAG, "Response is not null");
                        return TodayResource.successful(todayResponse);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TodayResource<TodayResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TodayResource<TodayResponse> todayResponseTodayResource) {

                            articles.setValue(todayResponseTodayResource);

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


//    public LiveData<TodayResource<TodayResponse>> getArticles() {
//        return articles;
//    }
}

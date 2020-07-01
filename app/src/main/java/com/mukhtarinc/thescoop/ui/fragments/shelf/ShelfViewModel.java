package com.mukhtarinc.thescoop.ui.fragments.shelf;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mukhtarinc.thescoop.data.ScoopRepository;
import com.mukhtarinc.thescoop.data.local.ArticleDao;
import com.mukhtarinc.thescoop.data.local.ScoopDatabase;
import com.mukhtarinc.thescoop.model.Article;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Raiyan Mukhtar on 7/1/2020.
 */
public class ShelfViewModel extends ViewModel {

    private static final String TAG = "ShelfViewModel";

    private ScoopRepository repository;
    public LiveData<List<Article>> getArticles;

    @Inject
    public ShelfViewModel(Application application){

        ArticleDao articleDao = ScoopDatabase.getDatabase(application.getApplicationContext()).articleDao();

        repository = new ScoopRepository(articleDao);

        getArticles = repository.getAllArticles();

    }


    public void  insert(Article article){


        //Run Insertion on a different Thread
        Observable.just(article)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Article>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Article article) {

                        repository.insertArticle(article);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Insert Successful");
                    }
                });


    }


}

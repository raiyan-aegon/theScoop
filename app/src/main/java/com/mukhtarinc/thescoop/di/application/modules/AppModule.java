package com.mukhtarinc.thescoop.di.application.modules;

import android.app.Application;
import android.content.Context;
import android.media.AsyncPlayer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mukhtarinc.thescoop.di.application.scopes.AppScoped;
import com.mukhtarinc.thescoop.utils.Constants;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */


@Module (includes ={ NetworkModule.class,UtilsModule.class})
public class AppModule {


    @AppScoped
    @Provides
    static Retrofit provideRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @AppScoped
    @Provides
    static RequestManager provideGlideRequestManager(Application application){

        return Glide.with(application);

    }

//    @AppScoped
//    @Provides
//    static ArticleDatabase provideArticleDatabase(Application application){
//
//
//        return ArticleDatabase.Companion.getDatabase(application);
//    }
//



}

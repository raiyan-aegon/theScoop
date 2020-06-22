package com.mukhtarinc.thescoop.data.network;

import com.mukhtarinc.thescoop.data.network.sources.SourceResponse;
import com.mukhtarinc.thescoop.data.network.today.TodayResponse;
import com.mukhtarinc.thescoop.model.Source;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */
public interface NewsAPIService {


    @GET("top-headlines")
    Observable<TodayResponse> getTodayArticles(@Query("country") String country,
                                               @Query("apiKey") String apiKey);

    @GET("sources")
    Observable<SourceResponse> getSources(@Query("apiKey") String apiKey);


    @GET("top-headlines")
    Observable<TodayResponse> getForYouArticles(@Query("sources") String source_id, @Query("apiKey") String apiKey);

}

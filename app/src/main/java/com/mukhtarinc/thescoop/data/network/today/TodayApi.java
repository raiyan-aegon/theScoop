package com.mukhtarinc.thescoop.data.network.today;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */
public interface TodayApi {


    @GET("top-headlines")
    Observable<TodayResponse> getTodayArticles(@Query("country") String country,
                                               @Query("apiKey") String apiKey);


}

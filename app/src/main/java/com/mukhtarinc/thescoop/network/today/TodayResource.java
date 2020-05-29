package com.mukhtarinc.thescoop.network.today;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Raiyan Mukhtar on 5/25/2020.
 */
public class TodayResource<T> {

    @NonNull
    public final TodayArticlesStatus status;

    @Nullable
    public final T data;



    @Nullable
    public final String message;

    public TodayResource(TodayArticlesStatus status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> TodayResource<T> successful(@Nullable  T data){

        return new TodayResource<>(TodayArticlesStatus.SUCCESS,data,null);
    }

    public static <T> TodayResource<T> loading(@Nullable  T data){

        return new TodayResource<>(TodayArticlesStatus.LOADING,data,null);
    }

    public static <T> TodayResource<T> error(@Nullable String message,@Nullable  T data){

        return new TodayResource<>(TodayArticlesStatus.ERROR,data,message);
    }



    public enum TodayArticlesStatus{ SUCCESS,LOADING,ERROR}
}

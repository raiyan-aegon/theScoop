package com.mukhtarinc.thescoop.data.network;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Raiyan Mukhtar on 5/25/2020.
 */
public class NetworkResource<T> {

    @NonNull
    public final NetworkResonseStatus status;

    @Nullable
    public final T data;



    @Nullable
    public final String message;

    public NetworkResource(NetworkResonseStatus status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> NetworkResource<T> successful(@Nullable  T data){

        return new NetworkResource<>(NetworkResonseStatus.SUCCESS,data,null);
    }

    public static <T> NetworkResource<T> loading(@Nullable  T data){

        return new NetworkResource<>(NetworkResonseStatus.LOADING,data,null);
    }

    public static <T> NetworkResource<T> error(@Nullable String message, @Nullable  T data){

        return new NetworkResource<>(NetworkResonseStatus.ERROR,data,message);
    }



    public enum NetworkResonseStatus{ SUCCESS,LOADING,ERROR}
}

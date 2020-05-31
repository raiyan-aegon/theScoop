package com.mukhtarinc.thescoop.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Raiyan Mukhtar on 5/29/2020.
 */
public class NetworkUtils {




    public static boolean hasNetwork(Application application){
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;


            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo.isConnected()) {
                isConnected = true;
            }


             return isConnected;
    }
}

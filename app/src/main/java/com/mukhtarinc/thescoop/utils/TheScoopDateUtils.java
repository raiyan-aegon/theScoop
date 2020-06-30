package com.mukhtarinc.thescoop.utils;

import android.util.Log;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Created by Raiyan Mukhtar on 5/28/2020.
 */
public class TheScoopDateUtils {
    private static final String TAG = "TheScoopDateUtils";

    public static String newsTimeDifference(String time){

        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        ParsePosition position = new ParsePosition(0);



        long then = formatter.parse(time, position).getTime();


        long now  = new Date().getTime();

        long seconds = (now-then)/1000;

        long minutes = seconds/60;

        long hours = minutes/60;

        long days = hours/24;

        String friendly = null;

        long num =0;

        if(days>0){
            num =days;
            friendly=days +"day";

        }else if(hours>0){

            num = hours;

            friendly = hours+"h";
        }else if(minutes>0){

            num = minutes;

            friendly = minutes+"min";
        }else if(seconds>0){

            num = seconds;

            friendly = seconds+"second";
        }

//        if(num>1){
//            friendly +="s";
//
//        }


       // return friendly+" ago";
        return friendly;

    }

}

package com.mukhtarinc.thescoop.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.snackbar.Snackbar;
import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.model.Category;
import com.mukhtarinc.thescoop.model.Source;
import com.mukhtarinc.thescoop.ui.activities.SourceArticleActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */
public class Constants {




    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static final String apiKey= "e4df3bcb12c942729410871f3c030013";
    public static final long CONNECT_TIMEOUT = 30000;
    public static final long READ_TIMEOUT = 30000;
    public static final long WRITE_TIMEOUT = 30000;
    public static final String SHARED_PREFS ="sources";
    public static final String SHARED_PREFS_CLICK ="clicks";


    public static final String CHECK ="check";
    public static final String SOURCE_NAME ="name";



   public static List<Category> getCategoriesList(){
        List<Category> categoriesList = new ArrayList<>();

        categoriesList.add(new Category(R.drawable.entertainment,"Entertainment"));
        categoriesList.add(new Category(R.drawable.business,"Business"));
        categoriesList.add(new Category(R.drawable.health,"Health"));
        categoriesList.add(new Category(R.drawable.sports,"Sports"));
        categoriesList.add(new Category(R.drawable.technology,"Technology"));
        categoriesList.add(new Category(R.drawable.science,"Science"));
        categoriesList.add(new Category(R.drawable.general,"General"));


        return categoriesList;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void showSnackBar(Activity activity, String message, Boolean action, Source source){
        View rootView  = activity.getWindow().getDecorView().findViewById(R.id.parent_container_main);
       if(action){

           Snackbar.make(rootView,message,Snackbar.LENGTH_LONG)
                   .setActionTextColor(activity.getColor(R.color.colorAccent))
                   .setAction("Read Now", view ->{
                       Intent intent = new Intent(activity, SourceArticleActivity.class);
                       intent.putExtra("source",source);
                      activity.startActivity(intent);

                   })
                   .show();
       }else {

           Snackbar.make(rootView,message,Snackbar.LENGTH_LONG)
                   .show();

       }


    }

}

package com.mukhtarinc.thescoop.utils;

import android.content.Context;

import com.mukhtarinc.thescoop.R;
import com.mukhtarinc.thescoop.model.Category;

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


}

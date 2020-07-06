package com.mukhtarinc.thescoop.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mukhtarinc.thescoop.model.Article;

/**
 * Created by Raiyan Mukhtar on 7/1/2020.
 */


@Database( entities = {Article.class}, version = 3,exportSchema = false)
public abstract class ScoopDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();



    public static ScoopDatabase getDatabase(Context context){


        return Room.databaseBuilder(context.getApplicationContext(),ScoopDatabase.class,"scoop.db").fallbackToDestructiveMigration().build();

    }



}

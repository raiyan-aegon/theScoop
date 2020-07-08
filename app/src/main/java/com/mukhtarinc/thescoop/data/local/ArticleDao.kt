package com.mukhtarinc.thescoop.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mukhtarinc.thescoop.model.Article


/**
 * Created by Raiyan Mukhtar on 6/30/2020.
 */

@Dao
interface ArticleDao {

    @Query("Select * from article ORDER BY publishedAt ASC")
    fun  geArticles() : LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Delete
    fun deleteArticle(article: Article)



}
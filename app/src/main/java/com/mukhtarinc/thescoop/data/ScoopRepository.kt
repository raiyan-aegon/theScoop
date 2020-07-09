package com.mukhtarinc.thescoop.data

import androidx.lifecycle.LiveData
import com.mukhtarinc.thescoop.data.local.ArticleDao
import com.mukhtarinc.thescoop.model.Article


/**
 * Created by Raiyan Mukhtar on 6/30/2020.
 */
class ScoopRepository(private val articleDao: ArticleDao){


    //TODO: Dont Forget to make all Data pass through the repository

    val allArticles : LiveData<List<Article>> = articleDao.geArticles()


    fun insertArticle(article: Article){


        articleDao.insertArticle(article)
    }

    fun deleteArticle(article: Article){

        articleDao.deleteArticle(article)
    }


}
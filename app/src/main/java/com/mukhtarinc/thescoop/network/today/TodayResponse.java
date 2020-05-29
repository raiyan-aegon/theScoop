package com.mukhtarinc.thescoop.network.today;

import com.google.gson.annotations.SerializedName;
import com.mukhtarinc.thescoop.model.Article;

import java.util.List;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */
public class TodayResponse {

    @SerializedName("articles")
    List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}

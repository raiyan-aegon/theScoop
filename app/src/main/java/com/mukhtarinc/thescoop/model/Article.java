package com.mukhtarinc.thescoop.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raiyan Mukhtar on 5/24/2020.
 */

@Entity (tableName = "article")
public class Article implements Parcelable {


    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="_id")
    private int _id;

    @SerializedName("author")
    private String author;

    @ColumnInfo (name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    private String url;

    @ColumnInfo(name ="urlToImage")
    @SerializedName("urlToImage")
    private String urlToImage;

    @ColumnInfo(name ="publishedAt")
    @SerializedName("publishedAt")
    private String publishedAt;

    @ColumnInfo(name = "content")
    @SerializedName("content")
    private String content;

    @Ignore
    @SerializedName("source")
    private Source getSource;


    public Article(){

    }


    protected Article(Parcel in) {
        author = in.readString();
        title = in.readString();
        url = in.readString();
        urlToImage = in.readString();
        publishedAt = in.readString();
        content = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public Source getGetSource() {
        return getSource;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeString(urlToImage);
        parcel.writeString(publishedAt);
        parcel.writeString(content);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setGetSource(Source getSource) {
        this.getSource = getSource;
    }
}

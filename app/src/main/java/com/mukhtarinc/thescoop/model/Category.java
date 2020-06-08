package com.mukhtarinc.thescoop.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raiyan Mukhtar on 6/7/2020.
 */
public class Category implements Parcelable {

    private int cat_image;
    private  String cat_name;


    protected Category(Parcel in) {
        cat_image = in.readInt();
        cat_name = in.readString();
    }

    public Category(int cat_image, String cat_name){
        this.cat_image= cat_image;
        this.cat_name = cat_name;

    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getCat_image() {
        return cat_image;
    }

    public void setCat_image(int cat_image) {
        this.cat_image = cat_image;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(cat_image);
        parcel.writeString(cat_name);
    }
}

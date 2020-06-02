package com.mukhtarinc.thescoop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raiyan Mukhtar on 5/28/2020.
 */
public class Source implements Parcelable {

    @SerializedName("id")
    private String source_id;

    @SerializedName("name")
    private String name;


    protected Source(Parcel in) {
        source_id = in.readString();
        name = in.readString();
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(source_id);
        parcel.writeString(name);
    }
}

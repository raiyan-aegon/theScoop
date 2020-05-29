package com.mukhtarinc.thescoop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Raiyan Mukhtar on 5/28/2020.
 */
public class Source {

    @SerializedName("id")
    private String source_id;

    @SerializedName("name")
    private String name;


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
}

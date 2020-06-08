package com.mukhtarinc.thescoop.data.network.sources;

import com.google.gson.annotations.SerializedName;
import com.mukhtarinc.thescoop.model.Source;

import java.util.List;

/**
 * Created by Raiyan Mukhtar on 6/7/2020.
 */
public class SourceResponse {

    @SerializedName("sources")
    List<Source> sources;

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}

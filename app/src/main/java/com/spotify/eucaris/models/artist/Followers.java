package com.spotify.eucaris.models.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ire_Eu on 21/05/2017.
 */

public class Followers {

    @SerializedName("href")
    @Expose
    private Object href;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Object getHref() {
        return href;
    }

    public void setHref(Object href) {
        this.href = href;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}

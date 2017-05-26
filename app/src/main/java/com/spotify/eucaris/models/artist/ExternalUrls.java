package com.spotify.eucaris.models.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ire_Eu on 21/05/2017.
 */

public class ExternalUrls {

    @SerializedName("spotify")
    @Expose
    private String spotify;

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

}

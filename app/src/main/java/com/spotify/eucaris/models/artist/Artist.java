package com.spotify.eucaris.models.artist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ire_Eu on 21/05/2017.
 */

public class Artist {

    @SerializedName("artists")
    @Expose
    public Artists artists;


    public Artists getArtists() {
        return artists;
    }

    public void setArtists(Artists artists) {
        this.artists = artists;
    }

}

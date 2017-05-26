package com.spotify.eucaris.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ire_Eu on 26/05/2017.
 */

public class Album extends RealmObject{
    @PrimaryKey
    String id;
    String albumImage;
    String name;

    public Album(){

    }

    public Album(String id, String albumImage, String name) {
        this.id = id;
        this.albumImage = albumImage;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

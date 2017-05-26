package com.spotify.eucaris;

import android.app.Application;
import android.content.Context;

import com.spotify.eucaris.realm.Migration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ire_Eu on 26/05/2017.
 */

public class MusicAplication extends Application {
    private  static Realm realm;
    private  static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(3) // Must be bumped when the schema changes
                .deleteRealmIfMigrationNeeded()
                .migration(new Migration()) // Migration to run instead of throwing an exception
                .build();

        realm=Realm.getInstance(config); // Use old file
        MusicAplication.context=getApplicationContext();
    }


    public static Realm getRealm(){
        return  MusicAplication.realm;

    }
}

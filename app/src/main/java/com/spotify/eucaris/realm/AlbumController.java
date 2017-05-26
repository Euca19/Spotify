package com.spotify.eucaris.realm;

import android.util.Log;

import com.spotify.eucaris.MusicAplication;
import com.spotify.eucaris.models.album.Item;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ire_Eu on 26/05/2017.
 */

public class AlbumController {

    private static AlbumController instance;

    private static final String TAG = AlbumController.class.getSimpleName();
    private static Realm realm = MusicAplication.getRealm();



    public static AlbumController with() {
        if (instance == null) {
            instance = new AlbumController();
        }
        return instance;
    }


    public static void clearData() {
        final RealmResults<Album> results = realm.where(Album.class).findAll();

// All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                // Delete all matches
                results.deleteAllFromRealm();
            }
        });

    }


    public static void saveData(final Item results) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // Add a person
                    // FavComic comic = realm.createObject(FavComic.class,results.getId());

                    Album album = realm.createObject(Album.class, results.getId());
                    Log.e(TAG, results.getName() + " " + results.getImages().get(0).getUrl() +" " + results.getId() );
                    album.setName(results.getName());

                    try {
                        album.setAlbumImage(results.getImages().get(0).getUrl());
                    }catch (Exception e){
                        album.setAlbumImage("");
                    }

                }


            });
        } catch (Exception e) {
            Log.e("TAG sa", e.getMessage());
        }

    }

    public static RealmResults<Album> getAllData() {
        final RealmResults<Album> results = realm.where(Album.class).findAll();
        Log.e("TAG save", String.valueOf(results.size()));
        return results;


    }

    public static void  deleteData(String id){
        try {
            final Album results = realm.where(Album.class).equalTo("id", id).findFirst();
            Log.d("DELETE",results.getName());
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.deleteFromRealm(); // indirectly delete object

                }
            });
        }catch (Exception e){
            Log.d("TAG",e.getMessage());
        }



    }

    public static boolean  getData(String  id){
        final Album results = realm.where(Album.class).equalTo("id", id).findFirst();


        if (results==null) {
            return  false;
        }else {
            return  true;
        }


    }



}

package com.spotify.eucaris.interfaces;

/**
 * Created by Ire_Eu on 21/05/2017.
 */

import android.util.Log;

import com.spotify.eucaris.models.artist.Artist;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;


/**
 * Created by Ire_Eu on 21/05/2017.
 */

public class CallsRetrofit {

    static SpotyFactory.SpotyInterfaces service =  SpotyFactory.getClient();
    private static final ArrayList<Subscription> subscriptions = new ArrayList<>();



    public static  void getArtist(String q)  {

        service.getArtist(q).enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {

                getResponse(response.body().getArtists());

            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {

            }
        });

    }


    public static void getResponse(Object response){

        ObjectMapper objectM = new ObjectMapper();
        try {
            Log.e("RESPONSE", objectM.writeValueAsString(response));
        }catch (Exception e){

        }
    }




}

